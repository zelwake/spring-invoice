package dev.zelwake.spring_postman.customer;

import java.util.UUID;

public record CustomerNameDTO(
        UUID id,
        String name
) implements BaseCustomer {
}
