package com.springboot.car.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.springboot.car.model.CarOwner;

public interface CarOwnerRepository extends JpaRepository<CarOwner, Integer> {
    
    @Query("select co from CarOwner co where co.user.username = ?1")
    CarOwner getCarOwnerByUsername(String username);
    
   
}
