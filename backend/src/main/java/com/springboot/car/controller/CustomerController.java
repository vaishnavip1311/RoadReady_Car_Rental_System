package com.springboot.car.controller;

import java.io.IOException;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.springboot.car.model.Customer;
import com.springboot.car.model.Manager;
import com.springboot.car.service.CustomerService;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
public class CustomerController {

    private Logger logger = LoggerFactory.getLogger(CustomerController.class);

    @Autowired
    private CustomerService customerService;

    @PostMapping("/api/customer/add")
    public ResponseEntity<?> insertCustomer(@RequestBody Customer customer) {
        logger.info("Inserting a new customer");
        return ResponseEntity.status(HttpStatus.CREATED).body(customerService.insertCustomer(customer));
    }

    @GetMapping("/api/customer/get-all")
    public ResponseEntity<?> getAllCustomers(
            @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
            @RequestParam(name = "size", required = false, defaultValue = "1000000") Integer size) {
        if (page == 0 && size == 1000000) {
            logger.info("No Pagination call for all customers");
        } else {
            logger.info("Fetching customers with pagination");
        }
        return ResponseEntity.status(HttpStatus.OK).body(customerService.getAllCustomers(page, size));
    }

    @DeleteMapping("/api/customer/delete")
    public ResponseEntity<?> deleteCustomer(Principal principal) {
        String username = principal.getName();
        logger.info("Deleting customer");
        customerService.deleteCustomerByUsername(username);
        return ResponseEntity.status(HttpStatus.OK).body("Customer deleted successfully");
    }


    @GetMapping("/api/customer/get-one")
    public ResponseEntity<?> getCustomerById(Principal principal) {
        logger.info("Fetching customer by username");
        String username = principal.getName();
        return ResponseEntity.status(HttpStatus.OK).body(customerService.getCustomerByUsername(username));
    }

    @PutMapping("/api/customer/update")
    public ResponseEntity<?> updateCustomer(Principal principal, @RequestBody Customer updatedCustomer) {
        logger.info("Updating customer");
        return ResponseEntity.status(HttpStatus.OK).body(customerService.updateCustomer(principal.getName(), updatedCustomer));
    }

    @GetMapping("/api/customer/search")
    public ResponseEntity<?> searchCustomers(
            @RequestParam String keyword,
            @RequestParam(name = "page", required = false, defaultValue = "0") int page,
            @RequestParam(name = "size", required = false, defaultValue = "1000000") int size) {
        logger.info("Searching customers with keyword");
        return ResponseEntity.status(HttpStatus.OK).body(customerService.searchCustomers(keyword, page, size));
    }
    
    @PostMapping("/api/customer/upload/profile-pic")
    public Customer uploadProfilePic(Principal principal, @RequestParam("file") MultipartFile file) throws IOException {
        return customerService.uploadProfilePic(file, principal.getName());
    }
}



