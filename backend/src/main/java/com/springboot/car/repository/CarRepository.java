package com.springboot.car.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.springboot.car.model.Car;

public interface CarRepository extends JpaRepository<Car,Integer>{
	 @Query("select c from Car c where c.pricePerDay between ?1 and ?2")
	    List<Car> getCarsByPriceRange(double min, double max, Pageable pageable);

	    Page<Car> findByAvailabilityStatus(String availabilityStatus, Pageable pageable);
	    List<Car> findByCarOwner_Id(int ownerId);
	    Page<Car> findByCarOwner_Name(String username,Pageable pageable);
	    Page<Car> findByAvailabilityStatusAndPricePerDayBetween(String availabilityStatus, double min, double max, Pageable pageable);


}





//@Query("select c from Car c where c.brand = ?1 and c.model = ?2")
//List<Car> filterCars(String brand, String model, Pageable pageable);