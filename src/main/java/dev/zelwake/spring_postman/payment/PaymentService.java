package dev.zelwake.spring_postman.payment;

import dev.zelwake.spring_postman.invoice.Invoice;
import dev.zelwake.spring_postman.invoice.InvoiceRepository;
import dev.zelwake.spring_postman.invoice.Status;
import dev.zelwake.spring_postman.utils.PaginatedResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;

    private final InvoiceRepository invoiceRepository;

    public PaymentService(PaymentRepository paymentRepository, InvoiceRepository invoiceRepository) {
        this.paymentRepository = paymentRepository;
        this.invoiceRepository = invoiceRepository;
    }

    public PaginatedResponse<Payment> getAll(Pageable pageable) {
        Page<Payment> payments = paymentRepository.findAll(PageRequest.of(
                pageable.getPageNumber(),
                pageable.getPageSize(),
                pageable.getSortOr(Sort.by(Sort.Direction.ASC, "date"))
        ));

        return new PaginatedResponse<>(payments.getTotalPages(), payments.getTotalElements(), payments.getNumber(), payments.getContent());
    }

    public PaginatedResponse<Payment> getAllByInvoiceId(UUID id, Pageable pageable) {
        Page<Payment> payments = paymentRepository.findAllByInvoiceId(id, PageRequest.of(
                pageable.getPageNumber(),
                pageable.getPageSize(),
                pageable.getSortOr(Sort.by(Sort.Direction.ASC, "date"))
        ));

        return new PaginatedResponse<>(payments.getTotalPages(), payments.getTotalElements(), payments.getNumber(), payments.getContent());
    }

    public Payment createPayment(PaymentRequestDTO payment) {
        try {
            Optional<Invoice> invoice = invoiceRepository.findById(payment.invoiceId().toString());
            if (invoice.isEmpty()) {
                return null;
            }

            Payment savedPayment = paymentRepository.save(new Payment(null, (int) Math.round(payment.amount() * 100), payment.date(), payment.invoiceId()));
            int invoiceTotalPriceInCents = invoice.get().totalPriceInCents();
            int payments = paymentRepository.findSumOfPriceById(payment.invoiceId());
            int remainingToBePaid = invoiceTotalPriceInCents - payments;
            if (remainingToBePaid <= 0 && invoice.get().status() == Status.PENDING) {
                Invoice _i = invoice.get();
                invoiceRepository.save(new Invoice(_i.id(), _i.invoiceNumber(), _i.issuedOn(), _i.expectedOn(), LocalDate.now(), Status.PAID, _i.totalPriceInCents(), _i.customerId()));
            }

            return savedPayment;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
