package dev.zelwake.spring_postman.invoiceItem;

import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface InvoiceItemRepository {

//    @Query("SELECT i.*, it.* " +
//           "FROM invoice i " +
//           "LEFT JOIN item it ON i.id = it.invoice_id " +
//           "WHERE i.id = :id " +
//           "LIMIT 1")
    InvoiceItem findInvoiceWithItemsByInvoiceId(UUID id);
}
