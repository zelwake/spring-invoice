package dev.zelwake.spring_postman.catalogue;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.annotation.Id;

import java.util.UUID;

public record Catalogue(
        @Id UUID id,
        String ICO,
        @NotBlank String name,
        @NotBlank String streetName,
        @NotBlank String city,
        @NotNull Integer zipCode
) {
}
