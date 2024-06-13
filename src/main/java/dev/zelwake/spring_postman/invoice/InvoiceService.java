package dev.zelwake.spring_postman.invoice;

import dev.zelwake.spring_postman.invoiceItem.InvoiceItem;
import dev.zelwake.spring_postman.invoiceItem.InvoiceItemService;
import dev.zelwake.spring_postman.item.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class InvoiceService {

    private final InvoiceRepository invoiceRepository;

    private final ItemService itemService;
    private final InvoiceItemService invoiceItemService;

    public InvoiceService(InvoiceRepository invoiceRepository, ItemService itemService, InvoiceItemService invoiceItemService) {
        this.invoiceRepository = invoiceRepository;
        this.itemService = itemService;
        this.invoiceItemService = invoiceItemService;
    }

    Page<Invoice> getInvoices(Pageable pageable) {
        return invoiceRepository.findAll(PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), pageable.getSortOr(Sort.by(Sort.Direction.ASC, "issuedOn"))));
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

    public InvoiceItem getInvoiceById(UUID id) {
        return invoiceItemService.findInvoiceDetailById(id);
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

