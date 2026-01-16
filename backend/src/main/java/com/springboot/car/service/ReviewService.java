package com.springboot.car.service;

import java.security.Principal;
import java.util.List;

import org.springframework.stereotype.Service;

import com.springboot.car.dto.ReviewDto;
import com.springboot.car.exception.ResourceNotFoundException;
import com.springboot.car.model.Booking;
import com.springboot.car.model.Car;
import com.springboot.car.model.Customer;
import com.springboot.car.model.Review;
import com.springboot.car.repository.BookingRepository;
import com.springboot.car.repository.CarRepository;
import com.springboot.car.repository.CustomerRepository;
import com.springboot.car.repository.ReviewRepository;

@Service
public class ReviewService {

    private final BookingRepository bookingRepository;
    private final ReviewRepository reviewRepository;
    private final CustomerRepository customerRepository;
    private final CarRepository carRepository;

    public ReviewService(BookingRepository bookingRepository,
                         ReviewRepository reviewRepository,
                         CustomerRepository customerRepository,CarRepository carRepository) {
        this.bookingRepository = bookingRepository;
        this.reviewRepository = reviewRepository;
        this.customerRepository = customerRepository;
        this.carRepository = carRepository;
    }

    public ReviewDto postReview(int carId, Review review, Principal principal) {
        String username = principal.getName();
        Customer customer = customerRepository.getCustomerByUsername(username);
        if (customer == null) {
            throw new ResourceNotFoundException("Customer not found with username: " + username);
        }

        List<Booking> bookings = bookingRepository.getBookingsByCustomerAndCar(customer.getId(), carId);

        if (bookings.isEmpty()) {
            throw new ResourceNotFoundException("Customer hasn't booked this car");
        }

        Booking latestBooking = bookings.get(0); 


        Car car = carRepository.findById(carId) 
                .orElseThrow(() -> new ResourceNotFoundException("Car not found with ID: " + carId));

        review.setBooking(latestBooking);
        review.setCustomer(customer);
        review.setCar(car); 

        Review savedReview = reviewRepository.save(review);
        return ReviewDto.convertToDto(savedReview);
    }


   

    public ReviewDto updateReview(int reviewId, Review updatedReview, Principal principal) {
        Review existing = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ResourceNotFoundException("Review not found"));

        Customer customer = customerRepository.getCustomerByUsername(principal.getName());
        if (customer == null || existing.getCustomer().getId() != customer.getId()) {
            throw new IllegalStateException("Unauthorized to update this review");
        }

        if (updatedReview.getComment() != null) {
            existing.setComment(updatedReview.getComment());
        }
        if (updatedReview.getRating() != null) {
            try {
                int ratingValue = Integer.parseInt(updatedReview.getRating());
                if (ratingValue >= 1 && ratingValue <= 5) {
                    existing.setRating(updatedReview.getRating());
                } else {
                    throw new IllegalArgumentException("Rating must be between 1 and 5");
                }
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Rating must be numeric");
            }
        }

        Review savedReview = reviewRepository.save(existing);
        return ReviewDto.convertToDto(savedReview);
    }

    public List<ReviewDto> getReviewsByCar(int carId) {
        List<Review> reviews = reviewRepository.findReviewsByCarId(carId);
        return ReviewDto.convertToDtoList(reviews);
    }

    public List<ReviewDto> getReviewsByCustomer(Principal principal) {
        Customer customer = customerRepository.getCustomerByUsername(principal.getName());
        if (customer == null) {
            throw new ResourceNotFoundException("Customer not found with username: " + principal.getName());
        }

        List<Review> reviews = reviewRepository.findByCustomerId(customer.getId());
        if (reviews.isEmpty()) {
            throw new ResourceNotFoundException("No reviews found for customer");
        }

        return ReviewDto.convertToDtoList(reviews);
    }

    public void deleteReview(int reviewId, Principal principal) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ResourceNotFoundException("Review not found"));

        Customer customer = customerRepository.getCustomerByUsername(principal.getName());
        if (customer == null || review.getCustomer().getId() != customer.getId()) {
            throw new IllegalStateException("Unauthorized to delete this review");
        }

        reviewRepository.deleteById(reviewId);
    }
}









