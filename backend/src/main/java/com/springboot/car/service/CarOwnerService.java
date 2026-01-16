package com.springboot.car.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.springboot.car.dto.CarOwnerDto;
import com.springboot.car.exception.ResourceNotFoundException;
import com.springboot.car.model.CarOwner;
import com.springboot.car.model.Manager;
import com.springboot.car.model.User;
import com.springboot.car.repository.CarOwnerRepository;
import com.springboot.car.repository.CarRepository;

@Service
public class CarOwnerService {
    private final CarOwnerRepository carOwnerRepository;
    private final CarRepository carRepository;
    private final UserService userService;

    public CarOwnerService(CarOwnerRepository carOwnerRepository, UserService userService,CarRepository carRepository) {
        this.carOwnerRepository = carOwnerRepository;
        this.userService = userService;
        this.carRepository =carRepository;
    }

    public CarOwner addOwner(CarOwner carOwner) {
        User user = carOwner.getUser();
        user.setRole("CAROWNER");
        user = userService.signUp(user);
        carOwner.setUser(user);
       return carOwnerRepository.save(carOwner);
    }

    public List<CarOwnerDto> getAllCarOwners(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        List<CarOwner> list = carOwnerRepository.findAll(pageable).getContent();
        return convertCarOwnerIntoDto(list);
    }

    public void deleteCarOwner(String username) {
    	 CarOwner owner = carOwnerRepository.getCarOwnerByUsername(username);
    	    if (owner == null) {
    	        throw new ResourceNotFoundException("Car owner not found with username: " + username);
    	    }

    	    int ownerId = owner.getId();

    	    if (!carRepository.findByCarOwner_Id(ownerId).isEmpty()) {
    	        throw new ResourceNotFoundException("Cannot delete car owner with existing cars.");
    	    }

    	    User user=owner.getUser();
    	    carOwnerRepository.deleteById(ownerId);
    	    if (user != null) {
                userService.deleteUser(user.getId());   
            }
    }


    public CarOwnerDto updateCarOwner(String username, CarOwner carOwner) {
    	 CarOwner updatedOwner = carOwnerRepository.getCarOwnerByUsername(username);
         if (updatedOwner == null) {
             throw new RuntimeException("Car owner not found");
         }
         if (carOwner.getName() != null) {
             updatedOwner.setName(carOwner.getName());
         }
        if (carOwner.getEmail() != null) {
            updatedOwner.setEmail(carOwner.getEmail());
        }
        if (carOwner.getContact() != null) {
            updatedOwner.setContact(carOwner.getContact());
        }
        if (carOwner.getUser() != null) {
            updatedOwner.setUser(carOwner.getUser());
        }
        if (carOwner.getProfilePic() != null) {
        	 updatedOwner.setProfilePic(carOwner.getProfilePic());
        }
        updatedOwner.setverified(true);

        CarOwner saved = carOwnerRepository.save(updatedOwner);
        return convertCarOwnerIntoDto(saved);
    }

    public CarOwnerDto getCarOwnerByUsername(String username) {
        CarOwner carOwner = carOwnerRepository.getCarOwnerByUsername(username);
        if (carOwner == null) {
            throw new RuntimeException("Car owner not found");
        }
        return convertCarOwnerIntoDto(carOwner);
    }
    
   
    
    
    
    public CarOwnerDto convertCarOwnerIntoDto(CarOwner carOwner) {
        CarOwnerDto dto = new CarOwnerDto();
        dto.setId(carOwner.getId());
        dto.setName(carOwner.getName());
        dto.setEmail(carOwner.getEmail());
        dto.setContact(carOwner.getContact());
        dto.setVerified(carOwner.verified());
        dto.setProfilePic(carOwner.getProfilePic());
        return dto;
    }

   
    public List<CarOwnerDto> convertCarOwnerIntoDto(List<CarOwner> carOwners) {
        List<CarOwnerDto> dtos = new ArrayList<>();
        for (CarOwner owner : carOwners) {
            dtos.add(convertCarOwnerIntoDto(owner));
        }
        return dtos;
    }
    
    
 public CarOwner uploadProfilePic(MultipartFile file, String username) throws IOException {
        
       CarOwner carOwner = carOwnerRepository.getCarOwnerByUsername(username);
       
        String originalFileName = file.getOriginalFilename(); // profile_pic.png
        
        String extension = originalFileName.split("\\.")[1]; // png
        if (!(List.of("jpg", "jpeg", "png", "gif", "svg").contains(extension))) {
            
            throw new RuntimeException("File Extension " + extension + " not allowed " + "Allowed Extensions"
                    + List.of("jpg", "jpeg", "png", "gif", "svg"));
        }
       
        long kbs = file.getSize() / 1024;
        if (kbs > 3000) {
           
            throw new RuntimeException("Image Oversized. Max allowed size is " + kbs);
        }
       
        String uploadFolder = "C:\\Users\\Admin\\OneDrive\\Desktop\\JAVA-FSD\\React\\react-car-ui\\public\\images";
        Files.createDirectories(Path.of(uploadFolder));
       
        Path path = Paths.get(uploadFolder, "\\", originalFileName);
      
        Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
       
        carOwner.setProfilePic(originalFileName);
       
        return carOwnerRepository.save(carOwner);
    }

}












