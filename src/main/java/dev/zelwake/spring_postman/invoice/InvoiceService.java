package dev.zelwake.spring_postman.invoice;

import dev.zelwake.spring_postman.customerInvoiceItem.CustomerInvoiceItem;
import dev.zelwake.spring_postman.customerInvoiceItem.CustomerInvoiceItemDTO;
import dev.zelwake.spring_postman.customerInvoiceItem.CustomerInvoiceItemService;
import dev.zelwake.spring_postman.invoiceCustomer.InvoiceCustomer;
import dev.zelwake.spring_postman.invoiceCustomer.InvoiceCustomerService;
import dev.zelwake.spring_postman.item.ItemDTO;
import dev.zelwake.spring_postman.item.ItemRequestDTO;
import dev.zelwake.spring_postman.item.ItemService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
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

    public Invoice addInvoice(InvoiceRequestDTO invoiceData) {
        Integer totalPriceInCents = null;
        try {
            totalPriceInCents = calcPriceInCents(invoiceData.items());
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
                totalPriceInCents,
                invoiceData.customerId()
        );

        Invoice savedInvoice = invoiceRepository.save(newInvoice);
        List<ItemDTO> items = asItemRequestDTOList(invoiceData.items());

        boolean itemsDidSave = itemService.saveItems(items, savedInvoice.id());
        if (!itemsDidSave)
            System.out.println("Items had error saving to database");

        return savedInvoice;
    }

    public CustomerInvoiceItem getInvoiceById(UUID id) {
        Optional<CustomerInvoiceItem> customerFromDB = customerInvoiceItemService.getInvoiceWithCustomerItemsById(id);
        if (customerFromDB.isEmpty()) {
            return null;
        }
        CustomerInvoiceItemDTO customer = asCustomerInvoiceItemDTO(customerFromDB.get());
        return customerInvoiceItemService.getInvoiceWithCustomerItemsById(id).orElse(null);
    }

    private CustomerInvoiceItemDTO asCustomerInvoiceItemDTO(CustomerInvoiceItem item) {
        return new CustomerInvoiceItemDTO(
                item.id(),
                item.invoiceNumber(),
                (float) (item.price() / 100),
                item.issuedOn(),
                item.expectedOn(),
                item.paidOn(),
                item.status(),
                item.customer(),
                asItemDTOList(item.items()));
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

    public Integer calcPriceInCents(List<ItemRequestDTO> items) {
        return items.stream().mapToInt(i -> Math.round(i.amount() * (i.price() * 100))).sum();
    }

    public List<ItemDTO> asItemRequestDTOList(List<ItemRequestDTO> items) {
        return items.stream().map(i -> new ItemDTO(i.name(), Math.round(i.price() * 100), i.amount())).toList();
    }

    public List<ItemRequestDTO> asItemDTOList(List<ItemDTO> items) {
        return items.stream().map(i -> new ItemRequestDTO(i.name(), (float) (i.priceInCents() / 100), i.amount())).toList();
    }
}
