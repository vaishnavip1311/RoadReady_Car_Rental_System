package com.springboot.car;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.springboot.car.dto.CustomerDto;
import com.springboot.car.exception.ResourceNotFoundException;
import com.springboot.car.model.Customer;
import com.springboot.car.model.User;
import com.springboot.car.repository.BookingRepository;
import com.springboot.car.repository.CustomerRepository;
import com.springboot.car.service.CustomerService;
import com.springboot.car.service.UserService;

@SpringBootTest
public class CustomerServiceTest {

    @InjectMocks
    private CustomerService customerService;
    @Mock
    private BookingRepository bookingRepository;


    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private CustomerDto customerDto;

    @Mock
    private UserService userService;

    private Customer customer;
    private User user;

    @BeforeEach
    public void init() {
        user = new User();
        user.setId(1);
        user.setUsername("john");
        user.setPassword("pass");

        customer = new Customer();
        customer.setId(1);
        customer.setName("John Doe");
        customer.setEmail("john@example.com");
        customer.setContact("1234567890");
        customer.setUser(user);
    }

    @Test
    public void testInsertCustomer() {
        when(userService.signUp(user)).thenReturn(user);
        when(customerRepository.save(customer)).thenReturn(customer);

        Customer savedCustomer = customerService.insertCustomer(customer);

        assertNotNull(savedCustomer);
        assertEquals(customer.getEmail(), savedCustomer.getEmail());
        assertEquals(customer.getContact(), savedCustomer.getContact());
        verify(userService, times(1)).signUp(user);
        verify(customerRepository, times(1)).save(customer);
    }

    @Test
    public void testGetAllCustomers() {
        Pageable pageable = PageRequest.of(0, 5);
        List<Customer> list = List.of(customer);
        Page<Customer> page = new PageImpl<>(list);

        when(customerRepository.findAll(pageable)).thenReturn(page);
        when(customerDto.convertCustomerIntoDto(list)).thenReturn(List.of(new CustomerDto()));

        List<CustomerDto> result = customerService.getAllCustomers(0, 5);
        assertEquals(1, result.size());
    }

    @Test
    public void testDeleteCustomerByUsername() {
        String username = "testUser";

        Customer customer = new Customer();
        customer.setId(0); // make sure ID is set to match the mock
        customer.setName(username);

        when(customerRepository.getCustomerByUsername(username)).thenReturn(customer);
        when(bookingRepository.findByCustomerId(0)).thenReturn(Collections.emptyList());

        assertDoesNotThrow(() -> customerService.deleteCustomerByUsername(username));

        verify(customerRepository).deleteById(0); // ✅ this matches the service method
    }

    @Test
    public void testDeleteCustomerByUsername_NotFound() {
        String username = "unknownUser";

        // Mock repository to return null (customer not found)
        when(customerRepository.getCustomerByUsername(username)).thenReturn(null);

        ResourceNotFoundException ex = assertThrows(ResourceNotFoundException.class, () -> {
            customerService.deleteCustomerByUsername(username);
        });

        assertEquals("Customer not found with username: unknownUser", ex.getMessage());
    }




    @Test
    public void testUpdateCustomer() {
        when(customerRepository.getCustomerByUsername("john")).thenReturn(customer);
        when(customerRepository.save(customer)).thenReturn(customer);
        when(customerDto.convertCustomerIntoDto(customer)).thenReturn(new CustomerDto());

        CustomerDto dto = customerService.updateCustomer("john", customer);
        assertEquals(CustomerDto.class, dto.getClass());
    }

    @Test
    public void testUpdateCustomer_NotFound() {
        when(customerRepository.getCustomerByUsername("unknown")).thenReturn(null);

        ResourceNotFoundException ex = assertThrows(ResourceNotFoundException.class, () -> {
            customerService.updateCustomer("unknown", customer);
        });
        assertEquals("Customer not found by username", ex.getMessage());
    }

    @Test
    public void testGetCustomerByUsername() {
        when(customerRepository.getCustomerByUsername("john")).thenReturn(customer);
        when(customerDto.convertCustomerIntoDto(customer)).thenReturn(new CustomerDto());

        CustomerDto dto = customerService.getCustomerByUsername("john");
        assertEquals(CustomerDto.class, dto.getClass());
    }

    @Test
    public void testGetCustomerByUsername_NotFound() {
        when(customerRepository.getCustomerByUsername("invalid")).thenReturn(null);

        ResourceNotFoundException ex = assertThrows(ResourceNotFoundException.class, () -> {
            customerService.getCustomerByUsername("invalid");
        });
        assertEquals("Customer not found by username", ex.getMessage());
    }

    @Test
    public void testSearchCustomers() {
        Pageable pageable = PageRequest.of(0, 5);
        List<Customer> list = List.of(customer);
        Page<Customer> page = new PageImpl<>(list);

        when(customerRepository.searchCustomers("john", pageable)).thenReturn(page);
        when(customerDto.convertCustomerIntoDto(list)).thenReturn(List.of(new CustomerDto()));

        List<CustomerDto> result = customerService.searchCustomers("john", 0, 5);
        assertEquals(1, result.size());
    }

    @AfterEach
    public void clear() {
        customer = null;
        user = null;
    }
}
