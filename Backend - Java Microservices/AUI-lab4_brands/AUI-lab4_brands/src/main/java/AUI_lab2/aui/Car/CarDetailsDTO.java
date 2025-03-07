package AUI_lab2.aui.Car;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Builder
@Data
public class CarDetailsDTO {
    private String brandName;
    private String modelName;
    private int productionYear;
    private UUID id;
    private UUID brandId;
}

