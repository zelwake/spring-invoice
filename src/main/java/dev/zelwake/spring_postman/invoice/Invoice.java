package dev.zelwake.spring_postman.invoice;

import com.fasterxml.jackson.databind.ser.Serializers;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.data.annotation.Id;

import java.time.LocalDate;

public record Invoice(
        @Id String id,
        @NotEmpty String invoiceNumber,
        LocalDate issuedOn,
        LocalDate paidOn,
        Status status,
        Integer amount
) implements BaseInvoice {
}

