package com.springboot.car;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.springboot.car.dto.ReviewDto;
import com.springboot.car.exception.ResourceNotFoundException;
import com.springboot.car.model.Booking;
import com.springboot.car.model.Customer;
import com.springboot.car.model.Review;
import com.springboot.car.repository.BookingRepository;
import com.springboot.car.repository.CustomerRepository;
import com.springboot.car.repository.ReviewRepository;
import com.springboot.car.service.ReviewService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ReviewServiceTest {

    @InjectMocks
    private ReviewService reviewService;

    @Mock
    private BookingRepository bookingRepository;

    @Mock
    private ReviewRepository reviewRepository;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private Principal principal;

    private Customer customer;
    private Booking booking;
    private Review review;

    @BeforeEach
    public void setup() {
        customer = new Customer();
        customer.setId(1);
        customer.setName("customerUser");

        booking = new Booking();
        booking.setId(100);
        booking.setCustomer(customer);

        review = new Review();
        review.setId(10);
        review.setComment("Good car");
        review.setRating("4");
        review.setCustomer(customer);
        review.setBooking(booking);
    }

    @Test
    public void postReviewTest() {
        when(principal.getName()).thenReturn("customerUser");
        when(customerRepository.getCustomerByUsername("customerUser")).thenReturn(customer);
        when(bookingRepository.getBooking(customer.getId(), 5)).thenReturn(Optional.of(booking));
        when(reviewRepository.save(any(Review.class))).thenReturn(review);

        ReviewDto dto = reviewService.postReview(5, review, principal);
        assertEquals("Good car", dto.getComment());

      // customer not found
        when(customerRepository.getCustomerByUsername("customerUser")).thenReturn(null);
        ResourceNotFoundException ex1 = assertThrows(ResourceNotFoundException.class, () -> {
            reviewService.postReview(5, review, principal);
        });
        assertEquals("Customer not found with username: customerUser", ex1.getMessage());

       
        when(customerRepository.getCustomerByUsername("customerUser")).thenReturn(customer);

        // Failure: booking not found
        when(bookingRepository.getBooking(customer.getId(), 5)).thenReturn(Optional.empty());
        ResourceNotFoundException ex2 = assertThrows(ResourceNotFoundException.class, () -> {
            reviewService.postReview(5, review, principal);
        });
        assertEquals("Customer hasn't booked this car", ex2.getMessage());
    }

   

    @Test
    public void updateReviewTest() {
        when(reviewRepository.findById(10)).thenReturn(Optional.of(review));
        when(principal.getName()).thenReturn("customerUser");
        when(customerRepository.getCustomerByUsername("customerUser")).thenReturn(customer);
        when(reviewRepository.save(any(Review.class))).thenReturn(review);

        Review updatedReview = new Review();
        updatedReview.setComment("Updated comment");
        updatedReview.setRating("5");

        // Success case
        ReviewDto dto = reviewService.updateReview(10, updatedReview, principal);
        assertEquals("Updated comment", dto.getComment());
        assertEquals("5", dto.getRating());

        // Failure: review not found
        when(reviewRepository.findById(999)).thenReturn(Optional.empty());
        ResourceNotFoundException ex1 = assertThrows(ResourceNotFoundException.class, () -> {
            reviewService.updateReview(999, updatedReview, principal);
        });
        assertTrue(ex1.getMessage().toLowerCase().contains("review not found"));

        // Failure: customer not found
        when(reviewRepository.findById(10)).thenReturn(Optional.of(review));
        when(customerRepository.getCustomerByUsername("customerUser")).thenReturn(null);
        IllegalStateException ex2 = assertThrows(IllegalStateException.class, () -> {
            reviewService.updateReview(10, updatedReview, principal);
        });
        assertTrue(ex2.getMessage().toLowerCase().contains("unauthorized"));

        // Failure: rating invalid (not numeric)
        when(customerRepository.getCustomerByUsername("customerUser")).thenReturn(customer);
        updatedReview.setRating("invalid");
        IllegalArgumentException ex3 = assertThrows(IllegalArgumentException.class, () -> {
            reviewService.updateReview(10, updatedReview, principal);
        });
        assertTrue(ex3.getMessage().toLowerCase().contains("rating must be numeric"));

        // Failure: rating out of bounds
        updatedReview.setRating("6");
        IllegalArgumentException ex4 = assertThrows(IllegalArgumentException.class, () -> {
            reviewService.updateReview(10, updatedReview, principal);
        });
        assertTrue(ex4.getMessage().toLowerCase().contains("between 1 and 5"));
    }

    @Test
    public void getReviewsByCarTest() {
        List<Review> reviews = new ArrayList<>();
        reviews.add(review);
        when(reviewRepository.findReviewsByCarId(5)).thenReturn(reviews);

        List<ReviewDto> dtos = reviewService.getReviewsByCar(5);
        assertEquals(1, dtos.size());
        assertEquals("Good car", dtos.get(0).getComment());
    }

    @Test
    public void getReviewsByCustomerTest() {
        List<Review> reviews = new ArrayList<>();
        reviews.add(review);

        when(principal.getName()).thenReturn("customerUser");
        when(customerRepository.getCustomerByUsername("customerUser")).thenReturn(customer);
        when(reviewRepository.findByCustomerId(customer.getId())).thenReturn(reviews);

        // Success case
        List<ReviewDto> dtos = reviewService.getReviewsByCustomer(principal);
        assertEquals(1, dtos.size());

        // Failure case: customer not found
        when(customerRepository.getCustomerByUsername("customerUser")).thenReturn(null);
        ResourceNotFoundException ex1 = assertThrows(ResourceNotFoundException.class, () -> {
            reviewService.getReviewsByCustomer(principal);
        });
        assertTrue(ex1.getMessage().toLowerCase().contains("customer not found"));

        // Failure case: no reviews found
        when(customerRepository.getCustomerByUsername("customerUser")).thenReturn(customer);
        when(reviewRepository.findByCustomerId(customer.getId())).thenReturn(new ArrayList<>());
        ResourceNotFoundException ex2 = assertThrows(ResourceNotFoundException.class, () -> {
            reviewService.getReviewsByCustomer(principal);
        });
        assertTrue(ex2.getMessage().toLowerCase().contains("no reviews found"));
    }

    @Test
    public void deleteReviewTest() {
        when(reviewRepository.findById(10)).thenReturn(Optional.of(review));
        when(principal.getName()).thenReturn("customerUser");
        when(customerRepository.getCustomerByUsername("customerUser")).thenReturn(customer);
        doNothing().when(reviewRepository).deleteById(10);

        // Success case
        reviewService.deleteReview(10, principal);

        // Failure case: review not found
        when(reviewRepository.findById(999)).thenReturn(Optional.empty());
        ResourceNotFoundException ex1 = assertThrows(ResourceNotFoundException.class, () -> {
            reviewService.deleteReview(999, principal);
        });
        assertTrue(ex1.getMessage().toLowerCase().contains("review not found"));

        // Failure case: unauthorized user
        when(reviewRepository.findById(10)).thenReturn(Optional.of(review));
        when(customerRepository.getCustomerByUsername("customerUser")).thenReturn(null);
        IllegalStateException ex2 = assertThrows(IllegalStateException.class, () -> {
            reviewService.deleteReview(10, principal);
        });
        assertTrue(ex2.getMessage().toLowerCase().contains("unauthorized"));
    }
}
