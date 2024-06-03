package dipl.uavbackend.service.implementation;

import dipl.uavbackend.dto.RouteParamDto;
import dipl.uavbackend.entity.RouteParam;
import dipl.uavbackend.exception.ResourceNotFoundException;
import dipl.uavbackend.mapper.RouteParamMapper;
import dipl.uavbackend.repository.RouteParamRepository;
import dipl.uavbackend.service.RouteService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class RouteServiceImplementation implements RouteService {

    @Autowired
    private RouteParamRepository routeParamRepository;

    @Override
    public RouteParamDto createRouteParam(RouteParamDto routeParamDto) {
        RouteParam routeParam = RouteParamMapper.mapToRouteParam(routeParamDto);
        RouteParam savedRouteParam = routeParamRepository.save(routeParam);
        return RouteParamMapper.mapToRouteParamDto(savedRouteParam);
    }

    @Override
    public RouteParamDto getRouteParamById(Long routeParamId) {
        RouteParam routeParam = routeParamRepository.findById(routeParamId)
                .orElseThrow(() -> new ResourceNotFoundException("Не знайдено параметри маршруту: " + routeParamId));
        return RouteParamMapper.mapToRouteParamDto(routeParam);
    }

    @Override
    public RouteParamDto getLatestRouteParam() {
        RouteParam routeParam = routeParamRepository.findFirstByOrderByTimestampDesc();
        return RouteParamMapper.mapToRouteParamDto(routeParam);
    }

    @Override
    public RouteParam getParams(Long routeParamId) {
        RouteParam routeParam = routeParamRepository.findById(routeParamId)
                .orElseThrow(() -> new ResourceNotFoundException("Не знайдено параметри маршруту: " + routeParamId));
//        String listOfLayers = routeParam.getLayers();
//        Matcher matcher = Pattern.compile("\\d+").matcher(listOfLayers);
//        List<Integer> numbers = new ArrayList<>();
//        while (matcher.find()) {
//            numbers.add(Integer.valueOf(matcher.group()));
//        }
//        routeParam.setLayersArray(numbers.stream().mapToInt(i -> i).toArray());
        String listOfLayers = routeParam.getLayers();
        int[] layersArr = layersStringToLayersArr(listOfLayers);
        routeParam.setLayersArray(layersArr);
        return routeParam;
    }

    @Override
    public List<RouteParamDto> getAllRouteParams() {
        return routeParamRepository.findAll()
                .stream().map(RouteParamMapper::mapToRouteParamDto)
                .collect(Collectors.toList());
    }

    @Override
    public RouteParamDto updateRouteParam(Long routeParamId, RouteParamDto routeParamDto) {
        RouteParam routeParam = routeParamRepository.findById(routeParamId)
                .orElseThrow(() -> new ResourceNotFoundException("Не знайдено параметри маршруту:" + routeParamId));
        routeParam.setDimX(routeParamDto.getDimX());
        routeParam.setDimY(routeParamDto.getDimY());
        routeParam.setLayers(routeParamDto.getLayers());
        routeParam.setTargetX(routeParamDto.getTargetX());
        routeParam.setTargetY(routeParamDto.getTargetY());
        routeParam.setTargetZ(routeParamDto.getTargetZ());
        routeParam.setSourceX(routeParamDto.getSourceX());
        routeParam.setSourceY(routeParamDto.getSourceY());
        routeParam.setSourceZ(routeParamDto.getSourceZ());
        routeParam.setAlgorithmType(routeParamDto.getAlgorithmType());
        routeParam.setRouteParamName(routeParamDto.getRouteParamName());
        RouteParam updatedRouteParam = routeParamRepository.save(routeParam);
        return RouteParamMapper.mapToRouteParamDto(updatedRouteParam);
    }

    @Override
    public void deleteRouteParam(Long routeParamId) {
        RouteParam routeParam = routeParamRepository.findById(routeParamId)
                .orElseThrow(() -> new ResourceNotFoundException("Не знайдено параметри маршруту: " + routeParamId));
        routeParamRepository.deleteById(routeParamId);
    }

    @Override
    public void calculateRoute(Long routeParamId) {
        RouteParam routeParam = routeParamRepository.findById(routeParamId)
                .orElseThrow(() -> new ResourceNotFoundException("Не знайдено параметри маршруту:" + routeParamId));
        routeParam.setTimestamp(LocalDateTime.now());
        RouteParam updatedRouteParam = routeParamRepository.save(routeParam);
    }

    public static int[] layersStringToLayersArr(String listOfLayers) {
        Matcher matcher = Pattern.compile("\\d+").matcher(listOfLayers);
        List<Integer> numbers = new ArrayList<>();
        while (matcher.find()) {
            numbers.add(Integer.valueOf(matcher.group()));
        }
        return numbers.stream().mapToInt(i -> i).toArray();
    }
}
