package dipl.uavbackend.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "effective_uavs")
public class EffectiveUav {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "x")
    private int x;

    @Column(name = "y")
    private int y;

    @Column(name = "status")
    private boolean status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "uav_id")
    private Uav uav;
}