package dev.zelwake.spring_postman.invoice;

import dev.zelwake.spring_postman.customerInvoiceItem.CustomerInvoiceItem;
import dev.zelwake.spring_postman.customerInvoiceItem.CustomerInvoiceItemService;
import dev.zelwake.spring_postman.invoiceCustomer.InvoiceCustomer;
import dev.zelwake.spring_postman.invoiceCustomer.InvoiceCustomerService;
import dev.zelwake.spring_postman.item.ItemService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class InvoiceService {

    private final InvoiceRepository invoiceRepository;

    private final ItemService itemService;
    private final InvoiceCustomerService invoiceCustomerService;
    private final CustomerInvoiceItemService customerInvoiceItemService;

    public InvoiceService(InvoiceRepository invoiceRepository, ItemService itemService, InvoiceCustomerService invoiceCustomerService, CustomerInvoiceItemService customerInvoiceItemService) {
        this.invoiceRepository = invoiceRepository;

        this.itemService = itemService;
        this.invoiceCustomerService = invoiceCustomerService;
        this.customerInvoiceItemService = customerInvoiceItemService;
    }

    Page<InvoiceCustomer> getInvoices(Pageable pageable) {
        return invoiceCustomerService.getAllInvoiceWithCustomerName(pageable);
    }

    public Invoice saveInvoice(InvoiceDTO invoiceData) {
        Integer amount = null;
        try {
            amount = invoiceData.items().stream().mapToInt(i -> i.amount() * i.value()).sum();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }

        Invoice newInvoice = new Invoice(
                null,
                invoiceData.invoiceNumber(),
                invoiceData.issuedOn(),
                (invoiceData.expectedOn() != null) ? invoiceData.expectedOn() : invoiceData.issuedOn().plusDays(20),
                null,
                Status.PENDING,
                amount,
                invoiceData.customerId()
        );

        Invoice savedInvoice = invoiceRepository.save(newInvoice);

        boolean itemsDidSave = itemService.saveItems(invoiceData.items(), savedInvoice.id());
        if (!itemsDidSave)
            System.out.println("Items had error saving to database");

        return savedInvoice;
    }

    public CustomerInvoiceItem getInvoiceById(UUID id) {
        return customerInvoiceItemService.getInvoiceWithCustomerItemsById(id).orElse(null);
    }

    public UpdateInvoiceStatus updateInvoice(String id, Invoice invoice) {
        try {
            boolean findInvoice = invoiceRepository.findById(id).isPresent();
            if (findInvoice) {
                invoiceRepository.save(invoice);
                return UpdateInvoiceStatus.UPDATED;
            }
            return UpdateInvoiceStatus.NOT_FOUND;
        } catch (Exception e) {
            return UpdateInvoiceStatus.ERROR;
        }
    }
}

