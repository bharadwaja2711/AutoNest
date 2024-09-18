package com.manoj.autonest.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.manoj.autonest.model.Car;
import com.manoj.autonest.repositories.CarRepository;

@Service
public class CarService {

    @Autowired
    private CarRepository carRepository;

    public Car saveCarWithImage(Car car, MultipartFile image) throws IOException {
        if (!image.isEmpty()) {
            car.setImage(image.getBytes());
        }
        return carRepository.save(car);
    }
    
    // New method to get car by id
    public Optional<Car> getCarById(Long id) {
        // Fetch car details by its id
        return carRepository.findById(id);
    }
    
    // New method to get all cars
    public List<Car> getAllCars() {
        return carRepository.findAll();
    }
    
    public Car updateCar(Car car) {
        return carRepository.save(car);  // save() will update the car if it exists
    }
    
    public void deleteCarById(Long id) {
        carRepository.deleteById(id); // Delete the car from the database
    }

}
