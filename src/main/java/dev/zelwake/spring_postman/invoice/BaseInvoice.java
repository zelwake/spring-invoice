package dev.zelwake.spring_postman.invoice;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public interface BaseInvoice {
    @NotBlank(message = "Missing invoice number") String invoiceNumber();
    @NotBlank(message = "Missing amount number") Integer amount();
    @NotBlank LocalDate issuedOn();
    LocalDate expectedOn();
}

