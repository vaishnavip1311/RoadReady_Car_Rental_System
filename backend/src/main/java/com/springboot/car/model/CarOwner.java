package com.springboot.car.model;

import jakarta.persistence.*;

@Entity
public class CarOwner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    private String email;
    private String contact;
    private boolean verified=true; 
    private String ProfilePic;
    
   	public String getProfilePic() {
   		return ProfilePic;
   	}
   	public void setProfilePic(String profilePic) {
   		ProfilePic = profilePic;
   	}

    @OneToOne
    private User user;

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
	public boolean verified() {
        return verified;
    }

    public void setverified(boolean verified) {
        this.verified = verified;
    }

 

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
