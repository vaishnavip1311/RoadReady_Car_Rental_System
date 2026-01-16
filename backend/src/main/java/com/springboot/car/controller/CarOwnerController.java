package com.springboot.car.controller;

import java.io.IOException;
import java.security.Principal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.springboot.car.model.CarOwner;
import com.springboot.car.model.Manager;
import com.springboot.car.service.CarOwnerService;

import java.util.List;

@RestController
@RequestMapping("/api/carowner")
@CrossOrigin(origins = "http://localhost:5173")
public class CarOwnerController {

    @Autowired
    private CarOwnerService carOwnerService;

    private final Logger logger = LoggerFactory.getLogger(CarOwnerController.class);

    @PostMapping("/add")
    public ResponseEntity<?> addCarOwner(@RequestBody CarOwner carOwner) {
        logger.info("Adding new car owner");
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(carOwnerService.addOwner(carOwner));
    }

    @GetMapping("/get-all")
    public ResponseEntity<?> getAllCarOwners(
            @RequestParam(name = "page", required = false, defaultValue = "0") int page,
            	@RequestParam(name = "size", required = false, defaultValue = "1000000") int size) {
        return ResponseEntity.status(HttpStatus.OK).body(carOwnerService.getAllCarOwners(page, size));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteCarOwner(Principal principal) {
        logger.info("Deleting car owner");
        carOwnerService.deleteCarOwner(principal.getName());
        return ResponseEntity.status(HttpStatus.OK).body("Car owner deleted successfully");
    }

    @GetMapping("/get-one")
    public ResponseEntity<?> getCarOwnerByUsername(Principal principal) {
        logger.info("Fetching car owner by username");
        return ResponseEntity.status(HttpStatus.OK).body(carOwnerService.getCarOwnerByUsername(principal.getName()));
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateCarOwner(Principal principal, @RequestBody CarOwner carOwner) {
        logger.info("Updating car owner");
        return ResponseEntity.status(HttpStatus.OK).body(carOwnerService.updateCarOwner(principal.getName(), carOwner));
    }

   
    
    @PostMapping("/upload/profile-pic")
    public CarOwner uploadProfilePic(Principal principal, @RequestParam("file") MultipartFile file) throws IOException {
        return carOwnerService.uploadProfilePic(file, principal.getName());
    }
}






