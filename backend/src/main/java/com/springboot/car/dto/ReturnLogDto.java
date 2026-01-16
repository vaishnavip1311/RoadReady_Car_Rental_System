package com.springboot.car.dto;

import org.springframework.stereotype.Component;

import com.springboot.car.model.ReturnLog;

@Component
public class ReturnLogDto {

    public ReturnLogDto convertReturnLogToDto(ReturnLog log) {
        ReturnLogDto dto = new ReturnLogDto();
        dto.setId(log.getId());
        dto.setFuelLevel(log.getFuelLevel());
        dto.setReturnDate(log.getReturnDate());
        dto.setBookingId(log.getBooking().getId());
        dto.setManagerId(log.getManager().getId());
        dto.setCarName(log.getBooking().getCar().getBrand());
        return dto;
    }

    
    private String carName;
    private int id;
    private String fuelLevel;
    private java.time.LocalDate returnDate;
    private int bookingId;
    private int managerId;
    
	public String getCarName() {
		return carName;
	}

	public void setCarName(String carName) {
		this.carName = carName;
	}

	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	public String getFuelLevel() {
		return fuelLevel;
	}
	public void setFuelLevel(String fuelLevel) {
		this.fuelLevel = fuelLevel;
	}
	
	public java.time.LocalDate getReturnDate() {
		return returnDate;
	}
	public void setReturnDate(java.time.LocalDate returnDate) {
		this.returnDate = returnDate;
	}
	public int getBookingId() {
		return bookingId;
	}
	public void setBookingId(int bookingId) {
		this.bookingId = bookingId;
	}
	public int getManagerId() {
		return managerId;
	}
	public void setManagerId(int managerId) {
		this.managerId = managerId;
	}

    
}
