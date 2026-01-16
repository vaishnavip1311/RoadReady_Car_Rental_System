package com.springboot.car.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.car.model.ReturnLog;

public interface ReturnLogRepository extends JpaRepository<ReturnLog, Integer> {
	


}
