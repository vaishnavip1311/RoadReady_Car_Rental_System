package com.springboot.car.dto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.springboot.car.model.Car;

@Component
public class CarDto {
    private int id;

	private String brand;
    private String model;
    private String colour;
    private String fuelType;
	private String availabilityStatus;
	private int seats;
private double pricePerDay;
	private int year;
	private String pic;

   

	public String getPic() {
		return pic;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}

	public String getFuelType() {
		return fuelType;
	}

	public void setFuelType(String fuelType) {
		this.fuelType = fuelType;
	}

	public String getAvailabilityStatus() {
		return availabilityStatus;
	}

	public void setAvailabilityStatus(String availabilityStatus) {
		this.availabilityStatus = availabilityStatus;
	}

	public int getSeats() {
		return seats;
	}

	public void setSeats(int seats) {
		this.seats = seats;
	}

	public double getPricePerDay() {
		return pricePerDay;
	}

	public void setPricePerDay(double pricePerDay) {
		this.pricePerDay = pricePerDay;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    
	public String getColour() {
		return colour;
	}

	public void setColour(String colour) {
		this.colour = colour;
	}

	public List<CarDto> convertCarIntoDto(List<?> list) {
	    List<CarDto> listDto = new ArrayList<>();
	    for (Object item : list) {
	        if (item instanceof Car) {
	            Car car = (Car) item;
	            CarDto dto = new CarDto();
	            dto.setId(car.getId());
	            dto.setBrand(car.getBrand());
	            dto.setModel(car.getModel());
	            dto.setFuelType(car.getFuelType());
	            dto.setColour(car.getColour());
	            dto.setPricePerDay(car.getPricePerDay());
	            dto.setYear(car.getYear());
	            dto.setAvailabilityStatus(car.getAvailabilityStatus());
	            dto.setSeats(car.getSeats());
	            dto.setPic(car.getPic());
	            listDto.add(dto);
	        }
	    }
	   
	    return listDto;
	}


}
