package com.springboot.car.dto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.springboot.car.model.User;

@Component
public class UserDto {

    private int id;
    private String username;
    private String role;

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public UserDto convertUserIntoDto(User user) {
        UserDto dto = new UserDto();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setRole(user.getRole());
        return dto;
    }
    public List<UserDto> convertUserIntoDto(List<User> users) {
        List<UserDto> dtoList = new ArrayList<>();
        for (User user : users) {
            dtoList.add(convertUserIntoDto(user));
        }
        return dtoList;
    }
}
