package com.springboot.car.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.springboot.car.dto.CarDto;
import com.springboot.car.exception.ResourceNotFoundException;
import com.springboot.car.model.Car;
import com.springboot.car.model.CarOwner;
import com.springboot.car.model.User;
import com.springboot.car.repository.CarOwnerRepository;
import com.springboot.car.repository.CarRepository;
import com.springboot.car.repository.ManagerRepository;
import com.springboot.car.repository.UserRepository;

@Service
public class CarService {

    @Autowired
    private CarRepository carRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ManagerRepository managerRepository;

    @Autowired
    private CarOwnerRepository carOwnerRepository;

    @Autowired
    private CarDto carDto; 
    
    
  
    public Car addCar(Car car) {
        if (car.getCarOwner() == null || car.getCarOwner().getId() == 0) {
            throw new RuntimeException("CarOwner ID is required");
        }

        int ownerId = car.getCarOwner().getId();

        CarOwner owner = carOwnerRepository.findById(ownerId)
                .orElseThrow(() -> new RuntimeException("Owner not found with ID: " + ownerId));

        car.setCarOwner(owner); // attach full owner entity

        return carRepository.save(car);
    }

    
    public List<Car> getAllCars(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return carRepository.findAll(pageable).getContent();
    }

    public Car getCarById(int id) {
        return carRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Car not found with ID: " + id));
    }

    public List<Car> getCarsByPriceRange(double min, double max, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return carRepository.getCarsByPriceRange(min, max, pageable);
    }

    

    public List<Car> getAvailableCars(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return carRepository.findByAvailabilityStatus("Available", pageable).getContent();
    }
    
    

    public Car updateCar(int id, Car updatedCar) {
        Car existing = carRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Car not found with id: " + id));

        if (updatedCar.getBrand() != null)
        	existing.setBrand(updatedCar.getBrand());
        	if (updatedCar.getModel() != null)
        	existing.setModel(updatedCar.getModel());
         
        	if (updatedCar.getColour() != null) 
        	existing.setColour(updatedCar.getColour());
        if (updatedCar.getFuelType() != null) 
        	existing.setFuelType(updatedCar.getFuelType());
        	if (updatedCar.getAvailabilityStatus() != null)
        	existing.setAvailabilityStatus(updatedCar.getAvailabilityStatus());
        
        	if (updatedCar.getSeats() != 0) 
        	existing.setSeats(updatedCar.getSeats());
          if (updatedCar.getPricePerDay() != 0.0) 
        		existing.setPricePerDay(updatedCar.getPricePerDay());
       
          if (updatedCar.getYear() != 0)
        	existing.setYear(updatedCar.getYear());
        			if (updatedCar.getManager() != null) 
        	existing.setManager(updatedCar.getManager());
        if (updatedCar.getCarOwner() != null)
        	existing.setCarOwner(updatedCar.getCarOwner());

        return carRepository.save(existing);
    }

    public void deleteCar(int id) {
        Car car = carRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Car not found with id: " + id));
        carRepository.delete(car);
    }
    
    public List<Car> getCarsByOwner(int ownerId) {
        return carRepository.findByCarOwner_Id(ownerId);
    }
    public List<Car> getCarsOwner(String username,int page,int size) {
    	Pageable pageable = PageRequest.of(page, size);
    	 User user = userRepository.findByUsername(username);
    	 if (user == null) {
             throw new RuntimeException("User not found");
         }
        return carRepository.findByCarOwner_Name(username,pageable).getContent();
    }
    
    
    public Car uploadPic(MultipartFile file, String username,int carId) throws IOException {
        // Validate user
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new RuntimeException("User not found");
        }

        // Fetch car
        Car car = carRepository.findById(carId)
                .orElseThrow(() -> new RuntimeException("Car not found with ID: " + carId));

     

        // Validate file extension
        String originalFileName = file.getOriginalFilename();
        String extension = originalFileName.substring(originalFileName.lastIndexOf('.') + 1).toLowerCase();
        if (!List.of("jpg", "jpeg", "png", "gif", "svg").contains(extension)) {
            throw new RuntimeException("Invalid file extension: " + extension);
        }

        // Validate size
        long kbs = file.getSize() / 1024;
        if (kbs > 3000) {
            throw new RuntimeException("Image too large (Max 3000 KB)");
        }

        // Store file
        String uploadFolder = "C:\\Users\\Admin\\OneDrive\\Desktop\\JAVA-FSD\\React\\react-car-ui\\public\\images";
        Files.createDirectories(Path.of(uploadFolder));

        Path path = Paths.get(uploadFolder, originalFileName);
        Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

        // Update image field in Car
        car.setPic(originalFileName); // or setImageUrl
        return carRepository.save(car);
    }

    
    public Page<Car> getAvailableCarsByPriceRange(double min, double max, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return carRepository.findByAvailabilityStatusAndPricePerDayBetween("AVAILABLE", min, max, pageable);
    }



}






//public List<Car> filterCars(String brand, String model, int page, int size) {
//    Pageable pageable = PageRequest.of(page, size);
//    return carRepository.filterCars(brand, model, pageable);
//}








