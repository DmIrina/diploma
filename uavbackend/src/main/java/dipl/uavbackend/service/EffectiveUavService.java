package dipl.uavbackend.service;

import dipl.uavbackend.dto.EffectiveUavDto;

import java.util.List;

public interface EffectiveUavService {
    EffectiveUavDto createEffectiveUav(EffectiveUavDto effectiveUavDto);

    EffectiveUavDto getEffectiveUavById(Long effectiveUavId);

    List<EffectiveUavDto> getAllEffectiveUavs();

    EffectiveUavDto updateEffectiveUav(Long effectiveUavId, EffectiveUavDto effectiveUavDto);

    void deleteEffectiveUav(Long effectiveUavId);
}
