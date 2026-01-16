package com.springboot.car.controller;

import java.security.Principal;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.car.dto.CarMaintenanceDto;
import com.springboot.car.model.CarMaintenance;
import com.springboot.car.service.CarMaintenanceService;

@RestController
@RequestMapping("/api/maintenance")
@CrossOrigin(origins = "http://localhost:5173")
public class CarMaintenanceController {

    private static final Logger logger = LoggerFactory.getLogger(CarMaintenanceController.class);

    private final CarMaintenanceService maintenanceService;

    public CarMaintenanceController(CarMaintenanceService maintenanceService) {
        this.maintenanceService = maintenanceService;
    }

    @PostMapping("/add")
    public ResponseEntity<?> addMaintenance(@RequestBody CarMaintenance maintenance,
                                            @RequestParam int carId,
                                            Principal principal) {
        logger.info("Maintenance record added by: " + principal.getName());

        CarMaintenanceDto saved = maintenanceService.addMaintenance(maintenance, carId, principal.getName());
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllMaintenanceRecords(
            @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
            @RequestParam(name = "size", required = false, defaultValue = "1000000") Integer size) {
       
        List<CarMaintenanceDto> records = maintenanceService.getAllMaintenanceRecords(page, size);
        return ResponseEntity.status(HttpStatus.OK).body(records);
    }

    @GetMapping("/car/{carId}")
    public ResponseEntity<?> getMaintenanceByCar(@PathVariable int carId) {
        logger.info("Fetching maintenance records for car");
        List<CarMaintenanceDto> records = maintenanceService.getMaintenanceRecordsByCar(carId);
        return ResponseEntity.status(HttpStatus.OK).body(records);
    }


    @PutMapping("/update/{maintenanceId}")
    public ResponseEntity<?> updateMaintenance(@PathVariable int maintenanceId,
                                               @RequestBody CarMaintenance maintenance) {
        logger.info("Maintenance record updated");
        CarMaintenanceDto updated = maintenanceService.update(maintenanceId, maintenance);
        return ResponseEntity.status(HttpStatus.OK).body(updated);
    }
}









