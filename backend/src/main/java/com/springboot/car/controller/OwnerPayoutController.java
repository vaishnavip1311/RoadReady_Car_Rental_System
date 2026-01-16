package com.springboot.car.controller;

import java.security.Principal;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.car.dto.OwnerPayoutDto;
import com.springboot.car.service.OwnerPayoutService;

@RestController
@RequestMapping("/api/ownerpayout")
@CrossOrigin(origins = "http://localhost:5173")
public class OwnerPayoutController {

    private static final Logger logger = LoggerFactory.getLogger(OwnerPayoutController.class);

    @Autowired
    private OwnerPayoutService payoutService;

    @PostMapping("/create/{bookingId}")
    public ResponseEntity<?> createPayout(@PathVariable int bookingId) {
        logger.info("Creating payout");
        OwnerPayoutDto payout = payoutService.createPayout(bookingId);
        return ResponseEntity.status(HttpStatus.CREATED).body(payout);
    }

    @GetMapping("/all")
    public ResponseEntity<List<OwnerPayoutDto>> getAllPayouts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {
        logger.info("Fetching all payouts");
        Pageable pageable = PageRequest.of(page, size);
        List<OwnerPayoutDto> payouts = payoutService.getAllPayouts(pageable);
        return ResponseEntity.ok(payouts);
    }

    @GetMapping("/owner")
    public ResponseEntity<?> getPayoutsByOwner(
            Principal principal,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {
        logger.info("Fetching payouts for owner");
        List<OwnerPayoutDto> payouts = payoutService.getPayoutsByOwnerUsername(principal.getName(), page, size);
        return ResponseEntity.ok(payouts);
    }
}






