package dev.zelwake.spring_postman;

import java.time.LocalDate;
import java.util.UUID;

public record Payment(
        int Id,
        LocalDate date,
        UUID invoiceId,
        int amountInCents
) {
}
