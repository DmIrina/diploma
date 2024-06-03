package dipl.uavbackend.service;

import dipl.uavbackend.dto.RouteParamDto;
import dipl.uavbackend.entity.RouteParam;

import java.util.List;

public interface RouteService {
    RouteParamDto createRouteParam(RouteParamDto routeParamDto);

    RouteParamDto getRouteParamById(Long routeParamId);

    RouteParamDto getLatestRouteParam();

    RouteParam getParams(Long routeParamId);

    List<RouteParamDto> getAllRouteParams();

    RouteParamDto updateRouteParam(Long routeParamId, RouteParamDto routeParamDto);

    void deleteRouteParam(Long routeParamId);

    void calculateRoute(Long routeParamId);
}
