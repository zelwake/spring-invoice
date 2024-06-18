package dev.zelwake.spring_postman.invoice;

import dev.zelwake.spring_postman.customerInvoiceItem.CustomerInvoiceItemService;
import dev.zelwake.spring_postman.invoiceCustomer.InvoiceCustomerService;
import dev.zelwake.spring_postman.item.ItemDTO;
import dev.zelwake.spring_postman.item.ItemRequestDTO;
import dev.zelwake.spring_postman.item.ItemService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class InvoiceServiceTest {

    private List<ItemRequestDTO> items;

    @Mock
    private InvoiceRepository invoiceRepository;
    @Mock
    private ItemService itemService;
    @Mock
    private InvoiceCustomerService invoiceCustomerService;
    @Mock
    private CustomerInvoiceItemService customerInvoiceItemService;

    @InjectMocks
    private InvoiceService invoiceService;

    @BeforeEach
    void setUp() {
        items = new ArrayList<>();
        items.add(new ItemRequestDTO("one", 123.45f, 15));
        items.add(new ItemRequestDTO("two", 678.90f, 10));
        items.add(new ItemRequestDTO("three", 1234.56f, 5));
    }

    @Test
    void testTotalPriceInCents() {
        int sum = invoiceService.calcPriceInCents(items);
        int expectedSum = 12345 * 15 + 67890 * 10 + 123456 * 5;

        assertEquals(expectedSum, sum);
    }

    @Test
    void testDTOConversion() {
        List<ItemDTO> convertedItems = invoiceService.asItemRequestDTOList(items);

        assertEquals(items.size(), convertedItems.size());
        for (int i = 0; i < convertedItems.size(); i++) {
            assertEquals(items.get(i).amount(), convertedItems.get(i).amount());
            assertEquals((int) (items.get(i).price() * 100), convertedItems.get(i).priceInCents());
            assertEquals(items.get(i).name(), convertedItems.get(i).name());
        }
    }

    @Test
    void testDTORequestConversion() {
        List<ItemDTO> dtoItems = new ArrayList<>();
        dtoItems.add(new ItemDTO("one", 12345, 15));
        dtoItems.add(new ItemDTO("two", 67890, 10));
        dtoItems.add(new ItemDTO("three", 123456, 5));

        List<ItemRequestDTO> convertedItems = invoiceService.asItemDTOList(dtoItems);

        assertEquals(items.size(), convertedItems.size());
        for (int i = 0; i < dtoItems.size(); i++) {
            assertEquals(items.get(i).amount(), convertedItems.get(i).amount());
            assertEquals(items.get(i).price(), convertedItems.get(i).price());
            assertEquals(items.get(i).name(), convertedItems.get(i).name());
        }
    }
}