package com.manoj.autonest.service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.manoj.autonest.model.Car;
import com.manoj.autonest.model.Notification;  // Import Notification
import com.manoj.autonest.repositories.CarRepository;
import com.manoj.autonest.repositories.NotificationRepository;  // Import NotificationRepository

@Service
public class CarService {

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private NotificationRepository notificationRepository;  // Autowire NotificationRepository

    public Car saveCarWithImage(Car car, MultipartFile image) throws IOException {
        if (!image.isEmpty()) {
            car.setImage(image.getBytes());
        }
        Car savedCar = carRepository.save(car);
        
        // Create a notification for car addition
        Notification notification = new Notification();
        notification.setMessage("New car added: " + car.getManufacturer() + " " + car.getModel());
        notification.setTimestamp(LocalDateTime.now());
        notificationRepository.save(notification);  // Save notification
        
        return savedCar;
    }
    
    public Optional<Car> getCarById(Long id) {
        return carRepository.findById(id);
    }
    
    public List<Car> getAllCars() {
        return carRepository.findAll();
    }
    
    public Car updateCar(Car car) {
        Car updatedCar = carRepository.save(car);
        
        // Create a notification for car update
        Notification notification = new Notification();
        notification.setMessage("Car updated: " + car.getManufacturer() + " " + car.getModel());
        notification.setTimestamp(LocalDateTime.now());
        notificationRepository.save(notification);  // Save notification
        
        return updatedCar;
    }
    
    public void deleteCarById(Long id) {
        Optional<Car> carOptional = carRepository.findById(id);
        if (carOptional.isPresent()) {
            Car car = carOptional.get();
            carRepository.deleteById(id);  // Delete the car from the database
            
            // Create a notification for car deletion
            Notification notification = new Notification();
            notification.setMessage("Car deleted: " + car.getManufacturer() + " " + car.getModel());
            notification.setTimestamp(LocalDateTime.now());
            notificationRepository.save(notification);  // Save notification
        }
    }
}
