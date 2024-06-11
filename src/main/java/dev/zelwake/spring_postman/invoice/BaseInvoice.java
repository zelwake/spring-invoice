package dev.zelwake.spring_postman.invoice;

import jakarta.validation.constraints.NotEmpty;

import java.time.LocalDate;

public interface BaseInvoice {
    @NotEmpty String invoiceNumber();
    Integer amount();
}

