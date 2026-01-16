package com.springboot.car.controller;

import java.security.Principal;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.springboot.car.model.Review;
import com.springboot.car.service.ReviewService;

@RestController
@RequestMapping("/api/review")
@CrossOrigin(origins = "http://localhost:5173")
public class ReviewController {

    private Logger logger = LoggerFactory.getLogger(ReviewController.class);

    @Autowired
    private ReviewService reviewService;

    @PostMapping("/add/{carId}")
    public ResponseEntity<?> postReview(@PathVariable int carId,
                                             @RequestBody Review review,
                                             Principal principal) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(reviewService.postReview(carId, review, principal));
    }


    
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateReview(@PathVariable int id, @RequestBody Review review, Principal principal) {
        logger.info("Updating review");
        return ResponseEntity.status(HttpStatus.OK)
                .body(reviewService.updateReview(id, review, principal));
    }

    @GetMapping("/car/{carId}")
    public ResponseEntity<?> getReviewsByCar(@PathVariable int carId) {
        logger.info("Fetching reviews for car");
        return ResponseEntity.status(HttpStatus.OK)
                .body(reviewService.getReviewsByCar(carId));
    }

    @GetMapping("/customer")
    public ResponseEntity<?> getReviewsByCustomer(Principal principal) {
        return ResponseEntity.ok(reviewService.getReviewsByCustomer(principal));
    }


    @DeleteMapping("/delete/{reviewId}")
    public ResponseEntity<String> deleteReview(@PathVariable int reviewId, Principal principal) {
        reviewService.deleteReview(reviewId, principal);
        return ResponseEntity.ok("Review deleted successfully");
    }

}







