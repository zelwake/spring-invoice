package dev.zelwake.spring_postman.invoice;

import jakarta.validation.constraints.NotEmpty;
import org.springframework.data.annotation.Id;

import java.time.LocalDate;
import java.util.UUID;

public record Invoice (
        @Id UUID id,
        @NotEmpty String invoiceNumber,
        LocalDate issuedOn,
        LocalDate paidOn,
        Status status,
        Integer amount
) implements BaseInvoice {
}

