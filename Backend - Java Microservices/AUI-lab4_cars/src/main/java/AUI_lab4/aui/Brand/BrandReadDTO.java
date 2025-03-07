package AUI_lab4.aui.Brand;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Builder
@Data
public class BrandReadDTO {
    private UUID id;
    private String brandName;
}
