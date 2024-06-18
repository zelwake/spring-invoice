package dev.zelwake.spring_postman.invoiceCustomer;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

public interface InvoiceCustomerRepository {
    Optional<InvoiceCustomer> getInvoiceWithCustomerNameById(UUID id);

    Page<InvoiceCustomer> getAllInvoiceWithCustomerName(Pageable pageable);
}
