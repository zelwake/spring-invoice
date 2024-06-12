package dev.zelwake.spring_postman.invoice;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.UUID;

public record InvoiceDTO(
        @NotBlank String invoiceNumber,
        @NotNull LocalDate issuedOn,
        LocalDate expectedOn,
        @NotNull UUID customerId
) implements BaseInvoice {
}
