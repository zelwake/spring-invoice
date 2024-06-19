package dev.zelwake.spring_postman.payment;

import org.springframework.data.annotation.Id;

import java.time.LocalDate;
import java.util.UUID;

public record Payment(
        @Id Integer id,
        int amountInCents,
        LocalDate date,
        UUID invoiceId
) {
}
