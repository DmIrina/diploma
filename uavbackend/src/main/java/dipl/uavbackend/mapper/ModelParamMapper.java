package dipl.uavbackend.mapper;

import dipl.uavbackend.dto.ModelParamDto;
import dipl.uavbackend.entity.ModelParam;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ModelParamMapper {
    public static ModelParamDto mapToModelParamDto(ModelParam modelParam) {
        return new ModelParamDto(
                modelParam.getId(),
                modelParam.getModelParamName(),
                modelParam.getNuav(),
                modelParam.getNuavingroups(),
                modelParam.getTimeBetweenGroups(),
                modelParam.getTimeRecharge(),
                modelParam.getUav().getId()
        );
    }

    public static ModelParam mapToModelParam(ModelParamDto modelParamDto) {
        ModelParam modelParam = new ModelParam();
        modelParam.setModelParamName(modelParamDto.getModelParamName());
        modelParam.setNuav(modelParamDto.getNuav());
        modelParam.setNuavingroups(modelParamDto.getNuavingroups());
        modelParam.setTimeRecharge(modelParamDto.getTimeRecharge());
        modelParam.setTimeBetweenGroups(modelParamDto.getTimeBetweenGroups());
        return modelParam;
    }
}
