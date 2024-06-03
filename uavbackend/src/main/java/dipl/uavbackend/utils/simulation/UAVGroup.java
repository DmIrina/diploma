package dipl.uavbackend.utils.simulation;

import dipl.uavbackend.entity.Uav;
import dipl.uavbackend.entity.Zone;
import dipl.uavbackend.utils.Constants;

public class UAVGroup {
    private int id;
    private Zone currZone;
    private int currZoneIndex;
    private Uav uav;
    private double speed;
    private int uavCountInGroup;
    private double timePerZone;

    public UAVGroup(int id, Zone currZone, Uav uav, int uavCountInGroup) {
        this.id = id;
        this.currZoneIndex = 0;
        this.currZone = currZone;
        this.uav = uav;
        this.speed = recalculateSpeedToMPerS(uav.getSpeed());
        this.timePerZone = recalculateTimePerZoneInSeconds(speed);
        this.uavCountInGroup = uavCountInGroup;
    }

    public int getId() {
        return id;
    }

    public Zone getCurrZone() {
        return this.currZone;
    }

    public void setCurrZone(Zone zone) {
        this.currZone = zone;
    }

    public int getCurrZoneIndex() {
        return currZoneIndex;
    }

    public void setCurrZoneIndex(int currZoneIndex) {
        this.currZoneIndex = currZoneIndex;
    }

    public static double recalculateSpeedToMPerS(int speedKmPerHour) {
        return speedKmPerHour * (5.0 / 18.0);
    }

    public static double recalculateTimePerZoneInSeconds(double speedInSeconds) {
        return Constants.ZONE_SIDE_IN_METERS / speedInSeconds;
    }

    public double getSpeed() {
        return speed;
    }

    public double getResistance() {
        return this.uav.getResistance();
    }

    public double getStealth() {
        return this.uav.getStealth();
    }

    public double getNoiseImmunity() {
        return this.uav.getNoiseimmunity();
    }

    public int getUavCountInGroup() {
        return uavCountInGroup;
    }

    public void hitUAV() {
        this.uavCountInGroup--;
    }

    public double getGroupTimePerZone() {
        return this.timePerZone;
    }
}
