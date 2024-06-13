package dev.zelwake.spring_postman.invoiceCustomer;

import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface InvoiceCustomerRepository {
    InvoiceCustomer findInvoiceWithCustomerNameById(UUID id);

    List<InvoiceCustomer> findAllInvoiceWithCustomerName(Pageable pageable);
}
