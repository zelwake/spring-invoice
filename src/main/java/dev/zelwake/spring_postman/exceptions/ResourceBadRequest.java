package dev.zelwake.spring_postman.exceptions;

public class ResourceBadRequest extends RuntimeException {
    public ResourceBadRequest(String message) {
        super(message);
    }
}
