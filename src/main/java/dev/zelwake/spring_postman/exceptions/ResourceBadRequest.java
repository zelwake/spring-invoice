package dev.zelwake.spring_postman.errors;

public class ResourceBadRequest extends RuntimeException {
    public ResourceBadRequest(String message) {
        super(message);
    }
}
