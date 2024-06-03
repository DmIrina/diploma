package dipl.uavbackend.mapper;
import dipl.uavbackend.dto.RouteParamDto;
import dipl.uavbackend.entity.RouteParam;
import dipl.uavbackend.service.implementation.RouteServiceImplementation;

public class RouteParamMapper {
    public static RouteParamDto mapToRouteParamDto(RouteParam routeParam) {
        return new RouteParamDto(
                routeParam.getId(),
                routeParam.getDimX(),
                routeParam.getDimY(),
                routeParam.getLayers(),
                routeParam.getSourceX(),
                routeParam.getSourceY(),
                routeParam.getSourceZ(),
                routeParam.getTargetX(),
                routeParam.getTargetY(),
                routeParam.getTargetZ(),
                routeParam.getAlgorithmType(),
                routeParam.getRouteParamName(),
                routeParam.getTimestamp()
        );
    }

    public static RouteParam mapToRouteParam(RouteParamDto routeParamDto) {
        int[] layersArray;
        layersArray = RouteServiceImplementation.layersStringToLayersArr(routeParamDto.getLayers());

        return new RouteParam(
                routeParamDto.getId(),
                routeParamDto.getDimX(),
                routeParamDto.getDimY(),
                routeParamDto.getLayers(),
                routeParamDto.getSourceX(),
                routeParamDto.getSourceY(),
                routeParamDto.getSourceZ(),
                routeParamDto.getTargetX(),
                routeParamDto.getTargetY(),
                routeParamDto.getTargetZ(),
                routeParamDto.getAlgorithmType(),
                routeParamDto.getRouteParamName(),
                routeParamDto.getTimestamp(),
                layersArray
        );
    }
}
