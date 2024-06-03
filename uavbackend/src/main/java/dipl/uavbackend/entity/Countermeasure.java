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
@Table(name = "countermeasures")
public class Countermeasure {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "countermeasure_name", nullable = false, unique = true)
    private String countermeasureName;

    @Column(name = "cm_type")
    private String cmType;

    @Column(name = "h_min")
    private int hMin;   // нижня стеля (м)

    @Column(name = "h_max")
    private int hMax;   // верхня стеля (м)

    @Column(name = "distance")
    private int distance;   // радіус дії (м)

    // кількість ракет (ЗРК), одночасно супроводж цілей (РЛС, пеленгатор),
    // напрямків подавлення (РЕБ)
    @Column(name = "n_channels")
    private int nChannels;

    // вірогідність збиття (ЗРК), виявлення (РЛС, пеленгатор), подавлення (РЕБ)
    @Column(name = "damage_probability")
    private double damageProbability;

    private int nChannelsLeft = nChannels;

    public void useChannel() {
        nChannels--;
    }

    public void rechargeOneChannel() {
        nChannels++;
    }
}
