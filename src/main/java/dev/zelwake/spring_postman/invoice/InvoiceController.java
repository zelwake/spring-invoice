package dev.zelwake.spring_postman.invoice;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

//    @PostMapping("")
//    ResponseEntity<String> createNew(@RequestBody InvoiceDTO invoice) {
//        Invoice newInvoice = new Invoice(
//                null,
//                invoice.invoiceNumber(),
//                LocalDate.now(),
//                LocalDate.now().plusDays(20),
//                Status.PENDING,
//                invoice.amount()
//        );
//        Invoice savedInvoice = invoices.save(newInvoice);
//        return ResponseEntity.created(URI.create(savedInvoice.id().toString())).build();
//    }
//
//    @GetMapping("/{id}")
//    ResponseEntity<Invoice> findById(@PathVariable String id) {
//        Optional<Invoice> invoice = invoices.findById(id);
//        return invoice.map(value -> ResponseEntity.ok().body(value)).orElseGet(() -> ResponseEntity.notFound().build());
//    }
}
