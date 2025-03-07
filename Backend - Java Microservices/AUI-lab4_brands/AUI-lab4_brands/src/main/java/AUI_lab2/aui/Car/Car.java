package AUI_lab2.aui.Car;

import AUI_lab2.aui.Brand.Brand;
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

    @ManyToOne
    @JoinColumn(name = "brand_id", nullable = false)
    private Brand brand;

    protected Car() {}

    public Car(UUID id, String model, int year, Brand brand) {
        this.id = id;
        this.model = model;
        this.productionYear = year;
        this.brand = brand;
    }
}

