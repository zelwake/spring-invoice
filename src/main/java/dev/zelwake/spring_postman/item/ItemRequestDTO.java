package dev.zelwake.spring_postman.item;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ItemDTO(
        @NotBlank(message = "Name cannot be empty") String name,
        @NotNull(message = "Value per item needs to be specified") Integer priceInCents,
        @NotNull(message = "Amount of items cannot be null") Integer amount
) {
}
