package dev.zelwake.spring_postman.invoice;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public interface BaseInvoice {
    @NotBlank(message = "Missing invoice number") String invoiceNumber();
    @NotNull(message = "Missing amount number") Integer amount();
    @NotNull LocalDate issuedOn();
    LocalDate expectedOn();
}

