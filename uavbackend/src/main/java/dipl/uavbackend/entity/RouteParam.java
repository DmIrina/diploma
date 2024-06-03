package dipl.uavbackend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "routeparam")
public class RouteParam {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "dimX")
    private int dimX;

    @Column(name = "dimY")
    private int dimY;

    @Column(name = "layers")
    private String layers;

    @Column(name = "sourceX")
    private int sourceX;

    @Column(name = "sourceY")
    private int sourceY;

    @Column(name = "sourceZ")
    private int sourceZ;

    @Column(name = "targetX")
    private int targetX;

    @Column(name = "targetY")
    private int targetY;

    @Column(name = "targetZ")
    private int targetZ;

    @Column(name = "algorithmType")
    private String algorithmType;

    @Column(name = "routeParamName")
    private String routeParamName;

    @Column(columnDefinition = "TIMESTAMP DEFAULT '1980-01-01 00:00:00'")
    private LocalDateTime timestamp = LocalDateTime.of(1980, 1, 1, 0, 0); // значення за замовчуванням

    private int[] layersArray;

    public int[] getLayersArray() {
        return this.layersArray;
    }

    public void setLayersArray(int[] layers) {
        this.layersArray = layers;
    }
}

