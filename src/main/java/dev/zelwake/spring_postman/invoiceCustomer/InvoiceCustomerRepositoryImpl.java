package dev.zelwake.spring_postman.invoiceCustomer;

import dev.zelwake.spring_postman.customer.CustomerNameDTO;
import dev.zelwake.spring_postman.invoice.Status;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

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

    @Override
    public List<InvoiceCustomer> findAllInvoiceWithCustomerName(Pageable pageable) {
        int limit = pageable.getPageSize();
        long offset = pageable.getOffset();

        String sql = "WITH total_count AS ( " +
                "SELECT COUNT(*) AS total_count " +
                "FROM invoice " +
                ") " +
                "SELECT " +
                "(SELECT total_count FROM total_count) AS number_of_items, " +
                "i.*, " +
                "c.name AS customer_name " +
                "FROM invoice AS i " +
                "JOIN customer AS c ON i.customer_id = c.id " +
                "LIMIT ? OFFSET ?";

        return jdbcTemplate.query(sql, new ListInvoiceCustomerResultSetExtractor(), limit, offset);
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
            int numberOfItems = 0;

            while (rs.next()) {
                if (id == null) {
                    id = UUID.fromString(rs.getString("id"));
                    invoiceNumber = rs.getString("invoice_number");
                    issuedOn = LocalDate.from(rs.getTimestamp("issued_on").toLocalDateTime());
                    expectedOn = LocalDate.from(rs.getTimestamp("expected_on").toLocalDateTime());
                    if (rs.getTimestamp("paid_on") != null)
                        paidOn = LocalDate.from(rs.getTimestamp("paid_on").toLocalDateTime());
                    status = Status.valueOf(rs.getString("status"));
                    amount = rs.getInt("amount");

                    String customerName = rs.getString("customer_name");
                    UUID customerId = UUID.fromString(rs.getString("customer_id"));
                    customer = new CustomerNameDTO(customerId, customerName);

                    numberOfItems = rs.getInt("number_of_items");
                }

            }

            return new InvoiceCustomer(id, invoiceNumber, issuedOn, expectedOn, paidOn, status, amount, customer, numberOfItems);
        }
    }

    private static class ListInvoiceCustomerResultSetExtractor implements ResultSetExtractor<List<InvoiceCustomer>>  {

        @Override
        public List<InvoiceCustomer> extractData(ResultSet rs) throws SQLException, DataAccessException {
            List<InvoiceCustomer> invoiceCustomers = new ArrayList<>();
            while (rs.next()) {
                UUID id = UUID.fromString(rs.getString("id"));
                String invoiceNumber = rs.getString("invoice_number");
                LocalDate issuedOn = LocalDate.from(rs.getTimestamp("issued_on").toLocalDateTime());
                LocalDate expectedOn = LocalDate.from(rs.getTimestamp("expected_on").toLocalDateTime());
                LocalDate paidOn = rs.getTimestamp("paid_on") != null ? LocalDate.from(rs.getTimestamp("paid_on").toLocalDateTime()) : null;
                Status status = Status.valueOf(rs.getString("status"));
                int amount = rs.getInt("amount");
                UUID customerId = UUID.fromString(rs.getString("customer_id"));
                String customerName = rs.getString("customer_name");
                CustomerNameDTO customer = new CustomerNameDTO(customerId, customerName);
                int numberOfItems = rs.getInt("number_of_items");

                InvoiceCustomer invoiceCustomer = new InvoiceCustomer(id, invoiceNumber, issuedOn, expectedOn, paidOn, status, amount, customer, numberOfItems);
                invoiceCustomers.add(invoiceCustomer);
            }
            return invoiceCustomers;
        }
    }
}
