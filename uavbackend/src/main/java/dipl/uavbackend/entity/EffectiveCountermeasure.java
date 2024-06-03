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
@Table(name = "effective_countermeasures")
public class EffectiveCountermeasure {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "x")
    private int x;

    @Column(name = "y")
    private int y;

    @Column(name = "credibility")
    private double credibility;         // достовірність

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "countermeasure_id")
    private Countermeasure countermeasure;

}
