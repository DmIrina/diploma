package dipl.uavbackend.controller;

import dipl.uavbackend.dto.RouteParamDto;
import dipl.uavbackend.dto.ZoneDto;
import dipl.uavbackend.entity.RouteParam;
import dipl.uavbackend.entity.Zone;
import dipl.uavbackend.mapper.ZoneMapper;
import dipl.uavbackend.service.RouteService;
import dipl.uavbackend.utils.algorithm.AStarRoute;
import dipl.uavbackend.utils.algorithm.DijkstraRoute;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.util.List;
import java.util.stream.Collectors;


@CrossOrigin(origins = "*") // Дозволяє запити з будь-якого джерела
@RestController
@RequestMapping("/api/route")
@AllArgsConstructor
public class RouteController {

    @Autowired
    private RouteService routeService;

    @Autowired
    private DijkstraRoute dijkstraRoute;
    private AStarRoute aStarRoute;

    @PostMapping
    public ResponseEntity<RouteParamDto> createRouteParam(@RequestBody RouteParamDto routeParamDto) {
        RouteParamDto newRouteParam = routeService.createRouteParam(routeParamDto);
        return new ResponseEntity<>(newRouteParam, HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    public ResponseEntity<RouteParamDto> getRouteParamById(@PathVariable("id") Long routeParamId) {
        RouteParamDto routeParamDto = routeService.getRouteParamById(routeParamId);
        return new ResponseEntity<>(routeParamDto, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<RouteParamDto>> getAllRouteParams() {
        List<RouteParamDto> routeParamDtoList = routeService.getAllRouteParams();
        return new ResponseEntity<>(routeParamDtoList, HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity<RouteParamDto> updateRouteParam(@PathVariable("id") Long uavId,
                                                          @RequestBody RouteParamDto routeParamDto) {
        RouteParamDto updatedRouteParam = routeService.updateRouteParam(uavId, routeParamDto);
        return new ResponseEntity<>(updatedRouteParam, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteRouteParam(@PathVariable("id") Long routeParamId) {
        routeService.deleteRouteParam(routeParamId);
        return new ResponseEntity<>("Delete RouteParam Successfully", HttpStatus.OK);
    }

    @PutMapping("/calculate/{id}")
    public ResponseEntity<String> calculateRoute(@PathVariable("id") Long routeParamId, HttpServletRequest request) {
        List<Zone> route;
        RouteParam params = routeService.getParams(routeParamId);

        System.out.printf("-- %d -- %s ", routeParamId, params.getRouteParamName());

        if (params.getAlgorithmType().equals("astar")) {
            route = aStarRoute.getRoute(params);
        } else {
            route = dijkstraRoute.getRoute(params);
        }

        try {
            FileOutputStream fileOut = new FileOutputStream("savedRoute.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(route);
            out.close();
            fileOut.close();
            System.out.println("Serialized data is saved in savedRoute.ser");
        } catch (IOException e) {
            e.printStackTrace();
        }

        routeService.calculateRoute(routeParamId);
        return new ResponseEntity<>("Calculate Route Successfully", HttpStatus.OK);
    }

    // Метод для отримання збереженого маршруту
    @GetMapping("/saved")
    public ResponseEntity<List<ZoneDto>> getSavedRoute() {
        try {
            FileInputStream fileIn = new FileInputStream("savedRoute.ser");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            List<Zone> savedRoute = (List<Zone>) in.readObject();
            in.close();
            fileIn.close();

            if (savedRoute != null) {
                List<ZoneDto> routeDto = savedRoute.stream().map(ZoneMapper::mapZoneToDto).collect(Collectors.toList());
                System.out.println("Маршрут відновлено");
                return ResponseEntity.ok(routeDto);
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        System.out.println("Файл не знайдено або відбулась помилка при читанні");
        return ResponseEntity.noContent().build();
    }

    public List<Zone> getSavedRouteZones() {
        List<Zone> savedRoute = null;
        try {
            FileInputStream fileIn = new FileInputStream("savedRoute.ser");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            savedRoute = (List<Zone>) in.readObject();
            in.close();
            fileIn.close();

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return savedRoute;
    }


    // Метод для отримання параметрів розрахованого маршруту
    @GetMapping("/lastparam")
    public ResponseEntity<RouteParamDto> getLastParam() {
        RouteParamDto routeParamDto = routeService.getLatestRouteParam();
        return new ResponseEntity<>(routeParamDto, HttpStatus.OK);
    }
}
