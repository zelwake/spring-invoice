package dev.zelwake.spring_postman.invoice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class InvoiceService {

    private final InvoiceRepository repository;

    @Autowired
    public InvoiceService(InvoiceRepository invoiceRepository) {
        this.repository = invoiceRepository;
    }

    Page<Invoice> getInvoices(Pageable pageable) {
        return repository.findAll(PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), pageable.getSortOr(Sort.by(Sort.Direction.ASC, "issuedOn"))));
    }

    public Invoice saveInvoice(InvoiceDTO invoiceData) {
        Invoice newInvoice = new Invoice(
                null,
                invoiceData.invoiceNumber(),
                invoiceData.issuedOn(),
                (invoiceData.expectedOn() != null) ? invoiceData.expectedOn() : invoiceData.issuedOn().plusDays(20),
                null,
                Status.PENDING,
                invoiceData.amount()
        );
        return repository.save(newInvoice);
    }

    public Optional<Invoice> getInvoiceById(String id) {
        return repository.findById(id);
    }

    public UpdateInvoiceStatus updateInvoice(String id, Invoice invoice) {
        try {
            boolean findInvoice = repository.findById(id).isPresent();
            if (findInvoice) {
                repository.save(invoice);
                return UpdateInvoiceStatus.UPDATED;
            }
            return UpdateInvoiceStatus.NOT_FOUND;
        } catch (Exception e) {
            return UpdateInvoiceStatus.ERROR;
        }
    }
}

