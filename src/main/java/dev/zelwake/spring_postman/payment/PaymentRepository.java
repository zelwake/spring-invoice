package dev.zelwake.spring_postman.payment;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import org.springframework.data.domain.Page;
import java.util.UUID;

public interface PaymentRepository extends CrudRepository<Payment, Integer>, PagingAndSortingRepository<Payment, Integer> {

    Page<Payment> findAllByInvoiceId(UUID id, Pageable pageable);
}
