package dev.zelwake.spring_postman.invoiceCustomer;

import dev.zelwake.spring_postman.customer.CustomerNameDTO;
import dev.zelwake.spring_postman.invoice.Status;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
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

    private static InvoiceCustomer mapRowToInvoiceCustomer(ResultSet rs) throws SQLException {
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

        return new InvoiceCustomer(id, invoiceNumber, issuedOn, expectedOn, paidOn, status, amount, customer);
    }

    @Override
    public Optional<InvoiceCustomer> findInvoiceWithCustomerNameById(UUID id) {
        String sql = "SELECT i.*, c.name AS customer_name, COUNT(*) AS number_of_items FROM invoice AS i " +
                "JOIN customer AS c ON i.customer_id = c.id " +
                "WHERE i.id = ?";

        return jdbcTemplate.query(sql, new InvoiceCustomerResultSetExtractor(), id);
    }

    @Override
    public Page<InvoiceCustomer> findAllInvoiceWithCustomerName(Pageable pageable) {
        int limit = pageable.getPageSize();
        long offset = pageable.getOffset();
        String sortClause = "";

        String countSql = "SELECT COUNT(*) FROM invoice";
        Integer total = jdbcTemplate.queryForObject(countSql, Integer.class);
        if (total == null) {
            total = 0;
        }

        String sql = "SELECT i.*, c.name AS customer_name FROM invoice AS i " +
                "JOIN customer AS c ON i.customer_id = c.id " +
                sortClause +
                " " +
                "LIMIT ? OFFSET ?";

        List<InvoiceCustomer> invoiceCustomers = jdbcTemplate.query(sql, new ListInvoiceCustomerResultSetExtractor(), limit, offset);

        if (invoiceCustomers == null) {
            invoiceCustomers = new ArrayList<>(); // IDEa is telling me that invoiceCustomers might be null even tho it is returning empty ArrayList
        }
        return new PageImpl<>(invoiceCustomers, pageable, total);
    }

    private static class InvoiceCustomerResultSetExtractor implements ResultSetExtractor<Optional<InvoiceCustomer>> {

        @Override
        public Optional<InvoiceCustomer> extractData(ResultSet rs) throws SQLException, DataAccessException {
            if (rs.next()) {
                return Optional.of(mapRowToInvoiceCustomer(rs));
            }
            return Optional.empty();
        }
    }

    private static class ListInvoiceCustomerResultSetExtractor implements ResultSetExtractor<List<InvoiceCustomer>> {

        @Override
        public List<InvoiceCustomer> extractData(ResultSet rs) throws SQLException, DataAccessException {
            List<InvoiceCustomer> invoiceCustomers = new ArrayList<>();
            while (rs.next()) {
                invoiceCustomers.add(mapRowToInvoiceCustomer(rs));
            }
            return invoiceCustomers;
        }
    }
}
