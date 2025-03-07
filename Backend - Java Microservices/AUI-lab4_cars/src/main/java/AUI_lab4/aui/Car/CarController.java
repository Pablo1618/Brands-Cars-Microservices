package AUI_lab4.aui.Car;

import AUI_lab4.aui.Brand.BrandReadDTO;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/cars")
public class CarController {

    private final CarService carService;

    @Autowired
    public CarController(CarService carService) {
        this.carService = carService;
    }

    @GetMapping
    public ResponseEntity<List<CarReadDTO>> getAllCars() {
        List<CarReadDTO> cars = carService.getAllCars();
        return new ResponseEntity<>(cars, HttpStatus.OK);
    }

    @GetMapping("/{carId}")
    public ResponseEntity<?> getCarById(@PathVariable UUID carId) {
        try {
            CarDetailsDTO car = carService.getCarById(carId);
            return new ResponseEntity<>(car, HttpStatus.OK);
        }catch (EntityNotFoundException e) {{
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("{\"errorMessage\": \"Car with this ID doesn't exist: " + e.getMessage() + "\"}");
        }
        }
    }

    @DeleteMapping("/{carId}")
    public ResponseEntity<String> deleteCar(@PathVariable UUID carId) {
        boolean removed = carService.removeCar(carId);
        if (!removed) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("{\"errorMessage\": \"Car with this ID doesn't exist!\"}");
        }
        return ResponseEntity.status(HttpStatus.OK)
                .body("{\"message\": \"Car with ID " + carId + " was successfully deleted\"}");
    }

    @PutMapping("/{carId}")
    public ResponseEntity<Object> updateCar(@PathVariable UUID carId, @RequestBody CarCreateDTO carCreateDTO) {
        boolean updated = carService.updateCar(carId, carCreateDTO);
        if (!updated) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("{\"errorMessage\": \"Car with this ID doesn't exist or brand with this name doesn't exist!\"}");
        }
        return ResponseEntity.status(HttpStatus.OK)
                .body("{\"message\": \"Car with ID " + carId + " was successfully updated\"}");
    }

    @PostMapping
    public ResponseEntity<Object> addCar(@RequestBody CarCreateDTO carCreateDTO) {
        try {
            CarReadDTO carReadDTO = carService.addCar(carCreateDTO);
            return new ResponseEntity<>(carReadDTO, HttpStatus.CREATED);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("{\"errorMessage\": \"Brand with this name doesn't exist: " + e.getMessage() + "\"}");
        }
    }

    @GetMapping("/brandname/{brandName}")
    public ResponseEntity<Object> getCarsByBrand(@PathVariable String brandName) {
        try{
            List<CarReadDTO> cars = carService.getCarsByBrand(brandName);
            if (cars.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("{\"Message\": \"There are no cars that belong to this brand\"}");
            }
            return new ResponseEntity<>(cars, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("{\"errorMessage\": \"Brand with this ID doesn't exist: " + e.getMessage() + "\"}");
        }
    }
}
