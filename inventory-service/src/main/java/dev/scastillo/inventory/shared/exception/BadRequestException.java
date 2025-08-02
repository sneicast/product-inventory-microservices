package dev.scastillo.inventory.shared.exception;

public class BadRequestException extends  RuntimeException {
    public BadRequestException(String message) {
        super(message);
    }
}
