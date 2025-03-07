package AUI_lab2.aui.Car;

import lombok.Builder;
import lombok.Data;
import java.util.UUID;

@Builder
@Data
public class CarCreateDTO {
    private UUID id;
    private String modelName;
    private int productionYear;
    private String brandName;
}

