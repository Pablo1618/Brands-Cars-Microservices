package AUI_lab2.aui.Car;

import AUI_lab2.aui.Brand.Brand;
import AUI_lab2.aui.Brand.BrandRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CarService {

    private final CarRepository carRepository;
    private final BrandRepository brandRepository;

    @Autowired
    public CarService(CarRepository carRepository, BrandRepository brandRepository) {
        this.carRepository = carRepository;
        this.brandRepository = brandRepository;
    }

    public boolean updateCar(UUID carId, CarCreateDTO carCreateDTO){
        Optional<Car> optionalCar = carRepository.findById(carId);

        if (optionalCar.isPresent()) {
            Car car = optionalCar.get();

            if (carCreateDTO.getModelName() != null) {
                car.setModel(carCreateDTO.getModelName());
            }
            if (carCreateDTO.getBrandName() != null) {
                Optional<Brand> optionalBrand = brandRepository.findByName(carCreateDTO.getBrandName());
                if (optionalBrand.isPresent()) {
                    Brand brand = optionalBrand.get();
                    car.setBrand((brand));
                } else {
                    return false;
                }
            }
            if (carCreateDTO.getProductionYear() != 0) {
                car.setProductionYear(carCreateDTO.getProductionYear());
            }

            carRepository.save(car);

            return true;
        } else {
            return false;
        }
    }

    public Car addCar(Car car) {
        return carRepository.save(car);
    }
    public CarReadDTO addCar(CarCreateDTO carCreateDTO) {
        Brand brand = brandRepository.findByName(carCreateDTO.getBrandName())
                .orElseThrow(() -> new EntityNotFoundException(carCreateDTO.getBrandName()));

        Car car = new Car();
        car.setModel(carCreateDTO.getModelName());
        car.setId(carCreateDTO.getId());
        car.setProductionYear(carCreateDTO.getProductionYear());
        car.setBrand(brand);

        Car savedCar = carRepository.save(car);

        return CarReadDTO.builder()
                .id(savedCar.getId())
                .modelName(savedCar.getModel())
                .build();
    }

    public List<CarReadDTO> getAllCars() {
        List<Car> cars = carRepository.findAll();
        return cars.stream()
                .map(car -> CarReadDTO.builder()
                        .id(car.getId())
                        .modelName(car.getModel())
                        .build())
                .collect(Collectors.toList());
    }

    public CarDetailsDTO getCarById(UUID carId) {
        Car car = carRepository.findById(carId)
                .orElseThrow(() -> new EntityNotFoundException(carId.toString()));
        return CarDetailsDTO.builder()
                .id(car.getId())
                .modelName(car.getModel())
                .productionYear(car.getProductionYear())
                .brandId(car.getBrand().getId())
                .brandName(car.getBrand().getName())
                .build();
    }

    public List<CarReadDTO> getCarsByBrand(UUID brandId) {
        if (!brandRepository.existsById(brandId)) {
            throw new EntityNotFoundException(brandId.toString());
        }
        List<Car> cars = carRepository.findByBrand_Id(brandId);
        if (cars.isEmpty()) {
            return new ArrayList<>();
        }
        return cars.stream()
                .map(car -> CarReadDTO.builder()
                        .id(car.getId())
                        .modelName(car.getModel())
                        .build())
                .collect(Collectors.toList());
    }

    public boolean removeCar(UUID carId) {
        if (carRepository.existsById(carId)) {
            carRepository.deleteById(carId);
            return true;
        }
        return false;
    }
}
