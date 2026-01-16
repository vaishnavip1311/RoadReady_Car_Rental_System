package com.springboot.car.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.springboot.car.dto.ReturnLogDto;
import com.springboot.car.exception.InvalidInputException;
import com.springboot.car.exception.ResourceNotFoundException;
import com.springboot.car.model.Booking;
import com.springboot.car.model.Car;
import com.springboot.car.model.Manager;
import com.springboot.car.model.ReturnLog;
import com.springboot.car.repository.BookingRepository;
import com.springboot.car.repository.CarRepository;
import com.springboot.car.repository.ManagerRepository;
import com.springboot.car.repository.ReturnLogRepository;

@Service
public class ReturnLogService {

    @Autowired
    private ReturnLogRepository returnLogRepository;

    @Autowired
    private ManagerRepository managerRepository;

    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private CarRepository carRepository;

    @Autowired
    private ReturnLogDto returnLogDto;

    public ReturnLogDto addReturnLog(ReturnLog log, int bookingId, String managerUsername) {
        Booking booking = bookingRepository.findById(bookingId)
            .orElseThrow(() -> new ResourceNotFoundException("Booking ID Invalid"));

        Manager manager = managerRepository.getManagerByUsername(managerUsername);
        if (manager == null) {
            throw new ResourceNotFoundException("Manager not found");
        }

        if ("cancelled".equalsIgnoreCase(booking.getBookingStatus())) {
            throw new InvalidInputException("Cannot add ReturnLog for a cancelled booking.");
        }

       
        booking.setBookingStatus("Completed");
        bookingRepository.save(booking); 

       
        log.setBooking(booking);
        log.setManager(manager);
        log.setReturnDate(LocalDate.now());

        ReturnLog savedLog = returnLogRepository.save(log);

       
        Car car = booking.getCar();
        car.setAvailabilityStatus("AVAILABLE");
        carRepository.save(car);

        return returnLogDto.convertReturnLogToDto(savedLog);
    }



    public ReturnLogDto updateReturnLog(int id, ReturnLog updated) {
        ReturnLog existing = returnLogRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ReturnLog not found with ID: " + id));

        Booking booking = existing.getBooking();
        if (booking != null && "cancelled".equalsIgnoreCase(booking.getBookingStatus())) {
            throw new InvalidInputException("Cannot update ReturnLog linked to a cancelled booking.");
        }

        if (updated.getFuelLevel() != null) {
            existing.setFuelLevel(updated.getFuelLevel());
        }

        if (updated.getReturnDate() != null) {
            existing.setReturnDate(updated.getReturnDate());
        }

        ReturnLog saved = returnLogRepository.save(existing);
        return returnLogDto.convertReturnLogToDto(saved);
    }

    public List<ReturnLogDto> getAllReturnLogs(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        List<ReturnLog> returnLogs = returnLogRepository.findAll(pageable).getContent();
        return convertReturnLogsToDto(returnLogs);
    }

    public void deleteReturnLog(int id) {
        ReturnLog existing = returnLogRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ReturnLog not found with ID: " + id));

              returnLogRepository.deleteById(id);
    }

 

    public List<ReturnLogDto> convertReturnLogsToDto(List<ReturnLog> returnLogs) {
        List<ReturnLogDto> dtos = new ArrayList<>();
        for (ReturnLog log : returnLogs) {
            dtos.add(returnLogDto.convertReturnLogToDto(log));
        }
        return dtos;
    }
}






