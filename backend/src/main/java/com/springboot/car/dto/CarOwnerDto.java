package com.springboot.car.dto;

import java.util.ArrayList;
import java.util.List;

import com.springboot.car.model.CarOwner;

public class CarOwnerDto {
    private int id;
    private String name;
    private String email;
    private String contact;
    private String ProfilePic;
    private boolean verified=true;


    public CarOwnerDto() {}

    public CarOwnerDto(int id,String name, String email, String contact, boolean verified, boolean isActive) {
        this.name = name;
        this.email = email;
        this.contact = contact;
        this.verified = verified;
        this.id=id;
    }

    public String getProfilePic() {
		return ProfilePic;
	}

	public void setProfilePic(String profilePic) {
		ProfilePic = profilePic;
	}

	// Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

    public String getEmail() {
        return email;
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

	public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }

    
    
    
    
}
