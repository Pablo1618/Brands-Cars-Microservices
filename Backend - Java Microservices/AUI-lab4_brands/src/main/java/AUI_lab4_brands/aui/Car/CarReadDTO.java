package AUI_lab4_brands.aui.Car;

import jakarta.persistence.Entity;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Builder
@Data
public class CarReadDTO {
    private UUID id;
    private String modelName;
}

