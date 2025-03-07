package AUI_lab4.aui.Car;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Entity
@Data
@Builder
@Table(name = "cars")
public class Car {

    @Id
    @Column(name = "id")
    private UUID id;

    @Column(name = "model")
    private String model;

    @Column(name = "production_year")
    private int productionYear;

    @Column(name = "brand_id")
    private UUID brandId;

    protected Car() {}

    public Car(UUID id, String model, int year,  UUID brandId) {
        this.id = id;
        this.model = model;
        this.productionYear = year;
        this.brandId = brandId;
    }
}

