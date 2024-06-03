package dipl.uavbackend.dto;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EffectiveCountermeasureDto {
    private Long id;
    private int x;
    private int y;
    private double credibility;
    private Long countermeasureId;
}
