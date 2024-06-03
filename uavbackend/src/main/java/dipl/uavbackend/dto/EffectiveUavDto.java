package dipl.uavbackend.dto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EffectiveUavDto {
    private Long id;
    private int x;
    private int y;
    private boolean status;
    private Long uavId;
}
