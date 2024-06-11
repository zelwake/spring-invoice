package dev.zelwake.spring_postman.invoice;

import org.springframework.data.repository.ListCrudRepository;

public interface InvoiceRepository extends ListCrudRepository<Invoice, String> {
}
