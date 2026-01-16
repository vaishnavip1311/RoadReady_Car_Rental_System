package com.springboot.car.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.springboot.car.model.Booking;
import com.springboot.car.model.Review;

public interface ReviewRepository extends JpaRepository<Review,Integer> {
	
	
	
	@Query("select r from Review r where r.car.id = ?1")
	List<Review> findReviewsByCarId(int carId);
	

	    List<Review> findByCustomerId(int customerId);
}
