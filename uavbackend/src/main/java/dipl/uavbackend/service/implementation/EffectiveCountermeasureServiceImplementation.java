package dipl.uavbackend.service.implementation;

import dipl.uavbackend.dto.CountermeasureDto;
import dipl.uavbackend.dto.EffectiveCountermeasureDto;
import dipl.uavbackend.entity.Countermeasure;
import dipl.uavbackend.entity.EffectiveCountermeasure;
import dipl.uavbackend.exception.ResourceNotFoundException;
import dipl.uavbackend.mapper.CountermeasureMapper;
import dipl.uavbackend.mapper.EffectiveCountermeasureMapper;
import dipl.uavbackend.repository.CountermeasureRepository;
import dipl.uavbackend.repository.EffectiveCountermeasureRepository;
import dipl.uavbackend.service.CountermeasureService;
import dipl.uavbackend.service.EffectiveCountermeasureService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class EffectiveCountermeasureServiceImplementation implements EffectiveCountermeasureService {
    @Autowired
    private EffectiveCountermeasureRepository effectiveCountermeasureRepository;

    @Autowired
    private CountermeasureRepository cmRepository;

    @Autowired
    private CountermeasureService countermeasureService;

    @Override
    public EffectiveCountermeasureDto createEffectiveCountermeasure(EffectiveCountermeasureDto effectiveCountermeasureDto) {
        EffectiveCountermeasure effectiveCountermeasure = EffectiveCountermeasureMapper.mapToEffectiveCountermeasure(effectiveCountermeasureDto);

        Countermeasure cm = cmRepository.findById(effectiveCountermeasureDto.getCountermeasureId())
                .orElseThrow(() -> new ResourceNotFoundException("Countermeasure was not found with id: "
                        + effectiveCountermeasureDto.getCountermeasureId()));
        effectiveCountermeasure.setCountermeasure(cm);
        EffectiveCountermeasure savedEffectiveCountermeasure = effectiveCountermeasureRepository.save(effectiveCountermeasure);
        return EffectiveCountermeasureMapper.mapToEffectiveCountermeasureDto(savedEffectiveCountermeasure);
    }

    @Override
    public EffectiveCountermeasureDto getEffectiveCountermeasureById(Long effectiveCountermeasureId) {
        EffectiveCountermeasure effectiveCountermeasure = effectiveCountermeasureRepository.findById(effectiveCountermeasureId).orElseThrow(() ->
                new ResourceNotFoundException("EffectiveCountermeasure was not found with given id: " + effectiveCountermeasureId));
        return EffectiveCountermeasureMapper.mapToEffectiveCountermeasureDto(effectiveCountermeasure);
    }

    @Override
    public List<EffectiveCountermeasureDto> getAllEffectiveCountermeasures() {
        List<EffectiveCountermeasure> effectiveCountermeasureList = effectiveCountermeasureRepository.findAll();
        return effectiveCountermeasureList.stream()
                .map(EffectiveCountermeasureMapper::mapToEffectiveCountermeasureDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<EffectiveCountermeasure> getAllEffectiveCountermeasuresList() {
        List<EffectiveCountermeasureDto> effectiveCountermeasureDtoList = getAllEffectiveCountermeasures();
        List<EffectiveCountermeasure> list = new ArrayList<>();

        for (var effectiveCountermeasureDto: effectiveCountermeasureDtoList) {

            EffectiveCountermeasure effectiveCountermeasure = EffectiveCountermeasureMapper.mapToEffectiveCountermeasure(effectiveCountermeasureDto);
            CountermeasureDto cmDto = countermeasureService.getCountermeasureById(effectiveCountermeasureDto.getCountermeasureId());
            Countermeasure cm = CountermeasureMapper.mapToCountermeasure(cmDto);
            effectiveCountermeasure.setCountermeasure(cm);
            list.add(effectiveCountermeasure);
        }
        return list;
    }

    @Override
    public EffectiveCountermeasureDto updateEffectiveCountermeasure(Long effectiveCountermeasureId, EffectiveCountermeasureDto effectiveCountermeasureDto) {
        EffectiveCountermeasure effectiveCountermeasure = effectiveCountermeasureRepository.findById(effectiveCountermeasureId).orElseThrow(() ->
                new ResourceNotFoundException("EffectiveCountermeasure was not found with given id: " + effectiveCountermeasureId));


        effectiveCountermeasure.setX(effectiveCountermeasureDto.getX());
        effectiveCountermeasure.setY(effectiveCountermeasureDto.getY());
        effectiveCountermeasure.setCredibility(effectiveCountermeasureDto.getCredibility());

        Countermeasure cm = cmRepository.findById(effectiveCountermeasureDto.getCountermeasureId())
                .orElseThrow(() -> new ResourceNotFoundException("Countermeasure was not found with id: "
                        + effectiveCountermeasureDto.getCountermeasureId()));
        effectiveCountermeasure.setCountermeasure(cm);

        EffectiveCountermeasure savedEffectiveCountermeasure = effectiveCountermeasureRepository.save(effectiveCountermeasure);
        return EffectiveCountermeasureMapper.mapToEffectiveCountermeasureDto(savedEffectiveCountermeasure);
    }

    @Override
    public void deleteEffectiveCountermeasure(Long effectiveCountermeasureId) {
        EffectiveCountermeasure effectiveCountermeasure = effectiveCountermeasureRepository.findById(effectiveCountermeasureId).orElseThrow(() ->
                new ResourceNotFoundException("EffectiveCountermeasure was not found with given id: " + effectiveCountermeasureId));
        effectiveCountermeasureRepository.deleteById(effectiveCountermeasureId);
    }
}