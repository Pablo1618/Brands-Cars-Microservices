package AUI_lab2.aui.Brand;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class BrandService {

    private final BrandRepository brandRepository;

    @Autowired
    public BrandService(BrandRepository brandRepository) {
        this.brandRepository = brandRepository;
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

    public boolean removeBrand(UUID brandId) {
        if (brandRepository.existsById(brandId)) {
            brandRepository.deleteById(brandId);
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
                    .brandName(brand.getName())
                    .build();
        }
        return null;
    }
}
