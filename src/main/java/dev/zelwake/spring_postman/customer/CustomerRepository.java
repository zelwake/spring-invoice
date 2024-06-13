package dev.zelwake.spring_postman.customer;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.UUID;

public interface CustomerRepository extends CrudRepository<Customer, UUID>, PagingAndSortingRepository<Customer, UUID> {
}
