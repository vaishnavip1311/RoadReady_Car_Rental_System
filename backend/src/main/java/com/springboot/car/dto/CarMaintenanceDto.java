package com.springboot.car.dto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.springboot.car.model.CarMaintenance;
import com.springboot.car.model.Car;

@Component
public class CarMaintenanceDto {

    private int id;
    private String type;
    private String remarks;
    private LocalDate date;
    private String car;
    private int carId; // NEW FIELD

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getCar() {
        return car;
    }

    public void setCar(String car) {
        this.car = car;
    }

    public int getCarId() {
        return carId;
    }

    public void setCarId(int carId) {
        this.carId = carId;
    }

    public CarMaintenanceDto convertToDto(CarMaintenance cm) {
        CarMaintenanceDto dto = new CarMaintenanceDto();
        dto.setId(cm.getId());
        dto.setType(cm.getType());
        dto.setRemarks(cm.getRemarks());
        dto.setDate(cm.getDate());

        Car car = cm.getCar();
        if (car != null) {
            dto.setCar(car.getBrand() + " " + car.getModel());
            dto.setCarId(car.getId()); // SET carId HERE
        }

        return dto;
    }

    public List<CarMaintenanceDto> convertToDto(List<CarMaintenance> list) {
        List<CarMaintenanceDto> dtos = new ArrayList<>();
        for (CarMaintenance cm : list) {
            dtos.add(convertToDto(cm));
        }
        return dtos;
    }
}
