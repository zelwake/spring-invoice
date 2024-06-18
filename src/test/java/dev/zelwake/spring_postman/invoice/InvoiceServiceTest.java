package dev.zelwake.spring_postman.invoice;

import dev.zelwake.spring_postman.item.ItemRequestDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InvoiceServiceTest {

    List<ItemRequestDTO> items = new ArrayList<>();

    @BeforeEach
    public void init() {
        items.clear();

        items.add(new ItemRequestDTO("one", 123.45f, 15));
        items.add(new ItemRequestDTO("two", 678.90f, 10));
        items.add(new ItemRequestDTO("three", 1234.56f, 5));
    }

    @Test
    public void testTotalPriceInCents() {
        int sum = InvoiceService.calcPriceInCents(items);
        int expectedSum = 12345 * 15 + 67890 * 10 + 123456 * 5;

        assertEquals(expectedSum, sum);
    }

}