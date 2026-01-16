package com.springboot.car.service;


import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.springboot.car.model.CarOwner;
import com.springboot.car.model.Customer;
import com.springboot.car.model.Manager;
import com.springboot.car.model.User;
import com.springboot.car.repository.CarOwnerRepository;
import com.springboot.car.repository.CustomerRepository;
import com.springboot.car.repository.ManagerRepository;
import com.springboot.car.repository.UserRepository;

@Service
public class UserService {

	private UserRepository userRepository;
	private PasswordEncoder passwordEncoder;
	private CustomerRepository customerRepository;
	private ManagerRepository managerRepository;
	private CarOwnerRepository carOwnerRepository;
	
	
	public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder,
		CustomerRepository customerRepository,ManagerRepository managerRepository,CarOwnerRepository carOwnerRepository) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.customerRepository=customerRepository;
		this.managerRepository = managerRepository;
		this.carOwnerRepository = carOwnerRepository;
	}

	public User signUp(User user) { 
		String plainPassword = user.getPassword(); 
		String encodedPassword =  passwordEncoder.encode(plainPassword);
		user.setPassword(encodedPassword); 
		return userRepository.save(user);
	}
	public void deleteUser(int id) {
	    userRepository.deleteById(id);
	}

	
	public Object getUserInfo(String username) {
		User user = userRepository.findByUsername(username);
		switch (user.getRole().toUpperCase()) {
			case "CUSTOMER":
				Customer customer = customerRepository.getCustomerByUsername(username);
				return customer;
			case "CAROWNER":
				CarOwner carOwner = carOwnerRepository.getCarOwnerByUsername(username);
				return carOwner;
			case "MANAGER":
				Manager manager=managerRepository.getManagerByUsername(username);
				return manager;
			
			default:
				return null;
		}

	}
	
	
}
