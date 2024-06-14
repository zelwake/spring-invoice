package dev.zelwake.spring_postman.customerInvoiceItem;

import dev.zelwake.spring_postman.customer.CustomerNameDTO;
import dev.zelwake.spring_postman.invoice.Status;
import dev.zelwake.spring_postman.item.ItemDTO;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class CustomerInvoiceItemRepositoryImpl implements CustomerInvoiceItemRepository {

    private final JdbcTemplate jdbcTemplate;

    public CustomerInvoiceItemRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<CustomerInvoiceItem> findCustomerInvoiceItemById(UUID id) {

        String sql = "SELECT i.*, " +
                     "	c.name AS customer_name, " +
                     "	it.name AS item_name, it.amount AS item_amount, it.value AS item_value FROM ( " +
                     "	SELECT id, invoice_number, amount AS invoice_amount, issued_on, expected_on, paid_on, status, customer_id " +
                     "	FROM invoice " +
                     "	WHERE id = ? " +
                     "	) AS i " +
                     "  JOIN customer AS c ON i.customer_id = c.id " +
                     "  JOIN item AS it ON i.id = it.invoice_id; ";

        return jdbcTemplate.query(sql, new CustomerInvoiceItemSetExtractor(), id);
    }

    private static class CustomerInvoiceItemSetExtractor implements ResultSetExtractor<Optional<CustomerInvoiceItem>> {

        @Override
        public Optional<CustomerInvoiceItem> extractData(ResultSet rs) throws SQLException, DataAccessException {

            UUID id = null;
            String invoiceNumber = null;
            int amount = 0;
            LocalDate issuedOn = null;
            LocalDate expectedOn = null;
            LocalDate paidOn = null;
            Status status = null;

            UUID customerId = null;
            String customerName = null;

            List<ItemDTO> items = new ArrayList<>();

            while(rs.next()) {
                id = UUID.fromString(rs.getString("id"));
                invoiceNumber = rs.getString("invoice_number");
                amount = rs.getInt("invoice_amount");
                issuedOn = LocalDate.from(rs.getTimestamp("issued_on").toLocalDateTime());
                expectedOn = LocalDate.from(rs.getTimestamp("expected_on").toLocalDateTime());
                Timestamp paidOnObject = rs.getTimestamp("expected_on");
                paidOn = paidOnObject != null ? LocalDate.from(paidOnObject.toLocalDateTime()) : null;
                status = Status.valueOf(rs.getString("status"));
                customerId = UUID.fromString(rs.getString("customer_id"));
                customerName = rs.getString("customer_name");


                String itemName = rs.getString("item_name");
                if (itemName != null) {
                    int itemValue = rs.getInt("item_value");
                    int itemAmount = rs.getInt("item_amount");
                    items.add(new ItemDTO(itemName, itemValue, itemAmount));
                }
            }
            if (id != null) {
                CustomerNameDTO customer = new CustomerNameDTO(customerId, customerName);
                CustomerInvoiceItem customerInvoiceItem = new CustomerInvoiceItem(id, invoiceNumber, amount, issuedOn, expectedOn, paidOn, status, customer, items);

                return Optional.of(customerInvoiceItem);
            }

            return Optional.empty();
        }
    }
}
