package dev.zelwake.spring_postman.catalogue;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
