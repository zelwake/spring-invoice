package dev.zelwake.spring_postman.invoiceItem;

import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface InvoiceItemRepository {

    InvoiceItem findInvoiceWithItemsByInvoiceId(UUID id);
}
