package dipl.uavbackend.entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "uav")
public class Uav {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "uav_name", nullable = false, unique = true)
    private String uavName;

    @Column(name = "h_min")
    private int hMin;   // нижня стеля (м)

    @Column(name = "h_max")
    private int hMax;   // верхня стеля (м)

    @Column(name = "distance")
    private int distance;   // радіус дії (м)

    @Column(name = "speed")
    private int speed;

    // стійкість до ураження засобами ППО
    @Column(name = "resistance")
    private int resistance;

    // завадостійкість (РЕБ)
    @Column(name = "noiseimmunity")
    private int noiseimmunity;

    // невидимість
    @Column(name = "stealth")
    private int stealth;

    // ефективність дії в групі (effectiveness of action in the group - eag)
    @Column(name = "eag")
    private int eag;
}

