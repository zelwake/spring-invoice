package dev.zelwake.spring_postman.customer;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class CustomerService {

    private final CustomerRepository repository;

    CustomerService(CustomerRepository repository) {
        this.repository = repository;
    }


    public CustomerResponseDTO getCatalogue(Pageable pageable) {
        Page<Customer> data = repository.findAll(PageRequest.of(
                pageable.getPageNumber(),
                pageable.getPageSize(),
                pageable.getSortOr(Sort.by(Sort.Direction.ASC, "name"))
        ));
        return new CustomerResponseDTO(data.getTotalPages(), data.getTotalElements(), data.getNumber(), data.getContent());
    }

    public Customer save(Customer customer) {
        Customer newCustomer = new Customer(
                null,
                customer.ICO(),
                customer.name(),
                customer.streetName(),
                customer.city(),
                customer.zipCode()
        );
        return repository.save(newCustomer);
    }

    public Optional<Customer> getCatalogueById(String id) {
        return repository.findById(UUID.fromString(id));
    }
}
