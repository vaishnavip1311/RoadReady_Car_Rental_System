package com.springboot.car.service;

import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.springboot.car.dto.BookingDto;
import com.springboot.car.dto.CarDto;
import com.springboot.car.dto.CustomerDto;
import com.springboot.car.exception.AccessDeniedException;
import com.springboot.car.exception.InvalidInputException;
import com.springboot.car.exception.NotBookedAnyCarException;
import com.springboot.car.exception.ResourceNotFoundException;
import com.springboot.car.model.Booking;
import com.springboot.car.model.Car;
import com.springboot.car.model.Customer;
import com.springboot.car.repository.BookingRepository;
import com.springboot.car.repository.CarRepository;
import com.springboot.car.repository.CustomerRepository;
import com.springboot.car.repository.ManagerRepository;

@Service
public class BookingService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CarRepository carRepository;
    @Autowired
    private ManagerRepository managerRepository;

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private CustomerDto customerDto;

    @Autowired
    private CarDto carDto;

    @Autowired
    private BookingDto bookingDto;

    public Booking addBooking(String username, int carId, Booking booking) {
        Customer customer = customerRepository.getCustomerByUsername(username);
        if (customer == null) {
            throw new ResourceNotFoundException("Customer not found by username");
        }

        Car car = carRepository.findById(carId)
                .orElseThrow(() -> new ResourceNotFoundException("Car ID Invalid"));

        if ("Not Available".equalsIgnoreCase(car.getAvailabilityStatus())) {
            throw new IllegalStateException("Car is already booked");
        }

        booking.setCustomer(customer);
        booking.setCar(car);
        booking.setBookingStatus("Booked"); 

        // Calculate total amount
        if (booking.getPickupDate() == null || booking.getDropoffDate() == null) {
            throw new IllegalArgumentException("Pickup and Drop dates must be provided");
        }

        long daysBetween = ChronoUnit.DAYS.between(booking.getPickupDate(), booking.getDropoffDate());
        if (daysBetween <= 0) {
            throw new IllegalArgumentException("Drop date must be after pickup date");
        }

        double totalAmount = daysBetween * car.getPricePerDay();
        booking.setTotalAmount(totalAmount);
        
        // Update car availability
        car.setAvailabilityStatus("NOT AVAILABLE");
        carRepository.save(car);

        return bookingRepository.save(booking);
    }


    public BookingDto getBookingByCarId(int carId) {
        carRepository.findById(carId)
                .orElseThrow(() -> new InvalidInputException("Car ID Invalid"));

        Booking booking = bookingRepository.findByCarId(carId)
                .orElseThrow(() -> new InvalidInputException("No booking found for this car"));

        return bookingDto.convertBookingtoDto(booking);
    }
    
    public List<BookingDto> getAllBookingById(int carId) {
        carRepository.findById(carId)
            .orElseThrow(() -> new InvalidInputException("Car ID Invalid"));

        List<Booking> bookings = bookingRepository.findAllByCarId(carId);

        // ✅ Instead of throwing an exception, just return an empty list
        if (bookings.isEmpty()) {
            return new ArrayList<>();
        }

        return BookingDto.convertBookingIntoDto(bookings);
    }


    public Booking getLatestBookingByCarId(int carId) {
        carRepository.findById(carId)
            .orElseThrow(() -> new ResourceNotFoundException("Car not found"));

        return bookingRepository.findTopByCarIdOrderByDropoffDateDesc(carId)
            .orElseThrow(() -> new ResourceNotFoundException("No bookings found for this car"));
    }



  

    public List<CarDto> getCarByCustomerId(String username, int page, int size) {
        Customer customer = customerRepository.getCustomerByUsername(username);
        if (customer == null) {
            throw new ResourceNotFoundException("Customer not found by username");
        }

        Pageable pageable = PageRequest.of(page, size);
        Page<Car> cars = bookingRepository.getCarByCustomerId(customer.getId(), pageable);


        if (cars == null || cars.isEmpty()) {
            throw new NotBookedAnyCarException("Customer has not booked any car");
        }

        return carDto.convertCarIntoDto(cars.getContent());
    }

 

   

    public Booking updateBooking(int bookingId, Booking bookingDetails, String username) {
        Booking existingBooking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new ResourceNotFoundException("Booking ID Invalid"));

        // Check access
        if (existingBooking.getCustomer() != null &&
            !existingBooking.getCustomer().getUser().getUsername().equals(username)) {
            throw new AccessDeniedException("You are not authorized to update this booking");
        }

        // Update dates if provided
        if (bookingDetails.getPickupDate() != null) {
            existingBooking.setPickupDate(bookingDetails.getPickupDate());
        }

        if (bookingDetails.getDropoffDate() != null) {
            existingBooking.setDropoffDate(bookingDetails.getDropoffDate());
        }

        // Update location if provided
        if (bookingDetails.getLocation() != null && !bookingDetails.getLocation().isBlank()) {
            existingBooking.setLocation(bookingDetails.getLocation());
        }

        // Recalculate total amount if both dates are available
        if (existingBooking.getPickupDate() != null && existingBooking.getDropoffDate() != null) {
            long days = ChronoUnit.DAYS.between(existingBooking.getPickupDate(), existingBooking.getDropoffDate());
            if (days <= 0) {
                throw new IllegalArgumentException("Dropoff date must be after pickup date");
            }
            double pricePerDay = existingBooking.getCar().getPricePerDay();
            existingBooking.setTotalAmount(days * pricePerDay);
        }

        return bookingRepository.save(existingBooking);
    }


    public void cancelBooking(int bookingId, String username) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new ResourceNotFoundException("Booking ID Invalid"));

     
        if (booking.getCustomer()!=null && !booking.getCustomer().getUser().getUsername().equals(username)) {
            throw new AccessDeniedException("You are not authorized to cancel this booking");
        }

        Car car = booking.getCar();
        car.setAvailabilityStatus("AVAILABLE");
        carRepository.save(car);

        // Delete booking from database
        bookingRepository.deleteById(bookingId);
    }


    public Booking getBookingById(int bookingId) {
        return bookingRepository.findById(bookingId)
            .orElseThrow(() -> new ResourceNotFoundException("Booking not found with id: " + bookingId));
    }
    public void completePayment(int bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
            .orElseThrow(() -> new ResourceNotFoundException("Booking not found"));

        if (booking.getBookingStatus().equalsIgnoreCase("Paid")) {
            throw new IllegalStateException("Booking already paid");
        }

        booking.setBookingStatus("Paid");
        bookingRepository.save(booking);
    }

    
    public Page<Booking> viewAllBookings(Pageable pageable) {
        return bookingRepository.findAll(pageable);
    }

    public List<BookingDto> getBookingsByUsername(String username) {
        Customer customer = customerRepository.getCustomerByUsername(username);
        if (customer == null) {
            throw new UsernameNotFoundException("Customer not found with username: " + username);
        }

        List<Booking> bookings = bookingRepository.findByCustomerId(customer.getId());
        return BookingDto.convertBookingIntoDto(bookings);
    }
    
    public List<BookingDto> getUnpaidBookings() {
        List<Booking> bookings = bookingRepository.findByPaymentStatus("unpaid");
        return bookingDto.convertBookingIntoDto(bookings);
    }
    public List<BookingDto> getBookingsByCustomerId(int customerId) {
        Customer customer = customerRepository.findById(customerId)
            .orElseThrow(() -> new ResourceNotFoundException("Customer not found with ID: " + customerId));

        List<Booking> bookings = bookingRepository.findByCustomer(customer);

        return bookingDto.convertBookingIntoDto(bookings);
    }


}












