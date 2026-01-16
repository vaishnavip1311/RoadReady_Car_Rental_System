package com.springboot.car.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.springboot.car.dto.CarMaintenanceDto;
import com.springboot.car.exception.ResourceNotFoundException;
import com.springboot.car.model.Car;
import com.springboot.car.model.CarMaintenance;
import com.springboot.car.model.Manager;
import com.springboot.car.repository.CarMaintenanceRepository;
import com.springboot.car.repository.CarRepository;
import com.springboot.car.repository.ManagerRepository;

@Service
public class CarMaintenanceService {

    @Autowired
    private CarMaintenanceRepository maintenanceRepository;

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private ManagerRepository managerRepository;

    @Autowired
    private CarMaintenanceDto carMaintenanceDto;  

    public CarMaintenanceDto addMaintenance(CarMaintenance maintenance, int carId, String username) {
        Car car = carRepository.findById(carId)
                .orElseThrow(() -> new ResourceNotFoundException("Invalid Car ID"));

        Manager manager = managerRepository.findByUserUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Manager not found for logged-in user"));

        maintenance.setCar(car);
        maintenance.setManager(manager);

        CarMaintenance saved = maintenanceRepository.save(maintenance);
        return carMaintenanceDto.convertToDto(saved);
    }


    public List<CarMaintenanceDto> getAllMaintenanceRecords(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        List<CarMaintenance> list = maintenanceRepository.findAll(pageable).getContent();
        return carMaintenanceDto.convertToDto(list);
    }

    public List<CarMaintenanceDto> getMaintenanceRecordsByCar(int carId) {
        Car car = carRepository.findById(carId)
                .orElseThrow(() -> new ResourceNotFoundException("Car Not Found"));
        List<CarMaintenance> list = maintenanceRepository.findByCar(car);
        return carMaintenanceDto.convertToDto(list);
    }

    public void deleteMaintenanceRecord(int id) {
        CarMaintenance maintenance = maintenanceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Maintenance record not found with id: " + id));
        maintenanceRepository.delete(maintenance);
    }

    public CarMaintenanceDto update(int id, CarMaintenance maintenance) {
        CarMaintenance existing = maintenanceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Maintenance record not found with id: " + id));
        if (maintenance.getType() != null) {
            existing.setType(maintenance.getType());
        }
       
        if (maintenance.getRemarks() != null) {
            existing.setRemarks(maintenance.getRemarks());
        }
        if (maintenance.getDate() != null) {
            existing.setDate(maintenance.getDate());
        }
        if (maintenance.getCar() != null) {
            int carId = maintenance.getCar().getId();
            Car car = carRepository.findById(carId)
                    .orElseThrow(() -> new ResourceNotFoundException("Car not found with id: " + carId));
            existing.setCar(car);
        }
        if (maintenance.getManager() != null) {
            int managerId = maintenance.getManager().getId();
            Manager manager = managerRepository.findById(managerId)
                    .orElseThrow(() -> new ResourceNotFoundException("Manager not found with id: " + managerId));
            existing.setManager(manager);
        }

        CarMaintenance saved = maintenanceRepository.save(existing);
        return carMaintenanceDto.convertToDto(saved);
    }

    public CarMaintenanceDto getMaintenanceById(int id) {
        CarMaintenance maintenance = maintenanceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Maintenance record not found with id: " + id));
        return carMaintenanceDto.convertToDto(maintenance);
    }
}










