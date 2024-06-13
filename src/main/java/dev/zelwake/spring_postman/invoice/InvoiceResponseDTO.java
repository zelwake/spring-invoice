package dev.zelwake.spring_postman.invoice;

import java.util.List;

public record InvoiceResponseDTO(
        Integer totalPages,
        Long totalElements,
        Integer currentPage,
        List<Invoice> content
) {

}
