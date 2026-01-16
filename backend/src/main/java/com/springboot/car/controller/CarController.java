package com.springboot.car.controller;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.springboot.car.dto.CarDto;
import com.springboot.car.model.Car;
import com.springboot.car.service.CarService;

@RestController
@RequestMapping("/api/car")
@CrossOrigin(origins = "http://localhost:5173")
public class CarController {

    private Logger logger = LoggerFactory.getLogger(CarController.class);

    @Autowired
    private CarService carService;

    @Autowired
    private CarDto carDto;

   
    @PostMapping("/add")
    public ResponseEntity<?> addCar(@RequestBody Car car) {
        logger.info("Adding a new car: {}", car.getModel());
        carService.addCar(car);
        return ResponseEntity.status(HttpStatus.CREATED).body("Car added successfully!");
    }


    @GetMapping("/all")
    public ResponseEntity<?> getAllCars(
            @RequestParam(name = "page", required = false, defaultValue = "0") int page,
            @RequestParam(name = "size", required = false, defaultValue = "1000000") int size) {

        logger.info("Fetching all cars");
        List<Car> cars = carService.getAllCars(page, size);
        List<CarDto> carDtos = carDto.convertCarIntoDto(cars);
        return ResponseEntity.status(HttpStatus.OK).body(carDtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Car> getCarById(@PathVariable int id) {
        Car car = carService.getCarById(id);
        return ResponseEntity.ok(car);
    }

    @GetMapping("/search/price")
    public ResponseEntity<?> getCarsByPriceRange(
            @RequestParam double min,
            @RequestParam double max,
            	@RequestParam(name = "page", required = false, defaultValue = "0") int page,
            @RequestParam(name = "size", required = false, defaultValue = "1000000") int size) {

        logger.info("Fetching cars in price range");
        List<Car> cars = carService.getCarsByPriceRange(min, max, page, size);
        List<CarDto> carDtos = carDto.convertCarIntoDto(cars);
        return ResponseEntity.status(HttpStatus.OK).body(carDtos);
    }

   

    @GetMapping("/available")
    public ResponseEntity<?> getAvailableCars(
            @RequestParam(name = "page", required = false, defaultValue = "0") int page,
            @RequestParam(name = "size", required = false, defaultValue = "10") int size) {

        logger.info("Fetching available cars");
        List<Car> cars = carService.getAvailableCars(page, size);
        List<CarDto> carDtos = carDto.convertCarIntoDto(cars);
        return ResponseEntity.status(HttpStatus.OK).body(carDtos);
    }
    
    @PutMapping("/update/{id}")
    public ResponseEntity<Car> updateCar(@PathVariable int id, @RequestBody Car car) {
        Car updated = carService.updateCar(id, car);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteCar(@PathVariable int id) {
        carService.deleteCar(id);
        return ResponseEntity.ok("Car deleted successfully");
    }
    
    @GetMapping("/by-owner/{ownerId}")
    public ResponseEntity<List<Car>> getCarsByOwner(@PathVariable int ownerId) {
        List<Car> cars = carService.getCarsByOwner(ownerId);
        return ResponseEntity.ok(cars);
    }
    @GetMapping("/by-owner")
    public ResponseEntity<List<Car>> getCarsOwner(Principal principal,
    		 @RequestParam(name = "page", required = false, defaultValue = "0") int page,
             @RequestParam(name = "size", required = false, defaultValue = "10") int size) {
        List<Car> cars = carService.getCarsOwner(principal.getName(),page,size);
        return ResponseEntity.ok(cars);
    }
    
    @PostMapping("/upload/car-pic")
    public Car uploadCarPic(@RequestParam("file") MultipartFile file,
            @RequestParam("carId") int carId,
            Principal principal) throws IOException {
return carService.uploadPic(file, principal.getName(), carId);
}
    
    
    @GetMapping("/search/price-and-availability")
    public List<Car> getAvailableCarsByPriceRange(
        @RequestParam double min,
        @RequestParam double max,
        @RequestParam int page,
        @RequestParam int size) {
        
        return carService.getAvailableCarsByPriceRange(min, max, page, size).getContent();
    }



    


}














