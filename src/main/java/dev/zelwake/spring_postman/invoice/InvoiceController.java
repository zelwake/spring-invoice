package dev.zelwake.spring_postman.invoice;

import dev.zelwake.spring_postman.invoiceItem.InvoiceItem;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/api/invoices")
public class InvoiceController {

    private final InvoiceService invoices;

    InvoiceController(InvoiceService invoiceService) {
        this.invoices = invoiceService;
    }

    @GetMapping("")
    ResponseEntity<InvoiceResponseDTO> findAll(Pageable pageable) {
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
        return savedInvoice != null ? ResponseEntity.created(URI.create(savedInvoice.id().toString())).build() : ResponseEntity.badRequest().body("Items body is missing properties");
    }

    @GetMapping("/{id}")
    ResponseEntity<InvoiceItem> findById(@PathVariable UUID id) {
        InvoiceItem invoiceDetail = invoices.getInvoiceById(id);
        return invoiceDetail != null ? ResponseEntity.ok(invoiceDetail) : ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    ResponseEntity<String> updateById(@PathVariable String id, @Valid @RequestBody Invoice invoice) {
        UpdateInvoiceStatus updatedInvoice = invoices.updateInvoice(id, invoice);

        if (updatedInvoice == UpdateInvoiceStatus.UPDATED) return ResponseEntity.noContent().build();
        else if (updatedInvoice == UpdateInvoiceStatus.NOT_FOUND) return ResponseEntity.notFound().build();
        else return ResponseEntity.badRequest().body("Wrong format of body");
    }
}
