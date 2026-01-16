package com.springboot.car.dto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.springboot.car.model.Customer;

@Component
public class CustomerDto {
    private int id;
    private String name;
    private String username;
    private String email;
	private String contact;
	private String profilePic;
    public String getEmail() {
		return email;
	}
    public String getProfilePic() {
		return profilePic;
	}
    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

	public void setEmail(String email) {
		this.email = email;
	}

	

	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
   
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    public CustomerDto convertCustomerIntoDto(Customer customer) {
        CustomerDto dto = new CustomerDto();
        dto.setId(customer.getId());
        dto.setName(customer.getName());
        dto.setContact(customer.getContact());
        dto.setEmail(customer.getEmail());
        dto.setProfilePic(customer.getProfilePic());

        dto.setUsername(customer.getUser().getUsername());
        return dto;
    }

    public List<CustomerDto> convertCustomerIntoDto(List<Customer> customers) {
        List<CustomerDto> dtos = new ArrayList<>();
        for (Customer customer : customers) {
            dtos.add(convertCustomerIntoDto(customer));
        }
        return dtos;
    }

    
}