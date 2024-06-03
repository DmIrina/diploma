package dipl.uavbackend.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "modelparam")
public class ModelParam {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "modelParamName")
    private String modelParamName;

    @Column(name = "nuav")
    private int nuav;

    @Column(name = "nuavingroups")
    private int nuavingroups;

    @Column(name = "timeBetweenGroups")
    private int timeBetweenGroups;

    @Column(name = "timeRecharge")
    private int timeRecharge;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "uav_id")
    private Uav uav;


}
