package com.springboot.car.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.springboot.car.dto.BookingBarDto;
import com.springboot.car.dto.ManagerDto;
import com.springboot.car.exception.ResourceNotFoundException;
import com.springboot.car.model.Booking;
import com.springboot.car.model.Car;
import com.springboot.car.model.Manager;
import com.springboot.car.model.User;
import com.springboot.car.repository.BookingRepository;
import com.springboot.car.repository.CarRepository;
import com.springboot.car.repository.ManagerRepository;
import com.springboot.car.repository.UserRepository;

@Service
public class ManagerService {

    private static final Logger logger = LoggerFactory.getLogger(ManagerService.class);

    
    private final CarRepository carRepository;
    private final BookingRepository bookingRepository;
    @Autowired
    private final ManagerRepository managerRepository;
    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private UserService userService;
    

    public ManagerService(ManagerRepository managerRepository, UserRepository userRepository,UserService userService,BookingRepository bookingRepository,CarRepository carRepository) {
        this.managerRepository = managerRepository;
        this.userRepository = userRepository;
        this.userService=userService;
        this.bookingRepository=bookingRepository;
        this.carRepository=carRepository;
    }

    public Manager postManager(Manager manager) {
        logger.info("Saving new manager");
            User user = manager.getUser();
            user.setRole("MANAGER"); 

            user = userService.signUp(user); 
            manager.setUser(user); 

            return managerRepository.save(manager); 
    }


    public ManagerDto updateManager(Principal principal, Manager updatedManager) {
        String username = principal.getName();
        logger.info("Updating manager");

        Manager existing = managerRepository.getManagerByUsername(username);
        if (existing == null) {
            throw new ResourceNotFoundException("Manager not found with username: " + username);
        }

        if (updatedManager.getName() != null) {
            existing.setName(updatedManager.getName());
        }

        if (updatedManager.getContact() != null) {
            existing.setContact(updatedManager.getContact());
        }

        if (updatedManager.getEmail() != null) {
            existing.setEmail(updatedManager.getEmail());
        }
        if (updatedManager.getProfilePic() != null) {
            existing.setProfilePic(updatedManager.getProfilePic());
        }

        Manager saved = managerRepository.save(existing);
        return ManagerDto.convertManagerIntoDto(saved);
    }


    public void deleteManager(Principal principal) {
        String username = principal.getName();
        logger.info("Deleting manager");

        Manager existing = managerRepository.getManagerByUsername(username);
        if (existing == null) {
            throw new ResourceNotFoundException("Manager not found with username: " + username);
        }
       
        User user= existing.getUser();
        managerRepository.delete(existing);
        if (user != null) {
            userService.deleteUser(user.getId());   
        }
    }
    

    public ManagerDto getManagerByUsername(String username) {
        Manager manager = managerRepository.findByUserUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Manager not found"));
        
        return ManagerDto.convertManagerIntoDto(manager);
    }
    
    
    public Manager uploadProfilePic(MultipartFile file, String username) throws IOException {
        
        Manager manager = managerRepository.getManagerByUsername(username);
       
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
       
        manager.setProfilePic(originalFileName);
       
        return managerRepository.save(manager);
    }
    
    
    
    
    public BookingBarDto getBookingStats(BookingBarDto dto) {
        List<Car> cars = carRepository.findAll(); // all cars
        List<Booking> bookings = bookingRepository.findAll(); // all bookings

        List<String> carNames = new ArrayList<>();
        List<Integer> bookingCounts = new ArrayList<>();

        for (Car car : cars) {
        	long count = bookings.stream()
                    .filter(b -> b.getCar().getId() == car.getId())
                    .count();

            carNames.add(car.getBrand()); 
            bookingCounts.add((int) count);
        }

        dto.setCarNames(carNames);
        dto.setBookingCounts(bookingCounts);
        return dto;
    }


}






