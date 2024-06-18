package dev.zelwake.spring_postman.invoice;

import dev.zelwake.spring_postman.invoiceCustomer.InvoiceCustomer;

import java.util.List;

public record InvoiceResponseDTO(
        Integer totalPages,
        Long totalElements,
        Integer currentPage,
        List<InvoiceCustomer> content
) {
}
