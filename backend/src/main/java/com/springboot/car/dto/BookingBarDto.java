package com.springboot.car.dto;

import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class BookingBarDto {
    private List<String> carNames;
    private List<Integer> bookingCounts;

    public List<String> getCarNames() {
        return carNames;
    }

    public void setCarNames(List<String> carNames) {
        this.carNames = carNames;
    }

    public List<Integer> getBookingCounts() {
        return bookingCounts;
    }

    public void setBookingCounts(List<Integer> bookingCounts) {
        this.bookingCounts = bookingCounts;
    }
}

