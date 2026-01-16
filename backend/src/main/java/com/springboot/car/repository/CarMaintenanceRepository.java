package com.springboot.car.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.car.model.Car;
import com.springboot.car.model.CarMaintenance;

public interface CarMaintenanceRepository extends JpaRepository<CarMaintenance, Integer> {
	List<CarMaintenance> findByCar(Car car);
}
