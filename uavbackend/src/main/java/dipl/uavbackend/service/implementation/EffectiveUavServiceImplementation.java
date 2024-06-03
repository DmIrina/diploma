package dipl.uavbackend.service.implementation;

import dipl.uavbackend.dto.EffectiveUavDto;
import dipl.uavbackend.entity.EffectiveUav;
import dipl.uavbackend.entity.Uav;
import dipl.uavbackend.exception.ResourceNotFoundException;
import dipl.uavbackend.mapper.EffectiveUavMapper;
import dipl.uavbackend.repository.EffectiveUavRepository;
import dipl.uavbackend.repository.UavRepository;
import dipl.uavbackend.service.EffectiveUavService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class EffectiveUavServiceImplementation implements EffectiveUavService {
    @Autowired
    private EffectiveUavRepository effectiveUavRepository;

    @Autowired
    private UavRepository departmentRepository;

    @Override
    public EffectiveUavDto createEffectiveUav(EffectiveUavDto effectiveUavDto) {
        EffectiveUav effectiveUav = EffectiveUavMapper.mapToEffectiveUav(effectiveUavDto);

        Uav department = departmentRepository.findById(effectiveUavDto.getUavId())
                .orElseThrow(() -> new ResourceNotFoundException("Uav was not found with id: "
                        + effectiveUavDto.getUavId()));
        effectiveUav.setUav(department);
        EffectiveUav savedEffectiveUav = effectiveUavRepository.save(effectiveUav);
        return EffectiveUavMapper.mapToEffectiveUavDto(savedEffectiveUav);
    }

    @Override
    public EffectiveUavDto getEffectiveUavById(Long effectiveUavId) {
        EffectiveUav effectiveUav = effectiveUavRepository.findById(effectiveUavId).orElseThrow(() ->
                new ResourceNotFoundException("EffectiveUav was not found with given id: " + effectiveUavId));
        return EffectiveUavMapper.mapToEffectiveUavDto(effectiveUav);
    }

    @Override
    public List<EffectiveUavDto> getAllEffectiveUavs() {
        List<EffectiveUav> effectiveUavList = effectiveUavRepository.findAll();
        return effectiveUavList.stream()
                .map(EffectiveUavMapper::mapToEffectiveUavDto)
                .collect(Collectors.toList());
    }

    @Override
    public EffectiveUavDto updateEffectiveUav(Long effectiveUavId, EffectiveUavDto effectiveUavDto) {
        EffectiveUav effectiveUav = effectiveUavRepository.findById(effectiveUavId).orElseThrow(() ->
                new ResourceNotFoundException("EffectiveUav was not found with given id: " + effectiveUavId));


        effectiveUav.setX(effectiveUavDto.getX());
        effectiveUav.setY(effectiveUavDto.getY());
        effectiveUav.setStatus(effectiveUavDto.isStatus());

        Uav department = departmentRepository.findById(effectiveUavDto.getUavId())
                .orElseThrow(() -> new ResourceNotFoundException("Uav was not found with id: "
                        + effectiveUavDto.getUavId()));
        effectiveUav.setUav(department);

        EffectiveUav savedEffectiveUav = effectiveUavRepository.save(effectiveUav);
        return EffectiveUavMapper.mapToEffectiveUavDto(savedEffectiveUav);
    }

    @Override
    public void deleteEffectiveUav(Long effectiveUavId) {
        EffectiveUav effectiveUav = effectiveUavRepository.findById(effectiveUavId).orElseThrow(() ->
                new ResourceNotFoundException("EffectiveUav was not found with given id: " + effectiveUavId));
        effectiveUavRepository.deleteById(effectiveUavId);
    }
}
