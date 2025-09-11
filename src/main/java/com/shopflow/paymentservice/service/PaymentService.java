package com.shopflow.paymentservice.service;

import com.shopflow.paymentservice.model.Payment;
import com.shopflow.paymentservice.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }

    public Optional<Payment> getPaymentById(Long id) {
        return paymentRepository.findById(id);
    }

    public Payment createPayment(Payment payment) {
        payment.setPaymentDate(LocalDateTime.now());
        payment.setStatus("PROCESSING");
        return paymentRepository.save(payment);
    }

    public Payment updatePayment(Long id, Payment paymentDetails) {
        Payment payment = paymentRepository.findById(id).orElseThrow(() -> new RuntimeException("Payment not found"));
        payment.setAmount(paymentDetails.getAmount());
        payment.setStatus(paymentDetails.getStatus());
        return paymentRepository.save(payment);
    }

    public void deletePayment(Long id) {
        paymentRepository.deleteById(id);
    }

    @KafkaListener(topics = "order_created_events", groupId = "payment_group")
    public void handleOrderCreatedEvent(String orderId) {
        System.out.println("Received order created event for order ID: " + orderId);
        // Simulate payment processing
        Payment payment = new Payment();
        payment.setOrderId(Long.parseLong(orderId));
        payment.setAmount(100.00); // Dummy amount
        payment.setPaymentDate(LocalDateTime.now());
        payment.setStatus("COMPLETED");
        paymentRepository.save(payment);
        System.out.println("Payment processed for order ID: " + orderId);
    }
}
