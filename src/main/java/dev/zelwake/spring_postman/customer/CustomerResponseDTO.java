package dev.zelwake.spring_postman.customer;

import java.util.List;

public record CustomerResponseDTO(
    Integer totalPages,
    Long totalElements,
    Integer currentPage,
    List<Customer> content
) {

}
