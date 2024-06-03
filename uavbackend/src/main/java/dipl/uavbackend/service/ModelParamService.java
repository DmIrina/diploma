package dipl.uavbackend.service;

import dipl.uavbackend.dto.ModelParamDto;
import dipl.uavbackend.entity.Uav;

import java.util.List;

public interface ModelParamService {
    ModelParamDto createModelParam(ModelParamDto modelParamDto);

    ModelParamDto getModelParamById(Long modelParamId);

    List<ModelParamDto> getAllModelParams();

    ModelParamDto updateModelParam(Long modelParamId, ModelParamDto modelParamDto);

    void deleteModelParam(Long modelParamId);

    String getUavNameByUavId(Long uavId);

    Uav getUavByUavId(Long uavId);
}
