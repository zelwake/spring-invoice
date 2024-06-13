package dev.zelwake.spring_postman.invoiceItem;

import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.UUID;

@Service
public class InvoiceItemService {

    private final InvoiceItemRepository invoiceItemRepository;

    public InvoiceItemService(InvoiceItemRepository invoiceItemRepository) {
        this.invoiceItemRepository = invoiceItemRepository;
    }

    public InvoiceItem findInvoiceDetailById(UUID id) {
        InvoiceItem invoiceDetail = invoiceItemRepository.findInvoiceWithItemsByInvoiceId(id);
        try {
            System.out.println(invoiceDetail);
            return invoiceDetail;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}
