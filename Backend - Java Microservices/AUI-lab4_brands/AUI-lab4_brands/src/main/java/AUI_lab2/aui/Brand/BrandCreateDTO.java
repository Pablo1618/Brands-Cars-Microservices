package AUI_lab2.aui.Brand;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Builder
@Data
public class BrandCreateDTO {
    private UUID id;
    private String brandName;
}
