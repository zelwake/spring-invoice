package dev.zelwake.spring_postman.invoice;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface InvoiceRepository extends CrudRepository<Invoice, String>, PagingAndSortingRepository<Invoice, String> {
}
