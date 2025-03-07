package AUI_lab2.aui.Brand;

import AUI_lab2.aui.Car.Car;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Builder
@Data
@Entity
@Table(name = "brands")
public class Brand implements Serializable {

    @Id
    @Column(name = "id")
    private UUID id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "brand", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Car> cars = new ArrayList<>();

    protected Brand() {}

    public Brand(UUID id, String name, List<Car> cars) {
        this.id = id;
        this.name = name;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.name);
    }
}
