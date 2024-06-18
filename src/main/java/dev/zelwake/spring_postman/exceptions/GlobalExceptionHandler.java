package dev.zelwake.spring_postman.exceptions;

import dev.zelwake.spring_postman.invoice.InvoiceController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptionHandler {
    Logger logger = LoggerFactory.getLogger(InvoiceController.class);

    @ExceptionHandler(ResourceNotFound.class)
    public ResponseEntity<?> handleResourceNotFound(ResourceNotFound ex, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(HttpStatus.NOT_FOUND, ex.getMessage(), request.getDescription(false));
        logger.error(ex.toString());
        return  new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ResourceBadRequest.class)
    public ResponseEntity<?> handleResourceBadRequest(ResourceBadRequest ex, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(HttpStatus.BAD_REQUEST, ex.getMessage(), request.getDescription(false));
        logger.error(ex.toString());
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    public record ErrorDetails (
            HttpStatus status,
            String message,
            String details
    ) {}
}
