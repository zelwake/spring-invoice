package dev.zelwake.spring_postman.catalogue;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.UUID;

public interface CatalogueRepository extends CrudRepository<Catalogue, UUID>, PagingAndSortingRepository<Catalogue, UUID> {
}
