package dev.zelwake.spring_postman.customerInvoiceItem;

import java.util.Optional;
import java.util.UUID;

public interface CustomerInvoiceItemRepository {

    Optional<CustomerInvoiceItem> findCustomerInvoiceItemById(UUID id);

}
