package dipl.uavbackend.mapper;
import dipl.uavbackend.dto.EffectiveUavDto;
import dipl.uavbackend.entity.EffectiveUav;

public class EffectiveUavMapper {
    public static EffectiveUavDto mapToEffectiveUavDto(EffectiveUav effectiveUav) {
        return new EffectiveUavDto(
          effectiveUav.getId(),
          effectiveUav.getX(),
          effectiveUav.getY(),
          effectiveUav.isStatus(),
          effectiveUav.getUav().getId()
        );
    }

    public static EffectiveUav mapToEffectiveUav(EffectiveUavDto effectiveUavDto) {
        EffectiveUav effectiveUav = new EffectiveUav();
        effectiveUav.setId(effectiveUavDto.getId());
        effectiveUav.setX(effectiveUavDto.getX());
        effectiveUav.setY(effectiveUavDto.getY());
        effectiveUav.setStatus(effectiveUavDto.isStatus());
        return effectiveUav;
    }
}
