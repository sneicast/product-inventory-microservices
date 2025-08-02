package dev.scastillo.inventory.shared.exception;

public class ConflictException  extends RuntimeException {
    public ConflictException(String message) {
        super(message);
    }
}
