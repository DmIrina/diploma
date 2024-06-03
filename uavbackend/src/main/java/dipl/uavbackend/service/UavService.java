package dipl.uavbackend.service;

import dipl.uavbackend.dto.UavDto;

import java.util.List;

public interface UavService {
    UavDto createUav(UavDto uavDto);

    UavDto getUavById(Long uavId);

    List<UavDto> getAllUavs();

    UavDto updateUav(Long uavId, UavDto uavDto);

    void deleteUav(Long uavId);
}
