package dev.zelwake.spring_postman.invoice;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/api/invoices")
public class InvoiceController {

    private final InvoiceRepository invoices;

    InvoiceController(InvoiceRepository invoiceRepository) {
        this.invoices = invoiceRepository;
    }

    @GetMapping("")
    List<Invoice> findAll() {
        return invoices.findAll();
    }

    @PostMapping("")
    ResponseEntity<String> createNew(@RequestBody InvoiceDTO invoice) {
        Invoice newInvoice = new Invoice(
                null,
                invoice.invoiceNumber(),
                LocalDate.now(),
                LocalDate.now().plusDays(20),
                Status.PENDING,
                invoice.amount()
        );
        Invoice savedInvoice = invoices.save(newInvoice);
        return ResponseEntity.created(URI.create(savedInvoice.id())).build();
    }

    @GetMapping("/{id}")
    ResponseEntity<Invoice> findById(@PathVariable String id) {
        Optional<Invoice> invoice = invoices.findById(id);
        System.out.println(invoice);
        return invoice.map(value -> ResponseEntity.ok().body(value)).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
