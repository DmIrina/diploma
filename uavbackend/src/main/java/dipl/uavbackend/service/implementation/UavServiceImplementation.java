package dipl.uavbackend.service.implementation;

import dipl.uavbackend.dto.UavDto;
import dipl.uavbackend.entity.Uav;
import dipl.uavbackend.exception.ResourceNotFoundException;
import dipl.uavbackend.mapper.UavMapper;
import dipl.uavbackend.repository.UavRepository;
import dipl.uavbackend.service.UavService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UavServiceImplementation implements UavService {

    @Autowired
    private UavRepository uavRepository;

    @Override
    public UavDto createUav(UavDto uavDto) {
        Uav uav = UavMapper.mapToUav(uavDto);
        Uav savedUav = uavRepository.save(uav);
        return UavMapper.mapToUavDto(savedUav);
    }

    @Override
    public UavDto getUavById(Long uavId) {
        Uav uav = uavRepository.findById(uavId)
                .orElseThrow(() -> new ResourceNotFoundException("БПЛА не знайдено id: " + uavId));
        return UavMapper.mapToUavDto(uav);
    }

    @Override
    public List<UavDto> getAllUavs() {
        return uavRepository.findAll()
                .stream().map(UavMapper::mapToUavDto)
                .collect(Collectors.toList());
    }

    @Override
    public UavDto updateUav(Long uavId, UavDto uavDto) {
        Uav uav = uavRepository.findById(uavId)
                .orElseThrow(() -> new ResourceNotFoundException("БПЛА не знайдено id: " + uavId));
        uav.setUavName(uavDto.getUavName());
        uav.setHMin(uavDto.getHMin());
        uav.setHMax(uavDto.getHMax());
        uav.setDistance(uavDto.getDistance());
        uav.setSpeed(uavDto.getSpeed());
        uav.setResistance(uavDto.getResistance());
        uav.setNoiseimmunity(uavDto.getNoiseimmunity());
        uav.setStealth(uavDto.getStealth());
        uav.setEag(uavDto.getEag());
        Uav updatedUav = uavRepository.save(uav);
        return UavMapper.mapToUavDto(updatedUav);
    }

    @Override
    public void deleteUav(Long uavId) {
        Uav uav = uavRepository.findById(uavId)
                .orElseThrow(() -> new ResourceNotFoundException("БПЛА не знайдено id: " + uavId));
        uavRepository.deleteById(uavId);
    }
}
