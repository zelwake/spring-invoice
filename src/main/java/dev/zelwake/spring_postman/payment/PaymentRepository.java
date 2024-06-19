package dev.zelwake.spring_postman.payment;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface PaymentRepository extends CrudRepository<Payment, Integer>, PagingAndSortingRepository<Payment, Integer> {

    Page<Payment> findAllByInvoiceId(UUID id, Pageable pageable);

    @Query("SELECT SUM(amount_in_cents) FROM payment WHERE invoice_id = :id")
    Integer findSumOfPriceById(@Param("id") UUID id);
}
