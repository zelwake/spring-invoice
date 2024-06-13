package dev.zelwake.spring_postman.invoiceItem;

import dev.zelwake.spring_postman.invoice.Status;
import dev.zelwake.spring_postman.item.ItemDTO;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
public class InvoiceItemRepositoryImpl implements InvoiceItemRepository {

    private final JdbcTemplate jdbcTemplate;

    public InvoiceItemRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public InvoiceItem findInvoiceWithItemsByInvoiceId(UUID id) {
        String sql = "SELECT i.id, i.invoice_number, i.issued_on, i.expected_on, " +
                "i.paid_on, i.status, i.amount as invoice_amount, i.customer_id, " +
                "it.id AS item_id, it.name AS item_name, it.value AS item_value, it.amount AS item_amount " +
                "FROM invoice AS i " +
                "LEFT JOIN item AS it ON i.id = it.invoice_id " +
                "WHERE i.id = ?";

        return jdbcTemplate.query(sql, new InvoiceItemResultSetExtractor(), id);
    }

    private static class InvoiceItemResultSetExtractor implements ResultSetExtractor<InvoiceItem> {
        @Override
        public InvoiceItem extractData(ResultSet rs) throws SQLException {
            UUID invoiceId = null;
            String invoiceNumber = null;
            LocalDate issuedOn = null;
            LocalDate expectedOn = null;
            LocalDate paidOn = null;
            Status status = null;
            int amount = 0;
            UUID customerId = null;
            List<ItemDTO> items = new ArrayList<>();

            while (rs.next()) {
                if (invoiceId == null) {
                    invoiceId = UUID.fromString(rs.getString("id"));
                    invoiceNumber = rs.getString("invoice_number");
                    issuedOn = LocalDate.from(rs.getTimestamp("issued_on").toLocalDateTime());
                    expectedOn = LocalDate.from(rs.getTimestamp("expected_on").toLocalDateTime());
                    if (rs.getTimestamp("paid_on") != null)
                        paidOn = LocalDate.from(rs.getTimestamp("paid_on").toLocalDateTime());
                    status = Status.valueOf(rs.getString("status"));
                    amount = rs.getInt("invoice_amount");
                    customerId = UUID.fromString(rs.getString("customer_id"));
                }

                String itemName = rs.getString("item_name");
                if (itemName != null) {
                    int itemValue = rs.getInt("item_value");
                    int itemAmount = rs.getInt("item_amount");
                    items.add(new ItemDTO(itemName, itemValue, itemAmount));
                }
            }

            return new InvoiceItem(
                    invoiceId,
                    invoiceNumber,
                    issuedOn,
                    expectedOn,
                    paidOn,
                    status,
                    amount,
                    customerId,
                    items
            );
        }
    }
}

