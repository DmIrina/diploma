package dipl.uavbackend.dto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CountermeasureDto {
    private Long id;
    private String countermeasureName;
    private String cmType;
    private int hMin;
    private int hMax;
    private int distance;
    private int nChannels;
    private double damageProbability;
}
