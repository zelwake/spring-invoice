package dev.zelwake.spring_postman.customerInvoiceItem;

import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class CustomerInvoiceItemService {

    private final CustomerInvoiceItemRepository customerInvoiceItemRepository;

    public CustomerInvoiceItemService(CustomerInvoiceItemRepository customerInvoiceItemRepository) {
        this.customerInvoiceItemRepository = customerInvoiceItemRepository;
    }

    public Optional<CustomerInvoiceItem> getInvoiceWithCustomerItemsById(UUID id) {
        return customerInvoiceItemRepository.getCustomerInvoiceItemById(id);
    }
}
