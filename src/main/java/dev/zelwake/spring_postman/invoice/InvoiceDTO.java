package dev.zelwake.spring_postman.invoice;

import jakarta.validation.constraints.NotEmpty;

import java.time.LocalDate;

public record InvoiceDTO(
        @NotEmpty String invoiceNumber,
        @NotEmpty Integer amount,
        @NotEmpty LocalDate issuedOn,
        LocalDate expectedOn
) implements BaseInvoice {
}
