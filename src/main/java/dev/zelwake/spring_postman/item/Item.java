package dev.zelwake.spring_postman.item;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.annotation.Id;

import java.util.UUID;

public record Item(
        @Id Integer id,
        @NotBlank String name,
        @NotNull Integer value,
        @NotNull Integer amount,
        @NotNull UUID invoiceId
) {
}
