package AUI_lab4.aui.Car;

import AUI_lab4.aui.Brand.BrandReadDTO;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CarService {

    private final CarRepository carRepository;
    private final RestTemplate restTemplate;

    @Autowired
    public CarService(CarRepository carRepository, RestTemplate restTemplate) {
        this.carRepository = carRepository;
        this.restTemplate = restTemplate;
    }

    public boolean updateCar(UUID carId, CarCreateDTO carCreateDTO){
        Optional<Car> optionalCar = carRepository.findById(carId);

        if (optionalCar.isPresent()) {
            Car car = optionalCar.get();

            if (carCreateDTO.getModelName() != null) {
                car.setModel(carCreateDTO.getModelName());
            }
            if (carCreateDTO.getBrandName() != null) {
                //String url = "http://localhost:8081/api/brands/name/" + carCreateDTO.getBrandName();
                String url = "http://brand-microservice:8081/api/brands/name/" + carCreateDTO.getBrandName();
                BrandReadDTO brandReadDTO;
                try {
                    ResponseEntity<BrandReadDTO> response = restTemplate.exchange(
                            url, HttpMethod.GET, null, BrandReadDTO.class);
                    brandReadDTO = response.getBody();
                } catch (HttpClientErrorException.NotFound ex) {
                    return false;
                }
                car.setBrandId(brandReadDTO.getId());
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

        //String url = "http://localhost:8081/api/brands/name/" + carCreateDTO.getBrandName();
        String url = "http://brand-microservice:8081/api/brands/name/" + carCreateDTO.getBrandName();
        BrandReadDTO brandReadDTO;
        try {
            ResponseEntity<BrandReadDTO> response = restTemplate.exchange(url, HttpMethod.GET, null, BrandReadDTO.class);
            brandReadDTO = response.getBody();
        } catch (HttpClientErrorException.NotFound ex) {
            throw new EntityNotFoundException(carCreateDTO.getBrandName());
        }

        Car car = new Car();
        car.setModel(carCreateDTO.getModelName());
        car.setId(carCreateDTO.getId());
        car.setProductionYear(carCreateDTO.getProductionYear());
        car.setBrandId(brandReadDTO.getId());

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

        //String url = "http://localhost:8081/api/brands/" + car.getBrandId();
        String url = "http://brand-microservice:8081/api/brands/" + car.getBrandId();

        BrandReadDTO brandReadDTO;

        ResponseEntity<BrandReadDTO> response = restTemplate.exchange(url, HttpMethod.GET, null, BrandReadDTO.class);
        brandReadDTO = response.getBody();


        return CarDetailsDTO.builder()
                .id(car.getId())
                .modelName(car.getModel())
                .productionYear(car.getProductionYear())
                .brandId(car.getBrandId())
                .brandName(brandReadDTO.getBrandName())
                .build();
    }

    public List<CarReadDTO> getCarsByBrand(String brandName) {

        //String url = "http://localhost:8081/api/brands/name/" + brandName;
        String url = "http://brand-microservice:8081/api/brands/name/" + brandName;
        BrandReadDTO brandReadDTO;
        try {
            ResponseEntity<BrandReadDTO> response = restTemplate.exchange(url, HttpMethod.GET, null, BrandReadDTO.class);
            brandReadDTO = response.getBody();
        } catch (HttpClientErrorException.NotFound ex) {
            throw new EntityNotFoundException(brandName);
        }

        UUID brandId = brandReadDTO.getId();
        List<Car> cars = carRepository.findByBrandId(brandId);
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
