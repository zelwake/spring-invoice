package dev.zelwake.spring_postman.customerInvoiceItem;

import dev.zelwake.spring_postman.customer.CustomerNameDTO;
import dev.zelwake.spring_postman.invoice.Status;
import dev.zelwake.spring_postman.invoiceCustomer.BaseInvoiceCustomer;
import dev.zelwake.spring_postman.item.ItemDTO;
import dev.zelwake.spring_postman.item.ItemRequestDTO;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record CustomerInvoiceItemDTO(
        UUID id,
        String invoiceNumber,
        Float price,
        LocalDate issuedOn,
        LocalDate expectedOn,
        LocalDate paidOn,
        Status status,
        CustomerNameDTO customer,
        List<ItemRequestDTO> items
) implements BaseInvoiceCustomer {
}
