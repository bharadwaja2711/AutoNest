package com.manoj.autonest.controller;

import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.manoj.autonest.model.Car;
import com.manoj.autonest.service.CarService;

@Controller
public class CarController {
    private final CarService carService;

    public CarController(CarService carService) {
        this.carService = carService;
    }

    @GetMapping("/admin-page/vehiclemanagement/add")
    public String showCarForm(Model model) {
        model.addAttribute("car", new Car());
        return "newcar";
    }

    @PostMapping("/admin-page/vehiclemanagement/add")
    public String addCar(@ModelAttribute Car car, @RequestParam("media") MultipartFile imageFile,
                         RedirectAttributes redirectAttributes) {
        try {
            carService.saveCarWithImage(car, imageFile);
            redirectAttributes.addFlashAttribute("message", "Car added successfully!");
        } catch (IOException e) {
            redirectAttributes.addFlashAttribute("error", "Failed to add car.");
            e.printStackTrace();
        }

        return "redirect:/admin-page/vehiclemanagement/add";  // Redirect to the form page after submission
    }
    
    @GetMapping("/admin-page/vehiclemanagement/update")
    public String showCarForm() {
        return "updatecar";
    }
    
    @GetMapping("/admin-page/vehiclemanagement/delete")
    public String deleteCarForm() {
        return "deletecar";
    }
    
    @GetMapping("/api/cars")
    public ResponseEntity<List<Car>> getAllCars() {
        List<Car> cars = carService.getAllCars(); // Assuming you have a method to fetch all cars
        return ResponseEntity.ok(cars);
    }
    
    @GetMapping("/cars/{id}/image")
    public ResponseEntity<byte[]> getCarImageById(@PathVariable Long id) {
        Optional<Car> carOptional = carService.getCarById(id);

        // Check if the car exists and has an image
        if (carOptional.isEmpty() || carOptional.get().getImage() == null) {
            return ResponseEntity.notFound().build();
        }

        byte[] image = carOptional.get().getImage();

        // Set the content type based on the image format (you can adjust this if using PNG)
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_PNG)
                .body(image);
    }
    
    @GetMapping("/admin-page/vehiclemanagement/update/{id}")
    public String showUpdateCarForm(@PathVariable Long id, Model model) {
        Optional<Car> carOptional = carService.getCarById(id);
        if (carOptional.isPresent()) {
            model.addAttribute("car", carOptional.get());  // Add the car object to the model to prepopulate the form
            return "updatecarform";  // This is the JSP or HTML file where the form will be
        } else {
            return "redirect:/admin-page/vehiclemanagement";  // Redirect if car not found
        }
    }
    
    @PostMapping("/admin-page/vehiclemanagement/update/{id}")
    public String updateCar(@PathVariable Long id, @ModelAttribute Car car, @RequestParam("media") MultipartFile imageFile, 
                            RedirectAttributes redirectAttributes) {
        Optional<Car> existingCarOptional = carService.getCarById(id);

        if (existingCarOptional.isPresent()) {
            Car existingCar = existingCarOptional.get();

            // Update fields from form to the existing car object
            existingCar.setManufacturer(car.getManufacturer());
            existingCar.setModel(car.getModel());
            existingCar.setPrice(car.getPrice());
            existingCar.setFuelType(car.getFuelType());
            existingCar.setKilometersDriven(car.getKilometersDriven());
            existingCar.setCity(car.getCity());
            existingCar.setYearOfPurchase(car.getYearOfPurchase());
            existingCar.setTransmission(car.getTransmission());
            existingCar.setBodyType(car.getBodyType());
            existingCar.setNumberOfOwners(car.getNumberOfOwners());
            existingCar.setColor(car.getColor());

            // Handle image update
            try {
                if (!imageFile.isEmpty()) {
                    existingCar.setImage(imageFile.getBytes());
                }
                carService.updateCar(existingCar); // Save updated car
                redirectAttributes.addFlashAttribute("message", "Car details updated successfully!");
            } catch (IOException e) {
                redirectAttributes.addFlashAttribute("error", "Failed to update car.");
                e.printStackTrace();
            }
        } else {
            redirectAttributes.addFlashAttribute("error", "Car not found.");
        }

        return "redirect:/admin-page/vehiclemanagement/update/" + id;  // Redirect to the update form after submission
    }

    @DeleteMapping("/admin-page/vehiclemanagement/delete/{id}")
    @ResponseBody
    public ResponseEntity<?> deleteCar(@PathVariable Long id) {
        System.out.println("Attempting to delete car with ID: " + id); // Debugging log
        try {
            carService.deleteCarById(id);
            return ResponseEntity.ok("Car deleted successfully.");
        } catch (Exception e) {
            e.printStackTrace(); // Log the exception details
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting car.");
        }
    }


}
