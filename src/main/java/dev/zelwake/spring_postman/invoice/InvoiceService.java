package dev.zelwake.spring_postman.invoice;

import dev.zelwake.spring_postman.item.Item;
import dev.zelwake.spring_postman.item.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InvoiceService {

    private final InvoiceRepository invoiceRepository;
    private final ItemRepository itemRepository;

    @Autowired
    public InvoiceService(InvoiceRepository invoiceRepository, ItemRepository itemRepository) {
        this.invoiceRepository = invoiceRepository;
        this.itemRepository = itemRepository;
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

        List<Item> items = invoiceData.items().stream().map(i -> new Item(null, i.name(), i.value(), i.amount(), savedInvoice.id())).toList();
        itemRepository.saveAll(items);

        return savedInvoice;
    }

    public Optional<Invoice> getInvoiceById(String id) {
        return invoiceRepository.findById(id);
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

