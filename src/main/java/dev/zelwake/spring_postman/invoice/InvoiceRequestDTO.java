package dev.zelwake.spring_postman.invoice;

import dev.zelwake.spring_postman.item.ItemDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record InvoiceDTO(
        @NotBlank(message = "Invoice number has to be specified") String invoiceNumber,
        @NotNull(message = "Need to set date when invoiced was issued") LocalDate issuedOn,
        LocalDate expectedOn,
        @NotNull(message = "Customer id is not set") UUID customerId,
        @NotNull(message = "At least one item has to be provided (name, value, amount)") List<ItemDTO> items
) implements BaseInvoice {
}
