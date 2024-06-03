package dipl.uavbackend.controller;

import dipl.uavbackend.dto.ModelParamDto;
import dipl.uavbackend.service.ModelParamService;
import dipl.uavbackend.utils.simulation.UAVSimulation;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/simulation")
@AllArgsConstructor
public class SimulationController {
//    @Autowired
//    private RouteService routeParamService;
//    @Autowired
//    private ZoneService zoneService;
//    @Autowired
//    private EffectiveCountermeasureRepository effectiveCountermeasureRepository;
//    @Autowired
//    private DijkstraRoute dijkstraRoute;
//    private AStarRoute aStarRoute;

    @Autowired
    private UAVSimulation militaryUAVSimulation;


    @Autowired
    private ModelParamService modelParamService;

    @GetMapping("/pages/{id}")
    public String getPages(@PathVariable("id") Long paramId) {
        ModelParamDto modelParam = modelParamService.getModelParamById(paramId);
        militaryUAVSimulation.simulate(modelParam);
        return militaryUAVSimulation.getResults();
    }
}