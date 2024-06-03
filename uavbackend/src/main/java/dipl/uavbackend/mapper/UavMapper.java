package dipl.uavbackend.mapper;
import dipl.uavbackend.dto.UavDto;
import dipl.uavbackend.entity.Uav;

public class UavMapper {
    public static UavDto mapToUavDto(Uav uav) {
        return new UavDto(
                uav.getId(),
                uav.getUavName(),
                uav.getHMin(),
                uav.getHMax(),
                uav.getDistance(),
                uav.getSpeed(),
                uav.getResistance(),
                uav.getNoiseimmunity(),
                uav.getStealth(),
                uav.getEag()
        );
    }

    public static Uav mapToUav(UavDto uavDto) {
        return new Uav(
                uavDto.getId(),
                uavDto.getUavName(),
                uavDto.getHMin(),
                uavDto.getHMax(),
                uavDto.getDistance(),
                uavDto.getSpeed(),
                uavDto.getResistance(),
                uavDto.getNoiseimmunity(),
                uavDto.getStealth(),
                uavDto.getEag()
        );
    }
}
