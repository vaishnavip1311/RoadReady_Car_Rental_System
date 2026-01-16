package com.springboot.car.controller;

import java.io.IOException;
import java.security.Principal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.springboot.car.dto.BookingBarDto;
import com.springboot.car.dto.ManagerDto;
import com.springboot.car.model.Manager;
import com.springboot.car.service.ManagerService;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
public class ManagerController {

    private static final Logger logger = LoggerFactory.getLogger(ManagerController.class);

    private final ManagerService managerService;

    public ManagerController(ManagerService managerService) {
        this.managerService = managerService;
    }

    @PostMapping("/api/manager/add")
    public ResponseEntity<?> postManager(@RequestBody Manager manager) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(managerService.postManager(manager));
    }

    @PutMapping("/api/manager/update")
    public ResponseEntity<ManagerDto> updateManager(@RequestBody Manager updatedManager, Principal principal) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(managerService.updateManager(principal, updatedManager));
    }
    
    @DeleteMapping("/api/manager/delete")
    public ResponseEntity<String> deleteManager(Principal principal) {
        logger.info("Deleting manager");
        managerService.deleteManager(principal);
        return ResponseEntity.status(HttpStatus.OK).body("Manager deleted successfully");
    }
    
    @GetMapping("/api/manager/view")
    public ResponseEntity<ManagerDto> viewProfile(Principal principal) {
        ManagerDto dto = managerService.getManagerByUsername(principal.getName());
        return ResponseEntity.ok(dto);
    }
  
    @PostMapping("/api/upload/profile-pic")
    public Manager uploadProfilePic(Principal principal, @RequestParam("file") MultipartFile file) throws IOException {
        return managerService.uploadProfilePic(file, principal.getName());
    }
    
    @GetMapping("api/manager/booking/stats")
    public BookingBarDto getBookingStats(BookingBarDto dto) {
        return managerService.getBookingStats(dto);
    }

}











