package com.springboot.car.service;

import java.time.LocalDate;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.springboot.car.dto.PaymentDto;
import com.springboot.car.exception.InvalidInputException;
import com.springboot.car.exception.ResourceNotFoundException;
import com.springboot.car.model.Booking;
import com.springboot.car.model.Customer;
import com.springboot.car.model.Payment;
import com.springboot.car.repository.BookingRepository;
import com.springboot.car.repository.CustomerRepository;
import com.springboot.car.repository.PaymentRepository;

@Service
public class PaymentService {

    private final Logger logger = LoggerFactory.getLogger(PaymentService.class);

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private PaymentDto paymentDto;

    public PaymentDto addPayment(Payment payment, int bookingId, String username) {
        logger.info("Adding payment");

        Customer customer = customerRepository.getCustomerByUsername(username);
        if (customer == null) {
            throw new InvalidInputException("Invalid username");
        }

        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new InvalidInputException("Invalid booking ID"));

        payment.setCustomer(customer);
        payment.setBooking(booking);
        payment.setPaymentDate(LocalDate.now());

        Payment saved = paymentRepository.save(payment);
        return paymentDto.convertPaymentIntoDto(saved);
    }

    public void deletePayment(int id, String username) {
        logger.info("Deleting payment");

        Customer customer = customerRepository.getCustomerByUsername(username);
        if (customer == null) {
            throw new InvalidInputException("Invalid username");
        }

        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new InvalidInputException("Invalid payment ID"));

        if (payment.getCustomer().getId() != customer.getId()) {
            throw new InvalidInputException("Unauthorized to delete this payment");
        }

        paymentRepository.deleteById(id);
    }

    public PaymentDto updatePayment(int id, Payment updated, String username) {
        logger.info("Updating payment");

        Customer customer = customerRepository.getCustomerByUsername(username);
        if (customer == null) {
            throw new InvalidInputException("Invalid username");
        }

        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new InvalidInputException("Invalid payment ID"));

        if (payment.getCustomer().getId() != customer.getId()) {
            throw new InvalidInputException("Unauthorized to update this payment");
        }

        if (updated.getMethod() != null) payment.setMethod(updated.getMethod());
        if (updated.getStatus() != null) payment.setStatus(updated.getStatus());

        Payment saved = paymentRepository.save(payment);
        return paymentDto.convertPaymentIntoDto(saved);
    }
    
    public List<PaymentDto> getAllPayments(int page, int size) {
        logger.info("Fetching all payments with paging");
        Pageable pageable = PageRequest.of(page, size);
        List<Payment> payments = paymentRepository.findAll(pageable).getContent();
        return paymentDto.convertPaymentIntoDto(payments);
    }

    public PaymentDto getPaymentById(int id) {
        logger.info("Fetching payment");
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Payment not found"));
        return paymentDto.convertPaymentIntoDto(payment);
    }

   
    public List<PaymentDto> getPaymentsByCustomerId(int customerId) {
        logger.info("Fetching payments");
        List<Payment> payments = paymentRepository.findByCustomerId(customerId);
        if (payments == null || payments.isEmpty()) {
            throw new ResourceNotFoundException("No payments found for customer ID");
        }
        return paymentDto.convertPaymentIntoDto(payments);
    }

    public PaymentDto getPaymentByBookingId(int bookingId) {
        logger.info("Fetching payment by booking ID");
        Payment payment = paymentRepository.findByBookingId(bookingId);
        if (payment == null) {
            throw new ResourceNotFoundException("Payment not found for booking ID");
        }
        return paymentDto.convertPaymentIntoDto(payment);
    }

      
}









