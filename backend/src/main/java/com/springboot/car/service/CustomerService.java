package com.springboot.car.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.springboot.car.dto.CustomerDto;
import com.springboot.car.exception.ResourceNotFoundException;
import com.springboot.car.model.Customer;
import com.springboot.car.model.User;
import com.springboot.car.repository.BookingRepository;
import com.springboot.car.repository.CustomerRepository;

@Service
public class CustomerService {

    private final Logger logger = LoggerFactory.getLogger(CustomerService.class);

    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private UserService userService;


    @Autowired
    private CustomerDto customerDto;

    public Customer insertCustomer(Customer customer) {
        logger.info("Inserting new customer");
        User user = customer.getUser();
        user.setRole("CUSTOMER");
        user = userService.signUp(user);
        customer.setUser(user);
        return customerRepository.save(customer);
    }

    public List<CustomerDto> getAllCustomers(int page, int size) {
        logger.info("Fetching all customers");
        Pageable pageable = PageRequest.of(page, size);
        List<Customer> list = customerRepository.findAll(pageable).getContent();
        return customerDto.convertCustomerIntoDto(list);
    }

    public void deleteCustomerByUsername(String username) {
        logger.info("Deleting customer with username: {}", username);

        Customer customer = customerRepository.getCustomerByUsername(username);
        if (customer == null) {
            throw new ResourceNotFoundException("Customer not found with username: " + username);
        }

        int customerId = customer.getId();

        if (!bookingRepository.findByCustomerId(customerId).isEmpty()) {
        	 throw new ResourceNotFoundException("Cannot delete customer with existing bookings.");
        }

        
        User user = customer.getUser();
        customerRepository.deleteById(customerId); 
        if (user != null) {
            userService.deleteUser(user.getId());   
        }
    }



    public CustomerDto updateCustomer(String username, Customer customer) {
        logger.info("Updating customer");
        Customer existingCustomer = customerRepository.getCustomerByUsername(username);
        if (existingCustomer == null) {
            throw new ResourceNotFoundException("Customer not found by username");
        }
        if (customer.getName() != null) {
            existingCustomer.setName(customer.getName());
        }
        if (customer.getEmail() != null) {
            existingCustomer.setEmail(customer.getEmail());
        }
        if (customer.getContact() != null) {
            existingCustomer.setContact(customer.getContact());
        }
        if (customer.getProfilePic() != null) {
            existingCustomer.setProfilePic(customer.getProfilePic());
        }
        if (customer.getUser() != null) {
            existingCustomer.setUser(customer.getUser());
        }

        Customer saved = customerRepository.save(existingCustomer);
        return customerDto.convertCustomerIntoDto(saved);
    }

    public CustomerDto getCustomerByUsername(String username) {
        logger.info("Fetching customer by username");
        Customer customer = customerRepository.getCustomerByUsername(username);
        if (customer == null) {
            throw new ResourceNotFoundException("Customer not found by username");
        }
        return customerDto.convertCustomerIntoDto(customer);
    }

    public List<CustomerDto> searchCustomers(String keyword, int page, int size) {
        logger.info("Searching customers");
        Pageable pageable = PageRequest.of(page, size);
        Page<Customer> resultPage = customerRepository.searchCustomers(keyword, pageable);
        return customerDto.convertCustomerIntoDto(resultPage.getContent());
    }
    
    public Customer uploadProfilePic(MultipartFile file, String username) throws IOException {
        Customer customer = customerRepository.getCustomerByUsername(username);
        System.out.println("Customer before update: " + customer.getProfilePic());

        String originalFileName = file.getOriginalFilename(); // new file name
        System.out.println("Original File: " + originalFileName);

        String extension = originalFileName.substring(originalFileName.lastIndexOf('.') + 1).toLowerCase();
        if (!List.of("jpg", "jpeg", "png", "gif", "svg").contains(extension)) {
            throw new RuntimeException("File Extension " + extension + " not allowed. Allowed: jpg, jpeg, png, gif, svg");
        }

        long kbs = file.getSize() / 1024;
        if (kbs > 3000) {
            throw new RuntimeException("Image Oversized. Max 3000KB allowed, got: " + kbs);
        }

        String uploadFolder = "C:\\Users\\Admin\\OneDrive\\Desktop\\JAVA-FSD\\React\\react-car-ui\\public\\images";
        Files.createDirectories(Path.of(uploadFolder));

        Path path = Paths.get(uploadFolder, originalFileName);
        Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

        // Update profile pic
        customer.setProfilePic(originalFileName);

        // Save and flush
        Customer savedCustomer = customerRepository.saveAndFlush(customer);

        System.out.println("Saved profilePic: " + savedCustomer.getProfilePic());

        return savedCustomer;
    }


}











