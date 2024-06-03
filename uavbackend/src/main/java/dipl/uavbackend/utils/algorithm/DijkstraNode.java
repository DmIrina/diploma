package dipl.uavbackend.utils.algorithm;

import java.util.ArrayList;
import java.util.List;

public class DijkstraNode {

    int id;
    public int z;
    public int y;
    public int x;
    public double overflightProbability; // вірогідн прольоту від початку маршруту
    public double damageProbability; // вірогідн збиття тільки для цієї ноди

    public List<DijkstraNode> neighbors; // Сусідні вершини

    public DijkstraNode predecessor; // Попередник вершини

    public DijkstraNode(int id, int z, int y, int x, double damageProbability) {
        this.id = id;
        this.z = z;
        this.y = y;
        this.x = x;
        this.overflightProbability = 0;    // початкові вірогідності прольоту  для всіх нод
        this.damageProbability = damageProbability;
        this.predecessor = null;                // попередник
        this.neighbors = new ArrayList<>();
    }
}
