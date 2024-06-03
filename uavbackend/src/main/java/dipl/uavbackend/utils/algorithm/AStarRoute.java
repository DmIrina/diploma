package dipl.uavbackend.utils.algorithm;

import dipl.uavbackend.dto.RouteParamDto;
import dipl.uavbackend.entity.RouteParam;
import dipl.uavbackend.entity.Zone;
import dipl.uavbackend.repository.EffectiveCountermeasureRepository;
import dipl.uavbackend.service.ZoneService;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Component
public class AStarRoute {

    private ZoneService zoneService;
    private EffectiveCountermeasureRepository effectiveCountermeasureRepository;

    public AStarRoute(ZoneService zoneService, EffectiveCountermeasureRepository effectiveCountermeasureRepository) {
        this.zoneService = zoneService;
        this.effectiveCountermeasureRepository = effectiveCountermeasureRepository;
    }

    public List<Zone> getRoute(RouteParam params) {
        long startTime = System.currentTimeMillis();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        System.out.println("Початок виконання:    " + LocalDateTime.now().format(formatter));

        int[] layers = params.getLayersArray();
        int dimX = params.getDimX();
        int dimY = params.getDimY();
        int dimZ = layers.length - 1;


        Zone[][][] zones = zoneService.getZones(effectiveCountermeasureRepository.findAll(), params);

        // Масив для зберігання вірогідностей уражень та попередників
        AStarNode[][][] aStarNodeArray = new AStarNode[dimZ][dimY][dimX];
        // Черга з пріоритетом, сортування за зростанням
        // openList - черга відомих вузлів - до якої додаємо вузли-сусіди поточного вузла і видаляемо поточний як обстежений
        // порівняння по Full
        PriorityQueue<AStarNode> openList = new PriorityQueue<>(Comparator.comparingDouble(AStarNode::getDangerWithHeuristic));

        // повністю досліджені вершини (ClosedList): до цих вершин вже відомий найкоротший шлях
        Set<AStarNode> closedSet = new HashSet<>();

        for (int i = 0; i < dimZ; i++) {
            for (int j = 0; j < dimY; j++) {
                for (int k = 0; k < dimX; k++) {

                    AStarNode node = new AStarNode(i, j, k, zones[i][j][k].getDamageProbability());
                    node.setH(calculateHValue(node, params.getTargetX(), params.getTargetY(), params.getTargetZ())); // порахувати відстань, км
                    aStarNodeArray[i][j][k] = node;

                }
            }
        }

        AStarNode startNode = aStarNodeArray[params.getSourceZ()][params.getSourceY()][params.getSourceX()];
        AStarNode targetNode = aStarNodeArray[params.getTargetZ()][params.getTargetY()][params.getTargetX()];

        startNode.setDangerFromStart(0);
        startNode.setH(calculateHValue(startNode, params.getTargetX(), params.getTargetY(), params.getTargetZ())); // порахувати відстань, км

        openList.offer(startNode);

        while (!openList.isEmpty()) {
            AStarNode currentNode = openList.poll();        // достали ноду з мінімальним ураженням з урах еврістики
            closedSet.add(currentNode);


            if (currentNode.equals(targetNode)) {
                System.out.println("Маршрут знайдено - відновлюю");
                List<Zone> route = reconstructPath(currentNode, zones);
                long endTime = System.currentTimeMillis();
                long executionTime = endTime - startTime;
                System.out.println("Завершення виконання: " + LocalDateTime.now().format(formatter));

                System.out.println("Час розрахунку маршруту: " + executionTime / 1000 + " секунд");
                System.out.println("X Y Z");
                System.out.printf("X %d, Y %d, Шарів: %d", dimX, dimY, dimZ);
                return route;
            }

            List<AStarNode> neighbors = getNeighbors(currentNode, aStarNodeArray, params);

            for (AStarNode neighbor : neighbors) {
                if (closedSet.contains(neighbor)) {
                    continue;
                }

                // double tentativeGScore = currentNode.getG() + neighbor.getDamageProbability();
                double safe_dfs = 1 - currentNode.getDangerFromStart();
                double safe_dwh = 1 - neighbor.getDangerWithHeuristic();
                double sf = safe_dfs * safe_dwh;
                // double tentativeDamageScore = 1 - (1-currentNode.getDangerFromStart())*(1-neighbor.getDangerWithHeuristic()); //попередній

                double tentativeDamageScore = 1 - sf;
                if (!openList.contains(neighbor) || tentativeDamageScore < neighbor.getDangerFromStart()) {
                    neighbor.setParent(currentNode);
                    neighbor.setDangerFromStart(tentativeDamageScore);
                    neighbor.setH(calculateHValue(neighbor, params.getTargetX(), params.getTargetY(), params.getTargetZ()));
                    if (!openList.contains(neighbor)) {
                        openList.offer(neighbor);
                    }
                }
            }
        }

        System.out.println("Не вдалося знайти маршрут");
        return null;
    }

    // еврістична функція - найкоротший шлях з мін небезпекою NB_MIN
    private double calculateHValue(AStarNode node, int targetX, int targetY, int targetZ) {
        return Math.sqrt(Math.pow(targetX - node.getX(), 2) +
                Math.pow(targetY - node.getY(), 2) +
                Math.pow(targetZ - node.getZ(), 2));
    }

    private List<AStarNode> getNeighbors(AStarNode node, AStarNode[][][] grid, RouteParam params) {
        List<AStarNode> neighbors = new ArrayList<>();
        int x = node.getX();
        int y = node.getY();
        int z = node.getZ();

        if (x > 0) neighbors.add(grid[z][y][x - 1]); // West
        if (x < params.getDimX() - 1) neighbors.add(grid[z][y][x + 1]); // East
        if (y > 0) neighbors.add(grid[z][y - 1][x]); // North
        if (y < params.getDimY() - 1) neighbors.add(grid[z][y + 1][x]); // South
        if (z > 0) neighbors.add(grid[z - 1][y][x]); // Down
        if (z < params.getLayersArray().length - 1) neighbors.add(grid[z][y][x]); // Up

//        if (i > 0) dijkstraNode.neighbors.add(dijkstraNodeArray[i - 1][j][k]); // Вершина ліворуч
//        if (i < z - 1) dijkstraNode.neighbors.add(dijkstraNodeArray[i + 1][j][k]); // Вершина праворуч


        return neighbors;
    }


    private List<Zone> reconstructPath(AStarNode currentNode, Zone[][][] zones) {
        List<Zone> path = new ArrayList<>();
        AStarNode node = currentNode;

        while (node != null) {
            path.add(zones[node.getZ()][node.getY()][node.getX()]);
            node = node.getParent();
        }

        Collections.reverse(path);
        return path;
    }


    // Other helper methods if needed
}

