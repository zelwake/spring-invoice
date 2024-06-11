package dev.zelwake.spring_postman.invoice;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record InvoiceDTO(
        @NotBlank String invoiceNumber,
        @NotNull Integer amount,
        @NotNull LocalDate issuedOn,
        LocalDate expectedOn
) implements BaseInvoice {
}
