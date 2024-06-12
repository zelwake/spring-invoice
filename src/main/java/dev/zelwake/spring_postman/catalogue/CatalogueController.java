package dev.zelwake.spring_postman.catalogue;

import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/api/catalogue")
public class CatalogueController {

    private final CatalogueService catalogueService;

    public CatalogueController(CatalogueService catalogueService) {
        this.catalogueService = catalogueService;
    }

    @GetMapping
    ResponseEntity<CatalogueResponseDTO> findAll(Pageable pageable) {
        CatalogueResponseDTO cataloguePage = catalogueService.getCatalogue(pageable);
        return ResponseEntity.ok(cataloguePage);
    }

    @PostMapping
    ResponseEntity<String> createNew(@Valid @RequestBody Catalogue catalogue) {
        Catalogue newCatalogue = catalogueService.save(catalogue);
        return ResponseEntity.created(URI.create(newCatalogue.id().toString())).build();
    }

    @GetMapping("/{id}")
    ResponseEntity<Catalogue> findById(@PathVariable String id) {
        Optional<Catalogue> catalogue = catalogueService.getCatalogueById(id);
        return catalogue.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
