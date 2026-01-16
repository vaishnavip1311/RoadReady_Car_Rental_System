package com.springboot.car.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.springboot.car.dto.OwnerPayoutDto;
import com.springboot.car.exception.ResourceNotFoundException;
import com.springboot.car.model.Booking;
import com.springboot.car.model.CarOwner;
import com.springboot.car.model.OwnerPayout;
import com.springboot.car.model.User;
import com.springboot.car.repository.BookingRepository;
import com.springboot.car.repository.CarOwnerRepository;
import com.springboot.car.repository.OwnerPayoutRepository;

@Service
public class OwnerPayoutService {

    @Autowired
    private OwnerPayoutRepository payoutRepo;

    @Autowired
    private BookingRepository bookingRepo;

    @Autowired
    private CarOwnerRepository carOwnerRepo;

    @Autowired
    private OwnerPayoutDto ownerPayoutDto;

    public OwnerPayoutDto createPayout(int bookingId) {
        Booking booking = bookingRepo.findById(bookingId)
            .orElseThrow(() -> new ResourceNotFoundException("Invalid booking ID"));

        if ("cancelled".equalsIgnoreCase(booking.getBookingStatus())) {
            throw new IllegalStateException("No payout for cancelled bookings.");
        }

        if ("paid".equalsIgnoreCase(booking.getPaymentStatus())) {
            throw new IllegalStateException("Payout has already been made for this booking.");
        }

        CarOwner owner = booking.getCar().getCarOwner();

        double rent = booking.getTotalAmount(); 
        double commission = 0.6 * rent; 
        double ownerAmount = rent - commission;

        OwnerPayout payout = new OwnerPayout();
        payout.setAmount(ownerAmount);
        payout.setCarOwner(owner);
        payout.setBooking(booking);
        payout.setStatus("paid");
        payout.setPaymentDate(LocalDate.now());

        
        booking.setPaymentStatus("paid");
        bookingRepo.save(booking);

        OwnerPayout saved = payoutRepo.save(payout);
        return ownerPayoutDto.convertToDto(saved);
    }


    public List<OwnerPayoutDto> getAllPayouts(Pageable pageable) {
        Page<OwnerPayout> pageResult = payoutRepo.findAll(pageable);
        return ownerPayoutDto.convertToDto(pageResult.getContent());
    }


    public List<OwnerPayoutDto> getPayoutsByOwnerUsername(String username, int page, int size) {
        CarOwner owner = carOwnerRepo.getCarOwnerByUsername(username);
        if (owner == null) {
            throw new ResourceNotFoundException("Owner not found with username: " + username);
        }
        Pageable pageable = PageRequest.of(page, size);
        Page<OwnerPayout> pageResult = payoutRepo.findByCarOwnerId(owner.getId(), pageable);
        return ownerPayoutDto.convertToDto(pageResult.getContent());
    }


}












