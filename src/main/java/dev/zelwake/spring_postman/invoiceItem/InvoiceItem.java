package dev.zelwake.spring_postman.invoiceItem;

import dev.zelwake.spring_postman.invoice.Status;
import dev.zelwake.spring_postman.item.Item;
import dev.zelwake.spring_postman.item.ItemDTO;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record InvoiceItem(
        UUID id,
        String invoiceNumber,
        LocalDate issuedOn,
        LocalDate expectedOn,
        LocalDate paidOn,
        Status status,
        Integer amount,
        UUID customerId,
        List<ItemDTO> items
) {
}
