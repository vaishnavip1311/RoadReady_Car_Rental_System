package com.springboot.car.dto;


import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.springboot.car.model.Payment;

@Component
public class PaymentDto {

    private int id;
    private String method;
    private String status;
    private String paymentDate;

    // Getters and Setters
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getMethod() {
        return method;
    }
    public void setMethod(String method) {
        this.method = method;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public String getPaymentDate() {
        return paymentDate;
    }
    public void setPaymentDate(String paymentDate) {
        this.paymentDate = paymentDate;
    }

  
    public PaymentDto convertPaymentIntoDto(Payment payment) {
        PaymentDto dto = new PaymentDto();
        dto.setId(payment.getId());
        dto.setMethod(payment.getMethod());
        dto.setStatus(payment.getStatus());
        dto.setPaymentDate(payment.getPaymentDate() != null ? payment.getPaymentDate().toString() : null);
        return dto;
    }

   
    public List<PaymentDto> convertPaymentIntoDto(List<Payment> paymentList) {
        List<PaymentDto> dtoList = new ArrayList<>();
        for (Payment payment : paymentList) {
            PaymentDto dto = convertPaymentIntoDto(payment);
            dtoList.add(dto);
        }
        return dtoList;
    }
}

