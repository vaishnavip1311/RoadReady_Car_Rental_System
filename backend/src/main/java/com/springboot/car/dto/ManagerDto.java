package com.springboot.car.dto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.springboot.car.model.Manager;

@Component
public class ManagerDto {

    private int id;
    private String name;
    private String username;
    private String contact;
    private String email;
    private String profilePic;
    

   

	public String getProfilePic() {
		return profilePic;
	}

	public void setProfilePic(String profilePic) {
		this.profilePic = profilePic;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

    public static ManagerDto convertManagerIntoDto(Manager manager) {
        ManagerDto dto = new ManagerDto();
        dto.setId(manager.getId());
        dto.setName(manager.getName());
        dto.setContact(manager.getContact());
        dto.setEmail(manager.getEmail());
        dto.setUsername(manager.getUser().getUsername());
        dto.setProfilePic(manager.getProfilePic());
        return dto;
    }

    public static List<ManagerDto> convertManagerIntoDto(List<Manager> managers) {
        List<ManagerDto> dtoList = new ArrayList<>();
        for (Manager manager : managers) {
            dtoList.add(convertManagerIntoDto(manager));
        }
        return dtoList;
    }
}
