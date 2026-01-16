package com.springboot.car.dto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.springboot.car.model.OwnerPayout;

@Component
public class OwnerPayoutDto {
    private int id;
    private double amount;
    private String status;
    private LocalDate paymentDate;
    private int bookingId;
    private int ownerId;

    public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public LocalDate getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(LocalDate paymentDate) {
		this.paymentDate = paymentDate;
	}

	public int getBookingId() {
		return bookingId;
	}

	public void setBookingId(int bookingId) {
		this.bookingId = bookingId;
	}

	public int getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(int ownerId) {
		this.ownerId = ownerId;
	}

	public OwnerPayoutDto convertToDto(OwnerPayout payout) {
        OwnerPayoutDto dto = new OwnerPayoutDto();
        dto.setId(payout.getId());
        dto.setAmount(payout.getAmount());
        dto.setStatus(payout.getStatus());
        dto.setPaymentDate(payout.getPaymentDate());
        dto.setBookingId(payout.getBooking().getId());
        dto.setOwnerId(payout.getCarOwner().getId());
        return dto;
    }

    public List<OwnerPayoutDto> convertToDto(List<OwnerPayout> list) {
        List<OwnerPayoutDto> dtos = new ArrayList<>();
        for (OwnerPayout payout : list) {
            dtos.add(convertToDto(payout));
        }
        return dtos;
    }

   
}
