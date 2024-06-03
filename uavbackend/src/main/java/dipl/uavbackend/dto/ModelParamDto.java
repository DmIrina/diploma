package dipl.uavbackend.dto;

import lombok.*;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class ModelParamDto {
    private Long id;
    private String modelParamName;
    private int nuav;
    private int nuavingroups;
    private int timeBetweenGroups;
    private int timeRecharge;
    private Long uavId;
}
