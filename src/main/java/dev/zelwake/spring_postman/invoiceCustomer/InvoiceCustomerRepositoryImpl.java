package dev.zelwake.spring_postman.invoiceCustomer;

import dev.zelwake.spring_postman.customer.CustomerNameDTO;
import dev.zelwake.spring_postman.invoice.Status;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.UUID;

@Repository
public class InvoiceCustomerRepositoryImpl implements InvoiceCustomerRepository {

    private final JdbcTemplate jdbcTemplate;

    public InvoiceCustomerRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public InvoiceCustomer findInvoiceWithCustomerNameById(UUID id) {
        String sql = "SELECT i.*, c.name AS customer_name FROM invoice AS i " +
                "JOIN customer AS c ON i.customer_id = c.id " +
                "WHERE i.id = ?";

        return jdbcTemplate.query(sql, new InvoiceCustomerResultSetExtractor(), id);
    }

    private static class InvoiceCustomerResultSetExtractor implements ResultSetExtractor<InvoiceCustomer> {

        @Override
        public InvoiceCustomer extractData(ResultSet rs) throws SQLException, DataAccessException {
            UUID id = null;
            String invoiceNumber = null;
            LocalDate issuedOn = null;
            LocalDate expectedOn = null;
            LocalDate paidOn = null;
            Status status = null;
            int amount = 0;
            CustomerNameDTO customer = null;

            while (rs.next()) {
                if (id == null) {
                    id = UUID.fromString(rs.getString("id"));
                    invoiceNumber = rs.getString("invoice_number");
                    issuedOn = LocalDate.from(rs.getTimestamp("issued_on").toLocalDateTime());
                    expectedOn = LocalDate.from(rs.getTimestamp("expected_on").toLocalDateTime());
                    if (rs.getTimestamp("paid_on") != null)
                        paidOn = LocalDate.from(rs.getTimestamp("paid_on").toLocalDateTime());
                    status = Status.valueOf(rs.getString("status"));
                    amount = rs.getInt("invoice_amount");

                    String customerName = rs.getString("customer_name");
                    UUID customerId = UUID.fromString(rs.getString("customer_id"));
                    customer = new CustomerNameDTO(customerId, customerName);
                }

            }

            return new InvoiceCustomer(id, invoiceNumber, issuedOn, expectedOn, paidOn, status,amount, customer);
        }
    }
}
