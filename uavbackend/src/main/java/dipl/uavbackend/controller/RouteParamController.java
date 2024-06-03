package dipl.uavbackend.controller;

import dipl.uavbackend.dto.RouteParamDto;
import dipl.uavbackend.service.RouteService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@CrossOrigin(origins = "*") // Дозволяє запити з будь-якого джерела
@RestController
@RequestMapping("/api/routeparams")
@AllArgsConstructor
public class RouteParamController {

    @Autowired
    private RouteService routeParamService;

    @PostMapping
    public ResponseEntity<RouteParamDto> createRouteParam(@RequestBody RouteParamDto routeParamDto) {
        RouteParamDto newRouteParam = routeParamService.createRouteParam(routeParamDto);
        return new ResponseEntity<>(newRouteParam, HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    public ResponseEntity<RouteParamDto> getRouteParamById(@PathVariable("id") Long routeParamId) {
        RouteParamDto routeParamDto = routeParamService.getRouteParamById(routeParamId);
        return new ResponseEntity<>(routeParamDto, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<RouteParamDto>> getAllRouteParams() {
        List<RouteParamDto> routeParamDtoList = routeParamService.getAllRouteParams();
        return new ResponseEntity<>(routeParamDtoList, HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity<RouteParamDto> updateRouteParam(@PathVariable("id") Long uavId,
                                                          @RequestBody RouteParamDto routeParamDto) {
        RouteParamDto updatedRouteParam = routeParamService.updateRouteParam(uavId, routeParamDto);
        return new ResponseEntity<>(updatedRouteParam, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteRouteParam(@PathVariable("id") Long routeParamId) {
        routeParamService.deleteRouteParam(routeParamId);
        return new ResponseEntity<>("Delete RouteParam Successfully", HttpStatus.OK);
    }
}
