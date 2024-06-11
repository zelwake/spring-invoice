package dev.zelwake.spring_postman.invoice;

import jakarta.validation.constraints.NotEmpty;

public record InvoiceDTO(
        @NotEmpty String invoiceNumber,
        Integer amount
) implements BaseInvoice {}
