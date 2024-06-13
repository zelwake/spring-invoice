package dev.zelwake.spring_postman.invoiceCustomer;

import java.util.UUID;

public interface InvoiceCustomerRepository {
    InvoiceCustomer findInvoiceWithCustomerNameById(UUID id);
}
