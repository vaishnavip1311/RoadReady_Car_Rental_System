package com.springboot.car.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.springboot.car.model.Manager;

public interface ManagerRepository extends JpaRepository<Manager, Integer> {

	
	 @Query("select a from Manager a where a.user.username = ?1")
	    Manager getManagerByUsername(String username);
	 
	 Optional<Manager> findByUserUsername(String username);
}