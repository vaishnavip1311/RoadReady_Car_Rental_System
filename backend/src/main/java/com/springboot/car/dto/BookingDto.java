package com.springboot.car.dto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.springboot.car.model.Booking;

@Component
public class BookingDto {

    private int id;
    private String bookingStatus;
    private double totalAmount;
    private LocalDate pickupDate;
    private LocalDate dropoffDate;
    private String location; 
    private String customerName;
    private String carBrand;
    private String carModel;
    private String carName; 

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBookingStatus() {
        return bookingStatus;
    }

    public void setBookingStatus(String bookingStatus) {
        this.bookingStatus = bookingStatus;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public LocalDate getPickupDate() {
        return pickupDate;
    }

    public void setPickupDate(LocalDate pickupDate) {
        this.pickupDate = pickupDate;
    }

    public LocalDate getDropoffDate() {
        return dropoffDate;
    }

    public void setDropoffDate(LocalDate dropoffDate) {
        this.dropoffDate = dropoffDate;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCarBrand() {
        return carBrand;
    }

    public void setCarBrand(String carBrand) {
        this.carBrand = carBrand;
    }

    public String getCarModel() {
        return carModel;
    }

    public void setCarModel(String carModel) {
        this.carModel = carModel;
    }

    public String getCarName() {
        return carName;
    }

    public void setCarName(String carName) {
        this.carName = carName;
    }

    public static List<BookingDto> convertBookingIntoDto(List<Booking> list) {
        List<BookingDto> listDto = new ArrayList<>();
        list.forEach(booking -> {
            BookingDto dto = new BookingDto();
            dto.setId(booking.getId());
            dto.setBookingStatus(booking.getBookingStatus());
            dto.setTotalAmount(booking.getTotalAmount());
            dto.setPickupDate(booking.getPickupDate());
            dto.setDropoffDate(booking.getDropoffDate());
            dto.setLocation(booking.getLocation());

            String brand = booking.getCar().getBrand();
            String model = booking.getCar().getModel();

            dto.setCustomerName(booking.getCustomer().getName());
            dto.setCarBrand(brand);
            dto.setCarModel(model);
            dto.setCarName(brand + " " + model); 
            listDto.add(dto);
        });
        return listDto;
    }
    
    
    public BookingDto convertBookingtoDto(Booking booking) {
        BookingDto dto = new BookingDto();
        dto.setId(booking.getId());
        dto.setBookingStatus(booking.getBookingStatus());
        dto.setTotalAmount(booking.getTotalAmount());
        dto.setPickupDate(booking.getPickupDate());
        dto.setDropoffDate(booking.getDropoffDate());
        dto.setLocation(booking.getLocation());
        dto.setCustomerName(booking.getCustomer().getName());
        dto.setCarBrand(booking.getCar().getBrand());
        dto.setCarModel(booking.getCar().getModel());
        dto.setCarName(booking.getCar().getBrand() + " " + booking.getCar().getModel());
        return dto;
    }

}
