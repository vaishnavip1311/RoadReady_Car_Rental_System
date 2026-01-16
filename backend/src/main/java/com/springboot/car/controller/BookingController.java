package com.springboot.car.controller;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.car.dto.BookingDto;
import com.springboot.car.dto.CarDto;
import com.springboot.car.dto.CustomerDto;
import com.springboot.car.model.Booking;
import com.springboot.car.service.BookingService;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
public class BookingController {

    private Logger logger = LoggerFactory.getLogger(BookingController.class);

    @Autowired
    private BookingService bookingService;
      @Autowired
    private CustomerDto customerDto;
    @Autowired
    private CarDto carDto;
    @Autowired
    private BookingDto bookingDto;

    @PostMapping("/api/customer/book/car/{carId}")
    public ResponseEntity<BookingDto> addBooking(
            @PathVariable int carId,
            @RequestBody Booking booking,
            Principal principal) {

        Booking saved = bookingService.addBooking(principal.getName(), carId, booking);
        BookingDto dto = bookingDto.convertBookingtoDto(saved); // Convert one booking
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }
    
    @GetMapping("/api/customer/recent-booking/{carId}")
    public ResponseEntity<BookingDto> getRecentBooking(@PathVariable int carId) {
        Booking booking = bookingService.getLatestBookingByCarId(carId);
        return ResponseEntity.ok(bookingDto.convertBookingtoDto(booking));
    }



//    @GetMapping("/api/customer/book/get-car/{carId}")
//    public ResponseEntity<?> getCustomerByCarId(
//            @PathVariable int carId,
//            @RequestParam(name = "page", defaultValue = "0") int page,
//            @RequestParam(name = "size", defaultValue = "10") int size) {
//        logger.info("Fetching customer list for car");
//        return ResponseEntity.status(HttpStatus.OK).body(bookingService.getCustomerByCarId(carId, page, size));
//    }
    
    @GetMapping("/api/customer/book/get-car/{carId}")
    public ResponseEntity<?> getCustomerByCarId(
            @PathVariable int carId) {
        logger.info("Fetching customer list for car");
        return ResponseEntity.status(HttpStatus.OK).body(bookingService.getBookingByCarId(carId));
    }

    @GetMapping("/api/book/get-car/{carId}")
    public ResponseEntity<?> getBookingByCarId(
            @PathVariable int carId) {
        logger.info("Fetching customer list for car");
        return ResponseEntity.status(HttpStatus.OK).body(bookingService.getAllBookingById(carId));
    }

    @GetMapping("/api/car/book/customer")
    public ResponseEntity<?> getCarByCustomerId(
            Principal principal,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size) {
        logger.info("Fetching car list booked by customer");
        List<CarDto> carDtos = bookingService.getCarByCustomerId(principal.getName(), page, size);
        return ResponseEntity.status(HttpStatus.OK).body(carDtos);
    }

    
 


    @PutMapping("/api/customer/booking/update/{bookingId}")
    public ResponseEntity<?> updateBooking(@PathVariable int bookingId,
                                           @RequestBody Booking bookingDetails,
                                           Principal principal) {
        logger.info("Booking updated");
        
        Booking updatedBooking = bookingService.updateBooking(bookingId, bookingDetails, principal.getName());
        
        return ResponseEntity.ok(bookingDto.convertBookingIntoDto(List.of(updatedBooking)));
    }

    @DeleteMapping("/api/customer/booking/cancel/{bookingId}")
    public ResponseEntity<?> cancelBooking(@PathVariable int bookingId, Principal principal) {
        logger.info("Booking cancelled");
        
        bookingService.cancelBooking(bookingId, principal.getName());
        
        return ResponseEntity.ok("Booking cancelled successfully");
    }


    @GetMapping("/api/customer/view/{bookingId}")
    public ResponseEntity<BookingDto> getBookingById(@PathVariable int bookingId) {
        Booking booking = bookingService.getBookingById(bookingId);
        BookingDto dto = bookingDto.convertBookingtoDto(booking);
        return ResponseEntity.ok(dto);
    }
    @GetMapping("api/manager/view-all-bookings")
    public ResponseEntity<?> viewAllBookings(
            Principal principal,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "6") int size) {

        logger.info("Manager viewing all bookings: {}", principal.getName());

        Pageable pageable = PageRequest.of(page, size);
        Page<Booking> bookings = bookingService.viewAllBookings(pageable);

        // Convert content to DTO
        List<BookingDto> dtoList = bookingDto.convertBookingIntoDto(bookings.getContent());

        // Return full Page structure with content
        Map<String, Object> response = new HashMap<>();
        response.put("content", dtoList);
        response.put("totalPages", bookings.getTotalPages());
        response.put("totalElements", bookings.getTotalElements());
        response.put("currentPage", bookings.getNumber());

        return ResponseEntity.ok(response);
    }

    
    @PostMapping("/api/customer/payment/{bookingId}")
    public ResponseEntity<?> processPayment(@PathVariable int bookingId) {
        bookingService.completePayment(bookingId);
        return ResponseEntity.ok("Payment successful");
    }

    
    @GetMapping("/api/by-customer")
    public ResponseEntity<?> getMyBookings(Principal principal) {
        String username = principal.getName(); // This gets the logged-in user's username
        List<BookingDto> bookings = bookingService.getBookingsByUsername(username);

        if (bookings.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No bookings found for user: " + username);
        }
        return ResponseEntity.ok(bookings);
    }
    
    @GetMapping("/api/by-customer/{customerId}")
    public ResponseEntity<?> getBookingsByCustomer(@PathVariable int customerId) {
        List<BookingDto> bookings = bookingService.getBookingsByCustomerId(customerId);

        if (bookings.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No bookings found for customer ID: " + customerId);
        }

        return ResponseEntity.ok(bookings);
    }

    
    @GetMapping("/api/booking/un-paid")
    public ResponseEntity<List<BookingDto>> getUnpaidBookings() {
        return ResponseEntity.ok(bookingService.getUnpaidBookings());
    }
    
  

}





