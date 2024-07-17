package dev.zelwake.spring_postman.payment;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDate;
import java.util.UUID;

public record PaymentRequestDTO(
        @NotNull(message = "Missing invoice id") UUID invoiceId,
        @NotNull(message = "Missing amount") @Positive(message = "Amount has to be a positive number") double amount,
        @NotNull(message = "Missing date of transaction") LocalDate date
) {
}
