package com.springboot.car.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.springboot.car.model.Booking;
import com.springboot.car.model.Car;
import com.springboot.car.model.Customer;

public interface BookingRepository extends JpaRepository<Booking,Integer> {
	@Query("select b from Booking b where b.customer.id=?1 and b.car.id=?2")
	Optional<Booking> getBooking(int customerID,int carId);
	
	
	@Query("SELECT b FROM Booking b WHERE b.customer.id = ?1 AND b.car.id = ?2 ORDER BY b.pickupDate DESC")
	List<Booking> getBookingsByCustomerAndCar(int customerId, int carId);

	
	@Query("select b.customer from Booking b where b.car.id = ?1")
	Page<Customer> getCustomerByCarId(int carId, Pageable pageable);
	List<Booking> findAllByCarId(int carId);
	Optional<Booking> findTopByCarIdOrderByDropoffDateDesc(int carId);
	Optional<Booking> findById(Integer id);

	Page<Booking> findAll(Pageable pageable);
	List<Booking> findByCustomer(Customer customer);
	Optional<Booking> findByCarId(int carId);


	@Query("select b.car from Booking b where b.customer.id=?1")
	Page<Car> getCarByCustomerId(int customerId,Pageable pageable);
	 List<Booking> findByCustomerId(int customerId);
	 
	 List<Booking> findByPaymentStatus(String paymentStatus);

}
