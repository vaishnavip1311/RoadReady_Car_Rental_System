package com.springboot.car.controller;

import java.security.Principal;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.car.dto.ReturnLogDto;
import com.springboot.car.model.ReturnLog;
import com.springboot.car.service.ReturnLogService;

@RestController
@RequestMapping("/api/returnlog")
@CrossOrigin(origins = "http://localhost:5173")
public class ReturnLogController {

    private static final Logger logger = LoggerFactory.getLogger(ReturnLogController.class);

    @Autowired
    private ReturnLogService returnLogService;


   @PostMapping("/add")
   public ResponseEntity<?> addReturnLog(
        @RequestParam int bookingId,
        @RequestBody ReturnLog returnLog,
        Principal principal) {

    logger.info("Adding return log by manager");
    ReturnLogDto saved = returnLogService.addReturnLog(returnLog, bookingId, principal.getName());
    return ResponseEntity.status(HttpStatus.CREATED).body(saved);
   }
   @PutMapping("/update/{id}")
   public ResponseEntity<?> updateReturnLog(@PathVariable int id,
                                         @RequestBody ReturnLog returnLog) {
    logger.info("Updating return log with ID");
    ReturnLogDto updated = returnLogService.updateReturnLog(id, returnLog);
    return ResponseEntity.status(HttpStatus.OK).body(updated);
   }

    @GetMapping("/all")
    public ResponseEntity<?> getAllReturnLogs(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "1000000") int size) {
        logger.info("Fetching all return logs");
        List<ReturnLogDto> list = returnLogService.getAllReturnLogs(page, size);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

  

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteReturnLog(@PathVariable int id) {
        logger.info("Deleting return log with ID");
        returnLogService.deleteReturnLog(id);
        return new ResponseEntity<>("Return log deleted successfully", HttpStatus.OK);
    }

   
}





