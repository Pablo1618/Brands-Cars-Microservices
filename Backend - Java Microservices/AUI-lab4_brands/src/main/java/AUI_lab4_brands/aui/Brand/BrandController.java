package AUI_lab4_brands.aui.Brand;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/brands")
public class BrandController {

    private final BrandService brandService;

    @Autowired
    public BrandController(BrandService brandService) {
        this.brandService = brandService;
    }

    @GetMapping
    public ResponseEntity<List<BrandReadDTO>> getAllBrands() {
        List<BrandReadDTO> brands = brandService.getAllBrands();
        return new ResponseEntity<>(brands, HttpStatus.OK);
    }

    @GetMapping("/{brandId}")
    public ResponseEntity<BrandReadDTO> getBrandById(@PathVariable UUID brandId) {
        BrandReadDTO brand = brandService.getBrandById(brandId);
        if (brand != null) {
            return ResponseEntity.ok(brand);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @GetMapping("/name/{brandName}")
    public ResponseEntity<BrandReadDTO> getBrandByName(@PathVariable String brandName) {
        BrandReadDTO brand = brandService.getBrandByName(brandName);
        if (brand != null) {
            return ResponseEntity.ok(brand);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PostMapping
    public ResponseEntity<BrandReadDTO> createBrand(@RequestBody BrandCreateDTO brandCreateDTO) {
        BrandReadDTO createdBrand = brandService.addBrand(brandCreateDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdBrand);
    }

    @DeleteMapping("/{brandName}")
    public ResponseEntity<String> deleteBrand(@PathVariable String brandName) {
        boolean removed = brandService.removeBrand(brandName);
        if (!removed) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("{\"errorMessage\": \"Brand with this ID doesn't exist!\"}");
        }
        return ResponseEntity.status(HttpStatus.OK)
                .body("{\"message\": \"Brand with name " + brandName + " was successfully deleted\"}");
    }

    @PutMapping("/{brandId}")
    public ResponseEntity<Object> updateBrand(@PathVariable UUID brandId, @RequestBody BrandCreateDTO brandCreateDTO) {
        boolean updated = brandService.updateBrand(brandId, brandCreateDTO);
        if (!updated) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("{\"errorMessage\": \"Brand with this ID doesn't exist!\"}");
        }
        return ResponseEntity.status(HttpStatus.OK)
                .body("{\"message\": \"Brand with ID " + brandId + " was successfully updated\"}");
    }
}
