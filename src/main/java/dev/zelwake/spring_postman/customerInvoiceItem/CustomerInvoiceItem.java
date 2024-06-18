package dev.zelwake.spring_postman.customerInvoiceItem;

import dev.zelwake.spring_postman.customer.CustomerNameDTO;
import dev.zelwake.spring_postman.invoice.Status;
import dev.zelwake.spring_postman.invoiceCustomer.BaseInvoiceCustomer;
import dev.zelwake.spring_postman.item.ItemDTO;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record CustomerInvoiceItem(
        UUID id,
        String invoiceNumber,
        Integer price,
        LocalDate issuedOn,
        LocalDate expectedOn,
        LocalDate paidOn,
        Status status,
        CustomerNameDTO customer,
        List<ItemDTO> items
) implements BaseInvoiceCustomer {
}
