package dipl.uavbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class RouteParamDto {
    private Long id;
    private int dimX;
    private int dimY;
    private String layers;
    private int sourceX;
    private int sourceY;
    private int sourceZ;
    private int targetX;
    private int targetY;
    private int targetZ;
    private String algorithmType;
    private String routeParamName;
    private LocalDateTime timestamp = LocalDateTime.of(1980, 1, 1, 0, 0); // значення за замовчуванням
}