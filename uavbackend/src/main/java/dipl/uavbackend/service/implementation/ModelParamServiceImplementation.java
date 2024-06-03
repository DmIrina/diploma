package dipl.uavbackend.service.implementation;

import dipl.uavbackend.dto.ModelParamDto;
import dipl.uavbackend.entity.ModelParam;
import dipl.uavbackend.entity.Uav;
import dipl.uavbackend.exception.ResourceNotFoundException;
import dipl.uavbackend.mapper.ModelParamMapper;
import dipl.uavbackend.repository.ModelParamRepository;
import dipl.uavbackend.repository.UavRepository;
import dipl.uavbackend.service.ModelParamService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ModelParamServiceImplementation implements ModelParamService {

    @Autowired
    private ModelParamRepository modelParamRepository;

    @Autowired
    private UavRepository uavRepository;

    @Override
    public ModelParamDto createModelParam(ModelParamDto modelParamDto) {
        ModelParam modelParam = ModelParamMapper.mapToModelParam(modelParamDto);
        Uav uav = uavRepository.findById(modelParamDto.getUavId())
                .orElseThrow(() -> new ResourceNotFoundException("Uav was not found with id: "
                        + modelParamDto.getUavId()));

        modelParam.setUav(uav);
        ModelParam savedModelParam = modelParamRepository.save(modelParam);
        return ModelParamMapper.mapToModelParamDto(savedModelParam);
    }

    @Override
    public ModelParamDto getModelParamById(Long modelParamId) {
        ModelParam modelParam = modelParamRepository.findById(modelParamId)
                .orElseThrow(() -> new ResourceNotFoundException("Не знайдено дані моделі: " + modelParamId));
        return ModelParamMapper.mapToModelParamDto(modelParam);
    }

    @Override
    public List<ModelParamDto> getAllModelParams() {
        return modelParamRepository.findAll()
                .stream().map(ModelParamMapper::mapToModelParamDto)
                .collect(Collectors.toList());
    }

    @Override
    public ModelParamDto updateModelParam(Long modelParamId, ModelParamDto modelParamDto) {
        ModelParam modelParam = modelParamRepository.findById(modelParamId)
                .orElseThrow(() -> new ResourceNotFoundException("Не знайдено дані моделі: " + modelParamId));

        modelParam.setModelParamName(modelParamDto.getModelParamName());
        modelParam.setNuav(modelParamDto.getNuav());
        modelParam.setNuavingroups(modelParamDto.getNuavingroups());
        modelParam.setTimeRecharge(modelParamDto.getTimeRecharge());
        modelParam.setTimeBetweenGroups(modelParamDto.getTimeBetweenGroups());
        Uav uav = uavRepository.findById(modelParamDto.getUavId()).orElseThrow(() ->
                new ResourceNotFoundException("Uav was not found with id: " + modelParamDto.getUavId()));
        modelParam.setUav(uav);

        ModelParam updatedModelParam = modelParamRepository.save(modelParam);
        return ModelParamMapper.mapToModelParamDto(updatedModelParam);
    }

    @Override
    public void deleteModelParam(Long modelParamId) {
        ModelParam modelParam = modelParamRepository.findById(modelParamId)
                .orElseThrow(() -> new ResourceNotFoundException("Не знайдено дані моделі: " + modelParamId));
        modelParamRepository.deleteById(modelParamId);
    }

    @Override
    public String getUavNameByUavId(Long uavId) {
        Uav uav = uavRepository.findById(uavId)
                .orElseThrow(() -> new ResourceNotFoundException("Uav was not found with id: " + uavId));
        return uav.getUavName();
    }

    @Override
    public Uav getUavByUavId(Long uavId) {
        Uav uav = uavRepository.findById(uavId)
                .orElseThrow(() -> new ResourceNotFoundException("Uav was not found with id: " + uavId));
        return uav;
    }
}