package dipl.uavbackend.utils.algorithm;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static dipl.uavbackend.utils.Constants.NB_MIN;

public class AStarNode {

    private int id;
    public int z;
    public int y;
    public int x;
    public double overflightProbability; // вірогідн прольоту від початку маршруту
    public double damageProbability; // вірогідн збиття тільки для цієї ноди
    private double dangerFromStart; // небезпека від початку маршруту
    private double h; // Геурістика

    public List<AStarNode> neighbors; // Сусідні вершини

    public AStarNode parent; // Попередник вершини

    private static int nextId = 0;

    public AStarNode(int z, int y, int x, double damageProbability) {
        this.id = nextId++;
        this.z = z;
        this.y = y;
        this.x = x;
        this.overflightProbability = 0;
        this.damageProbability = damageProbability;
        this.parent = null;
        this.neighbors = new ArrayList<>();
        this.dangerFromStart = Double.MAX_VALUE;
        this.h = 0;
    }

    // damageProbability з урах еврістики
    public double getDangerWithHeuristic() {
        //return g + h;
        double safeHeuristic = 1;
        for (int i = 0; i < h; i++) {
            safeHeuristic = safeHeuristic * (1 - NB_MIN);
        }
        return 1 - (1-damageProbability)*safeHeuristic;
    }

    public void setDangerFromStart(double dangerFromStart) {
        this.dangerFromStart = dangerFromStart;
    }

    public void setH(double h) {
        this.h = h;
    }

    public AStarNode getParent() {
        return parent;
    }

    public void setParent(AStarNode parent) {
        this.parent = parent;
    }

    public double getDangerFromStart() {
        return dangerFromStart;
    }

    public double getH() {
        return h;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getZ() {
        return z;
    }

    public double getDamageProbability() {
        return damageProbability;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AStarNode aStarNode = (AStarNode) o;
        return z == aStarNode.z &&
                y == aStarNode.y &&
                x == aStarNode.x;
    }

    @Override
    public int hashCode() {
        return Objects.hash(z, y, x);
    }


}