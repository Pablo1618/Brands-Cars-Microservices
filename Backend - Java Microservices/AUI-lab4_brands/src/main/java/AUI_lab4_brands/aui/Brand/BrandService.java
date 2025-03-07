package AUI_lab4_brands.aui.Brand;

import AUI_lab4_brands.aui.Car.CarReadDTO;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class BrandService {

    private final BrandRepository brandRepository;
    private final RestTemplate restTemplate;

    @Autowired
    public BrandService(BrandRepository brandRepository, RestTemplate restTemplate) {
        this.brandRepository = brandRepository;
        this.restTemplate = restTemplate;
    }

    public Brand initializeBrand(Brand brand) {
        return brandRepository.save(brand);
    }

    public BrandReadDTO addBrand(BrandCreateDTO brandCreateDTO) {
        Brand brand = new Brand();
        brand.setId(brandCreateDTO.getId());
        brand.setName(brandCreateDTO.getBrandName());

        Brand savedBrand = brandRepository.save(brand);

        return new BrandReadDTO(savedBrand.getId(), savedBrand.getName());
    }

    public boolean removeBrand(String brandName) {

        Optional<Brand> repositoryToDelete = brandRepository.findByName(brandName);

        if (repositoryToDelete.isPresent()) {

            //String url = "http://localhost:8082/api/cars/brandname/" + brandName;
            String url = "http://car-microservice:8082/api/cars/brandname/" + brandName;
            List<CarReadDTO> cars;
            try {
                ResponseEntity<List<CarReadDTO>> response = restTemplate.exchange(
                        url,
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<List<CarReadDTO>>() {}
                );
                cars = response.getBody();
            } catch (HttpClientErrorException.NotFound ex) {
                brandRepository.delete(repositoryToDelete.get());
                return true;
            }

            if (cars != null && !cars.isEmpty()) {
                for (CarReadDTO car : cars) {
                    //String deleteCarUrl = "http://localhost:8082/api/cars/" + car.getId();
                    String deleteCarUrl = "http://car-microservice:8082/api/cars/" + car.getId();
                    restTemplate.exchange(deleteCarUrl, HttpMethod.DELETE, null, Void.class);
                }
            }

            brandRepository.delete(repositoryToDelete.get());
            return true;
        }

        return false;
    }

    public List<BrandReadDTO> getAllBrands() {
        List<Brand> brands = brandRepository.findAll();
        return brands.stream()
                .map(brand -> BrandReadDTO.builder()
                        .id(brand.getId())
                        .brandName(brand.getName())
                        .build())
                .collect(Collectors.toList());
    }

    public BrandReadDTO getBrandById(UUID brandId) {
        Brand brand = brandRepository.findById(brandId).orElse(null);
        if (brand != null) {
            return BrandReadDTO.builder()
                    .id(brand.getId())
                    .brandName(brand.getName())
                    .build();
        }
        return null;
    }

    public BrandReadDTO getBrandByName(String brandName) {
        Brand brand = brandRepository.findByName(brandName).orElse(null);
        if (brand != null) {
            return BrandReadDTO.builder()
                    .id(brand.getId())
                    .brandName(brand.getName())
                    .build();
        }
        return null;
    }

    public boolean updateBrand(UUID brandId, BrandCreateDTO brandCreateDTO){
        Optional<Brand> optionalBrand = brandRepository.findById(brandId);

        if (optionalBrand.isPresent()) {
            Brand brand = optionalBrand.get();

            if (brandCreateDTO.getBrandName() != null) {
                brand.setName(brandCreateDTO.getBrandName());
            }

            brandRepository.save(brand);

            return true;
        } else {
            return false;
        }
    }
}
