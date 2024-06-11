package dev.zelwake.spring_postman.catalogue;

import java.util.List;
import java.util.Objects;

public class CatalogueResponseDTO {
    private Integer totalPages;
    private Long totalElements;
    private Integer currentPage;
    private List<Catalogue> content;

    public CatalogueResponseDTO(Integer totalPages, Long totalElements, Integer currentPage, List<Catalogue> content) {
        this.totalPages = totalPages;
        this.totalElements = totalElements;
        this.currentPage = currentPage;
        this.content = content;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    public Long getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(Long totalElements) {
        this.totalElements = totalElements;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public List<Catalogue> getContent() {
        return content;
    }

    public void setContent(List<Catalogue> content) {
        this.content = content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CatalogueResponseDTO that = (CatalogueResponseDTO) o;
        return Objects.equals(totalPages, that.totalPages) && Objects.equals(totalElements, that.totalElements) && Objects.equals(currentPage, that.currentPage) && Objects.equals(content, that.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(totalPages, totalElements, currentPage, content);
    }

    @Override
    public String toString() {
        return "CatalogueResponseDTO{" +
                "totalPages=" + totalPages +
                ", totalElements=" + totalElements +
                ", currentPage=" + currentPage +
                ", content=" + content +
                '}';
    }
}
