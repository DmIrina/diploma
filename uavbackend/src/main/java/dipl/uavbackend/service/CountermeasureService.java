package dipl.uavbackend.service;

import dipl.uavbackend.dto.CountermeasureDto;

import java.util.List;

public interface CountermeasureService {
    CountermeasureDto createCountermeasure(CountermeasureDto countermeasureDto);

    CountermeasureDto getCountermeasureById(Long countermeasureId);

    List<CountermeasureDto> getAllCountermeasures();

    CountermeasureDto updateCountermeasure(Long countermeasureId, CountermeasureDto countermeasureDto);

    void deleteCountermeasure(Long countermeasureId);
}
