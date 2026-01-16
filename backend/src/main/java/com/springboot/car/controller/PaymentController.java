package com.springboot.car.controller;

import java.security.Principal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.car.model.Payment;
import com.springboot.car.service.PaymentService;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {

    private Logger logger = LoggerFactory.getLogger(PaymentController.class);

    @Autowired
    private PaymentService paymentService;

  
    @PostMapping("/add")
    public ResponseEntity<?> addPayment(@RequestBody Payment payment,
                                        @RequestParam int bookingId,
                                        Principal principal) {
        logger.info("Adding new payment");
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(paymentService.addPayment(payment, bookingId, principal.getName()));
    }

    @DeleteMapping("/cancel/{id}")
    public ResponseEntity<?> deletePayment(@PathVariable int id, Principal principal) {
        logger.info("Deleting payment");
        paymentService.deletePayment(id, principal.getName());
        return ResponseEntity.ok("Payment deleted successfully");
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updatePayment(@PathVariable int id,
                                           @RequestBody Payment payment,
                                           Principal principal) {
        logger.info("Updating payment");
        return ResponseEntity.ok(paymentService.updatePayment(id, payment, principal.getName()));
    }

    @GetMapping("/get-all")
    public ResponseEntity<?> getAllPayments(
            @RequestParam(name = "page", required = false, defaultValue = "0") int page,
            @RequestParam(name = "size", required = false, defaultValue = "1000000") int size) {
       
        return ResponseEntity.status(HttpStatus.OK).body(paymentService.getAllPayments(page, size));
    }

    @GetMapping("/view/{id}")
    public ResponseEntity<?> getPayment(@PathVariable int id) {
        logger.info("Fetching payment with ID");
        return ResponseEntity.status(HttpStatus.OK).body(paymentService.getPaymentById(id));
    }
    
    @GetMapping("/customer/{customerId}")
    public ResponseEntity<?> getPaymentsByCustomer(@PathVariable int customerId) {
        logger.info("Fetching payments by customer ID");
        return ResponseEntity.status(HttpStatus.OK).body(paymentService.getPaymentsByCustomerId(customerId));
    }

    @GetMapping("/booking/{bookingId}")
    public ResponseEntity<?> getPaymentByBooking(@PathVariable int bookingId) {
        logger.info("Fetching payment by booking ID");
        return ResponseEntity.status(HttpStatus.OK).body(paymentService.getPaymentByBookingId(bookingId));
    }

   
}




