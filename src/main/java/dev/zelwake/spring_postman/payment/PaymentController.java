package dev.zelwake.spring_postman.payment;

import dev.zelwake.spring_postman.exceptions.ResourceNotFound;
import dev.zelwake.spring_postman.utils.PaginatedResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @GetMapping
    public ResponseEntity<PaginatedResponse<Payment>> getAllPayments(Pageable pageable) {
        PaginatedResponse<Payment> paymentList = paymentService.getAll(pageable);
        if (paymentList.content() == null)
            throw new ResourceNotFound("No payments exists");

        return ResponseEntity.ok(paymentList);
    }

    @GetMapping("/invoice/{id}")
    public ResponseEntity<PaginatedResponse<Payment>> getAllPaymentsByInvoiceId(@PathVariable UUID id, Pageable pageable) {
        System.out.println("Called api/payment/invoice/" + id);
        PaginatedResponse<Payment> paymentList = paymentService.getAllByInvoiceId(id, pageable);
        if (paymentList.content() == null)
            throw new ResourceNotFound("No payments exists");

        return ResponseEntity.ok(paymentList);
    }
}
