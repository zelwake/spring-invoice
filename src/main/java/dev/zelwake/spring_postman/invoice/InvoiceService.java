package dev.zelwake.spring_postman.invoice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class InvoiceService {

    private final InvoiceRepository repository;

    @Autowired
    public InvoiceService(InvoiceRepository invoiceRepository) {
        this.repository = invoiceRepository;
    }

    Page<Invoice> getInvoices(Pageable pageable) {
        return repository.findAll((PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), pageable.getSortOr(Sort.by(Sort.Direction.ASC, "issuedOn")))));
    }

    public Invoice saveInvoice(Invoice newInvoice) {
        return repository.save(newInvoice);
    }

    public Optional<Invoice> getInvoiceById(String id) {
        return repository.findById(id);
    }
}
