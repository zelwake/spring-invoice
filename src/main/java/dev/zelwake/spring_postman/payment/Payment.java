package dev.zelwake.spring_postman.payment;

import java.time.LocalDate;
import java.util.UUID;

public record Payment(
        Integer id,
        LocalDate date,
        UUID invoiceId,
        int amountInCents
) {
}
