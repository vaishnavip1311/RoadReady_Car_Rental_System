package com.springboot.car.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.springboot.car.model.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Integer> {
    List<Payment> findByCustomerId(int customerId);
    @Query("select p from Payment p where p.booking.id = ?1")
    Payment findByBookingId(int bookingId);
   
}












//
//
//@Query("select p from Payment p where lower(p.method) like lower(concat('%', :keyword, '%')) " +
//	       "or lower(p.status) like lower(concat('%', :keyword, '%'))")
//Page<Payment> searchByStatusOrMethod(String keyword, Pageable pageable);