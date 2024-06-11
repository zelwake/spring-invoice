package dev.zelwake.spring_postman.invoice;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDate;
import java.util.Optional;

@RestController
@RequestMapping("/api/invoices")
public class InvoiceController {

    private final InvoiceService invoices;

    InvoiceController(InvoiceService invoiceService) {
        this.invoices = invoiceService;
    }

    @GetMapping("")
    ResponseEntity<InvoiceResponseDTO> findAll(Pageable pageable, PagedResourcesAssembler<Invoice> assembler) {
        try {
            Page<Invoice> page = invoices.getInvoices(pageable);
            InvoiceResponseDTO invoice = new InvoiceResponseDTO(page.getTotalPages(), page.getTotalElements(), page.getNumber(), page.getContent());
            return ResponseEntity.ok(invoice);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("")
    ResponseEntity<String> createNew(@Valid @RequestBody InvoiceDTO invoice) {
        Invoice savedInvoice = invoices.saveInvoice(invoice);
        return ResponseEntity.created(URI.create(savedInvoice.id().toString())).build();
    }

    @GetMapping("/{id}")
    ResponseEntity<Invoice> findById(@PathVariable String id) {
        Optional<Invoice> invoice = invoices.getInvoiceById(id);
        return invoice.map(value -> ResponseEntity.ok().body(value)).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    ResponseEntity<String> updateById(@PathVariable String id, @Valid @RequestBody Invoice invoice) {
        UpdateInvoiceStatus updatedInvoice = invoices.updateInvoice(id, invoice);

        if (updatedInvoice == UpdateInvoiceStatus.UPDATED)
            return ResponseEntity.noContent().build();
        else if (updatedInvoice == UpdateInvoiceStatus.NOT_FOUND)
            return ResponseEntity.notFound().build();
        else
            return ResponseEntity.badRequest().body("Wrong format of body");
    }
}
