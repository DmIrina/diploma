package dipl.uavbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UavDto {
    private Long id;
    private String uavName;
    private int hMin;
    private int hMax;
    private int distance;
    private int speed;
    private int resistance;
    private int noiseimmunity;
    private int stealth;
    private int eag;
}
