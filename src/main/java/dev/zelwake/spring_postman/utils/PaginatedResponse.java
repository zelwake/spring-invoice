package dev.zelwake.spring_postman;

import java.util.List;

public record PaginatedResponse<T>(
        Integer totalPages,
        Long totalElements,
        Integer currentPage,
        List<T> content
) {
}
