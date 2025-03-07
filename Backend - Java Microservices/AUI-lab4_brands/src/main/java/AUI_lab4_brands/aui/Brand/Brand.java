package AUI_lab4_brands.aui.Brand;

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

    @ElementCollection
    @Column(name = "car_ids")
    private List<UUID> carIds = new ArrayList<>();

    protected Brand() {}

    public Brand(UUID id, String name, List<UUID> carIds) {
        this.id = id;
        this.name = name;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.name);
    }
}
