package dipl.uavbackend.service.implementation;

import dipl.uavbackend.dto.CountermeasureDto;
import dipl.uavbackend.entity.Countermeasure;
import dipl.uavbackend.exception.ResourceNotFoundException;
import dipl.uavbackend.mapper.CountermeasureMapper;
import dipl.uavbackend.repository.CountermeasureRepository;
import dipl.uavbackend.service.CountermeasureService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CountermeasureServiceImplementation implements CountermeasureService {

    @Autowired
    private CountermeasureRepository countermeasureRepository;

    @Override
    public CountermeasureDto createCountermeasure(CountermeasureDto countermeasureDto) {
        Countermeasure countermeasure = CountermeasureMapper.mapToCountermeasure(countermeasureDto);
        Countermeasure savedCountermeasure = countermeasureRepository.save(countermeasure);
        return CountermeasureMapper.mapToCountermeasureDto(savedCountermeasure);
    }

    @Override
    public CountermeasureDto getCountermeasureById(Long countermeasureId) {
        Countermeasure countermeasure = countermeasureRepository.findById(countermeasureId)
                .orElseThrow(() -> new ResourceNotFoundException("Засіб протидії не знайдено id: " + countermeasureId));
        return CountermeasureMapper.mapToCountermeasureDto(countermeasure);
    }

    @Override
    public List<CountermeasureDto> getAllCountermeasures() {
        return countermeasureRepository.findAll()
                .stream().map(CountermeasureMapper::mapToCountermeasureDto)
                .collect(Collectors.toList());
    }

    @Override
    public CountermeasureDto updateCountermeasure(Long countermeasureId, CountermeasureDto countermeasureDto) {
        Countermeasure countermeasure = countermeasureRepository.findById(countermeasureId)
                .orElseThrow(() -> new ResourceNotFoundException("Засіб протидії не знайдено id: " + countermeasureId));
        countermeasure.setCountermeasureName(countermeasureDto.getCountermeasureName());
        countermeasure.setCmType(countermeasureDto.getCmType());
        countermeasure.setHMin(countermeasureDto.getHMin());
        countermeasure.setHMax(countermeasureDto.getHMax());
        countermeasure.setDistance(countermeasureDto.getDistance());
        countermeasure.setNChannels(countermeasureDto.getNChannels());
        countermeasure.setDamageProbability(countermeasureDto.getDamageProbability());
        Countermeasure updatedCountermeasure = countermeasureRepository.save(countermeasure);
        return CountermeasureMapper.mapToCountermeasureDto(updatedCountermeasure);
    }

    @Override
    public void deleteCountermeasure(Long countermeasureId) {
        Countermeasure countermeasure = countermeasureRepository.findById(countermeasureId)
                .orElseThrow(() -> new ResourceNotFoundException("Засіб протидії не знайдено id: " + countermeasureId));
        countermeasureRepository.deleteById(countermeasureId);
    }
}
