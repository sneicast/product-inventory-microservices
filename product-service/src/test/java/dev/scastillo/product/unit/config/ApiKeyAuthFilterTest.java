package dev.scastillo.product.unit.config;

import dev.scastillo.product.config.ApiKeyAuthFilter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.PrintWriter;
import java.io.StringWriter;

import static org.mockito.Mockito.*;

public class ApiKeyAuthFilterTest {
    private ApiKeyAuthFilter filter;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private FilterChain chain;
    private StringWriter stringWriter;

    @BeforeEach
    void setUp() throws Exception {
        filter = new ApiKeyAuthFilter();
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        chain = mock(FilterChain.class);
        stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(printWriter);
    }

    @Test
    void testValidApiKey() throws Exception {
        // Simula la API Key configurada
        when(request.getHeader("X-API-KEY")).thenReturn(null);
        // Usa reflexión para establecer el valor de configuredApiKey
        var field = filter.getClass().getDeclaredField("configuredApiKey");
        field.setAccessible(true);
        field.set(filter, "test-api-key");

        when(request.getHeader("X-API-KEY")).thenReturn("test-api-key");
        filter.doFilter(request, response, chain);

        verify(chain, times(1)).doFilter(request, response);
        verify(response, never()).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        // No se escribe mensaje de error
        assert(!stringWriter.toString().contains("Clave de API inválida"));
    }

    @Test
    void testInvalidApiKey() throws Exception {
        var field = filter.getClass().getDeclaredField("configuredApiKey");
        field.setAccessible(true);
        field.set(filter, "test-api-key");

        when(request.getHeader("X-API-KEY")).thenReturn("invalid-key");
        filter.doFilter(request, response, chain);

        verify(response, times(1)).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        verify(chain, never()).doFilter(request, response);
        // Se escribe mensaje de error
        assert(stringWriter.toString().contains("Clave de API inválida"));
    }

    @Test
    void testMissingApiKey() throws Exception {
        var field = filter.getClass().getDeclaredField("configuredApiKey");
        field.setAccessible(true);
        field.set(filter, "test-api-key");

        when(request.getHeader("X-API-KEY")).thenReturn(null);
        filter.doFilter(request, response, chain);

        verify(response, times(1)).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        verify(chain, never()).doFilter(request, response);
        assert(stringWriter.toString().contains("Clave de API inválida"));
    }
}
