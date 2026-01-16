package com.springboot.car.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.springboot.car.model.Customer;

public interface CustomerRepository extends JpaRepository<Customer,Integer>{
	@Query("select c from Customer c where c.user.username=?1")
	Customer getCustomerByUsername(String username);


	@Query("select c from Customer c where lower(c.name) like lower(concat('%', ?1, '%')) or lower(c.email) like lower(concat('%', ?1, '%'))")
	Page<Customer> searchCustomers(String keyword, Pageable pageable);

	
}
