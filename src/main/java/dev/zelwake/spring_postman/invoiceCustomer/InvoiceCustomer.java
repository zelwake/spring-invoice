package dev.zelwake.spring_postman.invoiceCustomer;

import dev.zelwake.spring_postman.customer.CustomerNameDTO;
import dev.zelwake.spring_postman.invoice.Status;

import java.time.LocalDate;
import java.util.UUID;

public record InvoiceCustomer(
        UUID id,
        String invoiceNumber,
        LocalDate issuedOn,
        LocalDate expectedOn,
        LocalDate paidOn,
        Status status,
        Integer amount,
        CustomerNameDTO customer
//        int numberOfItems
) {
}
