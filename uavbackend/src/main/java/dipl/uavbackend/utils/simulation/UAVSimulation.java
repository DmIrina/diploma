package dipl.uavbackend.utils.simulation;

import dipl.uavbackend.controller.RouteController;
import dipl.uavbackend.dto.ModelParamDto;
import dipl.uavbackend.dto.RouteParamDto;
import dipl.uavbackend.entity.EffectiveCountermeasure;
import dipl.uavbackend.entity.Uav;
import dipl.uavbackend.entity.Zone;
import dipl.uavbackend.mapper.RouteParamMapper;
import dipl.uavbackend.repository.CountermeasureRepository;
import dipl.uavbackend.repository.EffectiveCountermeasureRepository;
import dipl.uavbackend.service.EffectiveCountermeasureService;
import dipl.uavbackend.service.ModelParamService;
import dipl.uavbackend.service.RouteService;
import dipl.uavbackend.service.UavService;
import dipl.uavbackend.utils.algorithm.AStarRoute;
import dipl.uavbackend.utils.algorithm.DijkstraRoute;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class UAVSimulation {
    private ModelParamDto modelParam;
    private RouteParamDto routeParam;
    private double timeBetweenGroups;
    private double timeRechargeCM;
    private final int N_TOP_DANGER = 10;
    public static List<Zone> route;
    private Uav uav;
    public static PriorityQueue<Event> eventQueue;
    private double speedInSeconds;
    private double timePerZoneInSeconds;
    private int nUAV;
    private int nUAVInGroup;
    private int nGroups;
    private int nUAVinAction;
    public static int nUAVinResult;
    public static double[] processedRouteStats;
    public static int[] leftUAVSInGroups;
    private static TreeMap<String, Integer> dangerList;
    private static StringBuilder results;
    public static List<EffectiveCountermeasure> listCM;

    @Autowired
    private RouteController routeController;

    @Autowired
    private RouteService routeParamService;

    @Autowired
    private ModelParamService modelParamService;

    @Autowired
    private UavService uavService;

    @Autowired
    private AStarRoute aStarRoute;

    @Autowired
    private DijkstraRoute dijkstraRoute;

    @Autowired
    private EffectiveCountermeasureService effectiveCountermeasureService;

    public void simulate(ModelParamDto modelParam) {
        this.modelParam = modelParam;
        this.routeParam = routeParamService.getLatestRouteParam();
        listCM = effectiveCountermeasureService.getAllEffectiveCountermeasuresList();

        initializeSimulation();
        runSimulation();
        reportResults();
    }

    private void initializeSimulation() {
        eventQueue = new PriorityQueue<>();

        initMapVariables();
        initUAVVariables();
        initUAVGroupsEventList();

        results = new StringBuilder();
        results.append("---------------------------------------- INIT DATA ----------------------------------------\n");
        results.append("Доступна кількість БПЛА: ").append(nUAV).append("\n");
        results.append("Кількість БПЛА у кожній групі: ").append(nUAVInGroup).append("\n");
        results.append("Кількість груп БПЛА: ").append(nGroups).append("\n");
        results.append("Кількість БПЛА, що беруть участь у моделюванні: ").append(nUAVinAction).append("\n");
        results.append("Довжина маршруту: ").append(route.size()).append("\n");
        String formattedSpeed = String.format("%.4f", speedInSeconds);
        results.append("Швидкість групи БПЛА: ").append(formattedSpeed).append("\n");
        String formattedTime = String.format("%.4f", timePerZoneInSeconds);
        results.append("Час прольоту зони однією групою БПЛА: ").append(formattedTime).append("\n");
    }

    private void initMapVariables() {

        List<Zone> savedRoute = routeController.getSavedRouteZones();
        if (savedRoute != null) {
            route = savedRoute;
        } else {
            if (routeParam.getAlgorithmType().equals("astar")) {
                route = aStarRoute.getRoute(RouteParamMapper.mapToRouteParam(routeParam));
            } else {
                route = dijkstraRoute.getRoute(RouteParamMapper.mapToRouteParam(routeParam));
            }
        }

        uav = modelParamService.getUavByUavId(modelParam.getUavId());
        dangerList = new TreeMap<>();
    }

    private void initUAVVariables() {
        nUAV = modelParam.getNuav();
        nUAVInGroup = modelParam.getNuavingroups();
        nUAVinAction = getNUAVInExperiment(nUAV, nUAVInGroup);
        nGroups = getNGroups(nUAV, nUAVInGroup);
        processedRouteStats = new double[nGroups];
        leftUAVSInGroups = new int[nGroups];
        nUAVinResult = 0;
    }

    private int getNUAVInExperiment(int nUAV, int nInGroup) {
        int spare = nUAV % nInGroup;
        return nUAV - spare;
    }

    private int getNGroups(int nUAV, int nInGroup) {
        return nUAV / nInGroup;
    }

    private void initUAVGroupsEventList() {
        speedInSeconds = UAVGroup.recalculateSpeedToMPerS(uav.getSpeed());
        timePerZoneInSeconds = UAVGroup.recalculateTimePerZoneInSeconds(speedInSeconds);
        timeRechargeCM = modelParam.getTimeRecharge();
        timeBetweenGroups = modelParam.getTimeBetweenGroups();

        if (timeBetweenGroups < timePerZoneInSeconds) {
            timeBetweenGroups = timePerZoneInSeconds;
        }

        double startTime = 0.0;
        for (int i = 0; i < nGroups; i++) {
            Zone curZone = route.get(0);
            eventQueue.add(new UAVEvent(startTime, new UAVGroup(i, curZone, uav, nUAVInGroup)));
            startTime += timeBetweenGroups;
        }
    }

    private void runSimulation() {
        results.append("---------------------------------------- SIMULATION ----------------------------------------").append("\n");
        while (!eventQueue.isEmpty()) {
            Event currentEvent = eventQueue.poll();
            currentEvent.process();
        }
    }

    private void reportResults() {
        results.append("---------------------------------------- RESULTS ----------------------------------------").append("\n");
        printProcessedRouteList();
        printDangerList();
        printUAVResult();
    }

    protected static void addToDangerList(String coordinates) {
        if (dangerList.containsKey(coordinates)) {
            dangerList.put(coordinates, dangerList.get(coordinates) + 1);
        } else {
            dangerList.put(coordinates, 1);
        }
    }

    private List<Map.Entry<String, Integer>> sortDangerListByUAVCount() {
        List<Map.Entry<String, Integer>> list = new LinkedList<>(dangerList.entrySet());
        list.sort((o1, o2) -> {
            int compare = o2.getValue().compareTo(o1.getValue());
            return compare != 0 ? compare : o1.getKey().compareTo(o2.getKey());
        });
        return list;
    }

    private void printDangerList() {
        List<Map.Entry<String, Integer>> list = sortDangerListByUAVCount();
        results.append("Координати зон з найбільшим збиттям БПЛА: ").append("\n");
        int count = 0;
        for (Map.Entry<String, Integer> entry : list) {
            if (count >= N_TOP_DANGER) {
                break;
            }
            results.append("\t[").append(entry.getKey()).append("] : ").append(entry.getValue()).append("\n");
            count++;
        }
    }

    private void printProcessedRouteList() {
        results.append("Відсоток проходження маршруту кожною групою БПЛА: ").append("\n");
        for (int i = 0; i < nGroups; i++) {
            results.append("\t група ").append(i).append(": ").append(String.format("%.4f", processedRouteStats[i])).append("%");
            if (processedRouteStats[i] == 100) {
                results.append(", кількість БПЛА: ").append(leftUAVSInGroups[i]);

            }
            results.append("\n");
        }
    }

    private void printUAVResult() {
        results.append("\n");
        results.append("Загальна кількість БПЛА, що досягла цілі: ").append(nUAVinResult).append("\n");
        results.append("Загальна кількість БПЛА, що брала участь у моделюванні: ").append(nUAVinAction).append("\n");
        double rate = (double) nUAVinResult / nUAVinAction * 100;
        results.append("Коефіцієнт досягнення цілі: ").append(String.format("%.4f", rate)).append("%").append("\n");
    }

    protected static void addToResults(String s) {
        results.append(s);
    }

    public String getResults() {
        return results.toString();
    }
}
