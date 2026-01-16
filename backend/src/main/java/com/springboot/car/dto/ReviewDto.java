package com.springboot.car.dto;

import java.util.ArrayList;
import java.util.List;

import com.springboot.car.model.Review;

public class ReviewDto {

	
    private int id;
    private String comment;
    private String rating;
    private String customerName;
    private int carId;

    
    
    public int getId() {
		return id;
	}



	public void setId(int id) {
		this.id = id;
	}



	public String getComment() {
		return comment;
	}



	public void setComment(String comment) {
		this.comment = comment;
	}



	public String getRating() {
		return rating;
	}



	public void setRating(String rating) {
		this.rating = rating;
	}



	public String getCustomerName() {
		return customerName;
	}



	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}



	public int getCarId() {
		return carId;
	}



	public void setCarId(int carId) {
		this.carId = carId;
	}



	 public static ReviewDto convertToDto(Review review) {
	        ReviewDto dto = new ReviewDto();
	        dto.setId(review.getId());
	        dto.setComment(review.getComment());
	        dto.setRating(review.getRating());
	        if (review.getCustomer() != null) {
	            dto.setCustomerName(review.getCustomer().getName());
	        }

	        if (review.getBooking() != null && review.getBooking().getCar() != null) {
	            dto.setCarId(review.getBooking().getCar().getId());
	        }

	        return dto;
	    }

	    public static List<ReviewDto> convertToDtoList(List<Review> reviews) {
	        List<ReviewDto> dtoList = new ArrayList<>();
	        for (Review review : reviews) {
	            dtoList.add(convertToDto(review));
	        }
	        return dtoList;
	    }
	    
}
