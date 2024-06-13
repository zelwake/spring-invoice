package dev.zelwake.spring_postman.customer;

import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    ResponseEntity<CustomerResponseDTO> findAll(Pageable pageable) {
        CustomerResponseDTO cataloguePage = customerService.getCatalogue(pageable);
        return ResponseEntity.ok(cataloguePage);
    }

    @PostMapping
    ResponseEntity<String> createNew(@Valid @RequestBody Customer customer) {
        Customer newCustomer = customerService.save(customer);
        return ResponseEntity.created(URI.create(newCustomer.id().toString())).build();
    }

    @GetMapping("/{id}")
    ResponseEntity<Customer> findById(@PathVariable String id) {
        Optional<Customer> catalogue = customerService.getCatalogueById(id);
        return catalogue.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
