package dev.zelwake.spring_postman.catalogue;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class CatalogueService {

    private final CatalogueRepository repository;

    CatalogueService(CatalogueRepository repository) {
        this.repository = repository;
    }


    public CatalogueResponseDTO getCatalogue(Pageable pageable) {
        Page<Catalogue> data = repository.findAll(PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), pageable.getSortOr(Sort.by(Sort.Direction.ASC, "name"))));
        return new CatalogueResponseDTO(data.getTotalPages(), data.getTotalElements(), data.getNumber(), data.getContent());
    }
}
