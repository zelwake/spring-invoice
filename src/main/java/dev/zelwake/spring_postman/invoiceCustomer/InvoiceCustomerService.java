package dev.zelwake.spring_postman.invoiceCustomer;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class InvoiceCustomerService {

    private final InvoiceCustomerRepository invoiceCustomerRepository;

    public InvoiceCustomerService(InvoiceCustomerRepository invoiceCustomerRepository) {
        this.invoiceCustomerRepository = invoiceCustomerRepository;
    }

    public Optional<InvoiceCustomer> getInvoiceWithCustomerNameById(UUID id) {
        return invoiceCustomerRepository.findInvoiceWithCustomerNameById(id);
    }

    public Page<InvoiceCustomer> getAllInvoiceWithCustomerName(Pageable pageable) {
        return invoiceCustomerRepository.findAllInvoiceWithCustomerName(pageable);
    }
}
