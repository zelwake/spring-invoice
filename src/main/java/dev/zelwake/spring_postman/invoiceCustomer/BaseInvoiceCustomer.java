package dev.zelwake.spring_postman.invoiceCustomer;

import dev.zelwake.spring_postman.customer.CustomerNameDTO;
import dev.zelwake.spring_postman.invoice.Status;

import java.time.LocalDate;
import java.util.UUID;

public interface BaseInvoiceCustomer {
        UUID id();
        String invoiceNumber();
        LocalDate issuedOn();
        LocalDate expectedOn();
        LocalDate paidOn();
        Status status();
//        Float price();
        CustomerNameDTO customer();
}
