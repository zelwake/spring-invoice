package dev.zelwake.spring_postman.payment;

import dev.zelwake.spring_postman.utils.PaginatedResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;

    public PaymentService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
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

    public Integer createPayment(PaymentRequestDTO payment) {
        try {
            Payment savedPayment = paymentRepository.save(new Payment(null, payment.date(), payment.invoiceId(), Math.round(payment.amount() * 100)));
            return savedPayment.id();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
