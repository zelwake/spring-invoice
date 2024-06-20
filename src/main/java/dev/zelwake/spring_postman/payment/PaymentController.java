package dev.zelwake.spring_postman.payment;

import dev.zelwake.spring_postman.exceptions.ResourceNotFound;
import dev.zelwake.spring_postman.utils.PaginatedResponse;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
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

    @PostMapping
    public ResponseEntity<String> addPayment(@Valid @RequestBody PaymentRequestDTO payment) {
        if (payment.amount() <= 0)
            return ResponseEntity.badRequest().body("Amount has to be a positive number");

        Payment savedPayment = paymentService.createPayment(payment);
        if (savedPayment == null) {
            return ResponseEntity.badRequest().body("Could not create new payment.");
        }

        return ResponseEntity.created(URI.create(savedPayment.id().toString())).build();
    }

    @GetMapping("/invoice/{id}")
    public ResponseEntity<PaginatedResponse<Payment>> getAllPaymentsByInvoiceId(@PathVariable UUID id, Pageable pageable) {
        PaginatedResponse<Payment> paymentList = paymentService.getAllByInvoiceId(id, pageable);
        if (paymentList.content() == null)
            throw new ResourceNotFound("No payments exists");

        return ResponseEntity.ok(paymentList);
    }
}
