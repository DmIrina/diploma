package dipl.uavbackend.mapper;

import dipl.uavbackend.dto.EffectiveCountermeasureDto;
import dipl.uavbackend.entity.EffectiveCountermeasure;

public class EffectiveCountermeasureMapper {
    public static EffectiveCountermeasureDto mapToEffectiveCountermeasureDto(EffectiveCountermeasure effectiveCountermeasure) {
        return new EffectiveCountermeasureDto(
                effectiveCountermeasure.getId(),
                effectiveCountermeasure.getX(),
                effectiveCountermeasure.getY(),
                effectiveCountermeasure.getCredibility(),
                effectiveCountermeasure.getCountermeasure().getId()
        );
    }

    public static EffectiveCountermeasure mapToEffectiveCountermeasure(EffectiveCountermeasureDto effectiveCountermeasureDto) {
        EffectiveCountermeasure effectiveCountermeasure = new EffectiveCountermeasure();
        effectiveCountermeasure.setId(effectiveCountermeasureDto.getId());
        effectiveCountermeasure.setX(effectiveCountermeasureDto.getX());
        effectiveCountermeasure.setY(effectiveCountermeasureDto.getY());
        effectiveCountermeasure.setCredibility(effectiveCountermeasureDto.getCredibility());
        return effectiveCountermeasure;
    }
}
