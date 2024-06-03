package dipl.uavbackend.utils.algorithm;

import dipl.uavbackend.entity.RouteParam;
import dipl.uavbackend.entity.Zone;
import dipl.uavbackend.repository.EffectiveCountermeasureRepository;
import dipl.uavbackend.service.ZoneService;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Component
public class DijkstraRoute {
    private ZoneService zoneService;
    private EffectiveCountermeasureRepository effectiveCountermeasureRepository;

    public DijkstraRoute(ZoneService zoneService, EffectiveCountermeasureRepository effectiveCountermeasureRepository) {
        this.zoneService = zoneService;
        this.effectiveCountermeasureRepository = effectiveCountermeasureRepository;
    }

    public List<Zone> getRoute(RouteParam params) {
        int dimX = params.getDimX();
        int dimY = params.getDimY();
        int dimZ = params.getLayersArray().length - 1;

        System.out.printf("dimX: %d, dimY: %d, dimZ:%d%n", dimX, dimY, dimZ);
        System.out.printf("Source: %d, %d, %d%n", params.getSourceX(), params.getSourceY(), params.getSourceZ());
        System.out.printf("Target: %d, %d, %d%n", params.getTargetX(), params.getTargetY(), params.getTargetZ());

        long startTime = System.currentTimeMillis();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        System.out.println("Початок виконання:    " + LocalDateTime.now().format(formatter));

        Zone[][][] zones = zoneService.getZones(effectiveCountermeasureRepository.findAll(), params);

        // Масив для зберігання вірогідностей уражень та попередників
        DijkstraNode[][][] dijkstraNodeArray = new DijkstraNode[dimZ][dimY][dimX];

        // черга з пріоритетом, сортування за убуванням
        PriorityQueue<DijkstraNode> dijkstraNodeQueue = new PriorityQueue<>(Comparator.comparingDouble(node -> -node.overflightProbability));

        System.out.println("встановити DamageProbability");

        int id = 0;
        for (int i = 0; i < dimZ; i++) {
            for (int j = 0; j < dimY; j++) {
                for (int k = 0; k < dimX; k++) {
                    // граф відстаней
                    dijkstraNodeArray[i][j][k] = new DijkstraNode(id, i, j, k, zones[i][j][k].getDamageProbability());
                    // Додати кожну ноду в пріоритетну чергу з сортуванням за зменшенням вірогідності прольоту від початку маршруту
                    dijkstraNodeQueue.offer(dijkstraNodeArray[i][j][k]);
                }
            }
        }
        dijkstraNodeArray[params.getSourceZ()][params.getSourceY()][params.getSourceX()].overflightProbability = 1;

        // Встановлення посилань на сусідні вершини
        System.out.println("встановлення посилань на сусідні вершини");
        for (int i = 0; i < dimZ; i++) {
            for (int j = 0; j < dimY; j++) {
                for (int k = 0; k < dimX; k++) {
                    DijkstraNode dijkstraNode = dijkstraNodeArray[i][j][k];
                    if (i > 0) dijkstraNode.neighbors.add(dijkstraNodeArray[i - 1][j][k]); // Вершина ліворуч
                    if (i < dimZ - 1) dijkstraNode.neighbors.add(dijkstraNodeArray[i + 1][j][k]); // Вершина праворуч
                    if (j > 0) dijkstraNode.neighbors.add(dijkstraNodeArray[i][j - 1][k]); // Вершина знизу
                    if (j < dimY - 1) dijkstraNode.neighbors.add(dijkstraNodeArray[i][j + 1][k]); // Вершина зверху
                    if (k > 0) dijkstraNode.neighbors.add(dijkstraNodeArray[i][j][k - 1]); // Вершина позаду
                    if (k < dimX - 1) dijkstraNode.neighbors.add(dijkstraNodeArray[i][j][k + 1]); // Вершина спереду
                }
            }
        }

        // Виконання алгоритму Дейкстри
        System.out.println("Виконання алгоритму Дейкстри");
        while (!dijkstraNodeQueue.isEmpty()) {
            // Вибір вершини з найменшою вірогідністю ураження (починаємо з SOURCE, бо там 0)
            DijkstraNode distNode = dijkstraNodeQueue.poll();

            // Перебір сусідніх вершин
            for (DijkstraNode neighbor : distNode.neighbors) {
                int nZ = neighbor.z;
                int nY = neighbor.y;
                int nX = neighbor.x;
                var nodeNeighbor = dijkstraNodeArray[nZ][nY][nX];

                // Перевірка на валідність та оновлення вірогідністей прольоту
                double nodeOverflightProbability = 1 - neighbor.damageProbability;  // вірогідність прольоту

                // вузол ще не обходили (distNeighbor.dist = 1) або знайдено кращий маршрут до вузла
                if (nodeNeighbor.overflightProbability < distNode.overflightProbability * nodeOverflightProbability) {
                    dijkstraNodeArray[nZ][nY][nX].overflightProbability = distNode.overflightProbability * nodeOverflightProbability; // зберегти показхник кращого маршруту
                    dijkstraNodeQueue.remove(nodeNeighbor); // Видалення з черги для оновлення пріоритету
                    nodeNeighbor.predecessor = distNode; // Оновлення попередника з кращим маршрутом
                    dijkstraNodeQueue.offer(nodeNeighbor); // Додавання вузла з оновленим пріоритетом
                }
            }
        }

        List<Zone> route = restoreRoute(dijkstraNodeArray, zones, params.getTargetZ(), params.getTargetY(), params.getTargetX());

        long endTime = System.currentTimeMillis();
        long executionTime = endTime - startTime;
        System.out.println("Завершення виконання: " + LocalDateTime.now().format(formatter));
        System.out.println("Час розрахунку маршруту: " + executionTime / 1000 + " секунд");
        return route;
    }

    public List<Zone> restoreRoute(DijkstraNode[][][] distGraph, Zone[][][] zones, int targetZ, int targetY, int targetX) {
        List<Zone> rout = new ArrayList<>();
        DijkstraNode targetNode = distGraph[targetZ][targetY][targetX]; // Кінцева вершина

        // Ітеративно додаємо вершини у маршрут, починаючи з кінцевої і йдучи до початкової
        while (targetNode != null) {
            Zone zone = zones[targetNode.z][targetNode.y][targetNode.x]; // Відповідна вершина типу Node
            rout.add(zone); // Додаємо вершину у маршрут
            targetNode = targetNode.predecessor; // Переходимо до попередника
        }

        Collections.reverse(rout); // Обертаємо маршрут, щоб він був від початку до кінця

        return rout;
    }
}
