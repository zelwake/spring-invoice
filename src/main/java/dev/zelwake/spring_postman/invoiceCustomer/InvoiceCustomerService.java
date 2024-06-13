package dev.zelwake.spring_postman.invoiceCustomer;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class InvoiceCustomerService {

    private final InvoiceCustomerRepository invoiceCustomerRepository;

    public InvoiceCustomerService(InvoiceCustomerRepository invoiceCustomerRepository) {
        this.invoiceCustomerRepository = invoiceCustomerRepository;
    }

    public InvoiceCustomer getInvoiceWithCustomerNameById(UUID id) {
        return invoiceCustomerRepository.findInvoiceWithCustomerNameById(id);
    }

    public Page<InvoiceCustomer> getAllInvoiceWithCustomerName(Pageable pageable) {
        List<InvoiceCustomer> invoiceList = this.invoiceCustomerRepository.findAllInvoiceWithCustomerName(pageable);
        boolean isListEmpty = !invoiceList.isEmpty();
        return new PageImpl<>(invoiceList, pageable, isListEmpty ? invoiceList.getFirst().numberOfItems() : 0);
    }
}
