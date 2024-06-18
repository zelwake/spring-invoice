package dev.zelwake.spring_postman.invoice;

import dev.zelwake.spring_postman.customerInvoiceItem.CustomerInvoiceItemDTO;
import dev.zelwake.spring_postman.exceptions.ResourceBadRequest;
import dev.zelwake.spring_postman.utils.PaginatedResponse;
import dev.zelwake.spring_postman.customerInvoiceItem.CustomerInvoiceItem;
import dev.zelwake.spring_postman.exceptions.ResourceNotFound;
import dev.zelwake.spring_postman.invoiceCustomer.InvoiceCustomer;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/invoices")
public class InvoiceController {

    Logger logger = LoggerFactory.getLogger(InvoiceController.class);

    private final InvoiceService invoices;

    InvoiceController(InvoiceService invoiceService) {
        this.invoices = invoiceService;
    }

    @GetMapping("")
    ResponseEntity<PaginatedResponse<InvoiceCustomer>> getAllInvoices(Pageable pageable) {
        try {
            Page<InvoiceCustomer> page = invoices.getInvoices(pageable);
            List<InvoiceCustomer> customers = page.getContent().stream().map(c -> new InvoiceCustomer(c.id(), c.invoiceNumber(), c.issuedOn(), c.expectedOn(), c.paidOn(), c.status(), c.price() / 100, c.customer())).toList();
            PaginatedResponse<InvoiceCustomer> invoice = new PaginatedResponse<>(page.getTotalPages(), page.getTotalElements(), page.getNumber(), customers);
            return ResponseEntity.ok(invoice);
        } catch (Exception e) {
            logger.error(e.toString());
            logger.error(e.getMessage());
            throw new ResourceBadRequest("Wrong use of query parameters");
        }
    }

    @PostMapping("")
    ResponseEntity<String> addNewInvoice(@Valid @RequestBody InvoiceRequestDTO invoice) {
        Invoice savedInvoice = invoices.addInvoice(invoice);
        if (savedInvoice == null) {
            throw new ResourceBadRequest("Items body is missing properties");
        }
        return ResponseEntity.created(URI.create(savedInvoice.id().toString())).build();
    }

    @GetMapping("/{id}")
    ResponseEntity<CustomerInvoiceItemDTO> findById(@PathVariable UUID id) {
        CustomerInvoiceItemDTO invoiceDetail = invoices.getInvoiceById(id);
        if (invoiceDetail == null) {
            throw new ResourceNotFound("No invoice with this id exists");
        }
        return ResponseEntity.ok(invoiceDetail);
    }

    @PutMapping("/{id}")
    ResponseEntity<String> updateById(@PathVariable String id, @Valid @RequestBody Invoice invoice) {
        UpdateInvoiceStatus updatedInvoice = invoices.updateInvoice(id, invoice);

        if (updatedInvoice == UpdateInvoiceStatus.UPDATED) return ResponseEntity.noContent().build();
        else if (updatedInvoice == UpdateInvoiceStatus.NOT_FOUND) return ResponseEntity.notFound().build();
        else return ResponseEntity.badRequest().body("Wrong format of body");
    }
}
