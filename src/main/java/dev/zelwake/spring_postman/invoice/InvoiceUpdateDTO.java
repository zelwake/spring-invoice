package dev.zelwake.spring_postman.invoice;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.annotation.Id;

import java.time.LocalDate;
import java.util.UUID;

public record InvoiceUpdateDTO(
        @Id UUID id,
        @NotBlank String invoiceNumber,
        @NotNull LocalDate issuedOn,
        @NotNull LocalDate expectedOn,
        LocalDate paidOn,
        Status status,
        @NotNull(message = "Price cannot be empty") Float price,
        @NotNull UUID customerId
) implements BaseInvoice {
}

