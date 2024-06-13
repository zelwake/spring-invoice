package dev.zelwake.spring_postman.invoiceItem;

import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class InvoiceItemService {

    private final InvoiceItemRepository invoiceItemRepository;

    public InvoiceItemService(InvoiceItemRepository invoiceItemRepository) {
        this.invoiceItemRepository = invoiceItemRepository;
    }

    public InvoiceItem findInvoiceDetailById(UUID id) {
        try {
            return invoiceItemRepository.findInvoiceWithItemsByInvoiceId(id);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}
