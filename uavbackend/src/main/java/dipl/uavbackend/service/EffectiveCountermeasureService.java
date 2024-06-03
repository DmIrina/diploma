package dipl.uavbackend.service;

import dipl.uavbackend.dto.EffectiveCountermeasureDto;
import dipl.uavbackend.entity.EffectiveCountermeasure;

import java.util.List;

public interface EffectiveCountermeasureService {
    EffectiveCountermeasureDto createEffectiveCountermeasure(EffectiveCountermeasureDto effectiveCountermeasureDto);

    EffectiveCountermeasureDto getEffectiveCountermeasureById(Long effectiveCountermeasureId);

    List<EffectiveCountermeasureDto> getAllEffectiveCountermeasures();

    List<EffectiveCountermeasure> getAllEffectiveCountermeasuresList();

    EffectiveCountermeasureDto updateEffectiveCountermeasure(Long effectiveCountermeasureId, EffectiveCountermeasureDto effectiveCountermeasureDto);

    void deleteEffectiveCountermeasure(Long effectiveCountermeasureId);
}
