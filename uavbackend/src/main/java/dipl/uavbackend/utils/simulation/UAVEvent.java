package dipl.uavbackend.utils.simulation;

import dipl.uavbackend.entity.Zone;
import dipl.uavbackend.entity.ZoneCountermeasure;
import dipl.uavbackend.utils.Constants;

import java.util.Random;

public class UAVEvent extends Event {
    private UAVGroup uavGroup;
    private int currZoneIndex;
    private static Random random = new Random();

    public UAVEvent(double time, UAVGroup uavGroup) {
        super(time);
        this.uavGroup = uavGroup;
        this.currZoneIndex = uavGroup.getCurrZoneIndex();
    }

    @Override
    public void process() {
        int endZoneIndex = (int) (currZoneIndex + uavGroup.getSpeed() * Constants.AVERAGE_TIME_UNIT / Constants.ZONE_SIDE_IN_METERS);
        if (endZoneIndex < UAVSimulation.route.size()) {
            uavGroup.setCurrZone(UAVSimulation.route.get(currZoneIndex));
            for (int i = currZoneIndex; i < endZoneIndex; i++) {
                Zone zone = UAVSimulation.route.get(i);

                //Check if discovered
                double detectionProbability = zone.getDetection() * (1 - (uavGroup.getStealth() / 100));

                if (detectionProbability < 0.1) {
                    detectionProbability = 0.2;
                }
                double tmp = random.nextDouble();
                if (tmp < detectionProbability) {
                    UAVSimulation.addToResults("! UAV group " + uavGroup.getId() + ", uavs: " + uavGroup.getUavCountInGroup() +
                            ", detected in zone " + uavGroup.getCurrZone().getId() + "[" + uavGroup.getCurrZone().getX() +
                            " " + uavGroup.getCurrZone().getY() + " " + uavGroup.getCurrZone().getZ() + "]\n");

                    for (ZoneCountermeasure countermeasure : UAVSimulation.route.get(currZoneIndex).getTerminators()) {


                        int indexEffectiveCM = countermeasure.getIndexInEffectiveCMList();

                        //int nTimes = (getTime() - countermeasure.getLastTimeShelling()) / UAVSimulation.getTimeToRecharge();


                        if (getTime() - countermeasure.getLastTimeShelling() >= UAVSimulation.getTimeToRecharge()){
                            double getTime = getTime();
                            double lastShel = countermeasure.getLastTimeShelling();
                            double timeRecharge = UAVSimulation.getTimeToRecharge();
                            int nTimes = (int) ((getTime - lastShel) / timeRecharge);

                            double newTimeLastChange = lastShel + (timeRecharge * nTimes);
                            countermeasure.setLastTimeShelling(newTimeLastChange);

                            for (int n = 0; n < nTimes; n++) {
                                if (UAVSimulation.listCM.get(indexEffectiveCM).getCountermeasure().getNChannelsLeft() <
                                        UAVSimulation.listCM.get(indexEffectiveCM).getCountermeasure().getNChannels()){
                                    UAVSimulation.listCM.get(indexEffectiveCM).getCountermeasure().rechargeOneChannel();
                                }
                            }
                        }

                        for (int efCM = 0; efCM < UAVSimulation.listCM.get(indexEffectiveCM).getCountermeasure().getNChannelsLeft(); efCM++) {
                            if (uavGroup.getUavCountInGroup() > 0) {
                                double damageProbability = countermeasure.getDamageProbability();
                                double speed = uavGroup.getSpeed();
                                double uavCharacteristics = getUavCharacteristics(uavGroup, countermeasure);

                                if (!countermeasure.getCmType().equals("РЛС")) {
                                    countermeasure.useChannel();
                                    UAVSimulation.listCM.get(indexEffectiveCM).getCountermeasure().useChannel();

                                }

                                double hitProbability = getEffectiveDamageProbability(speed, uavCharacteristics, damageProbability);

                                // UAV is hit
                                if (random.nextDouble() < hitProbability) {
                                    uavGroup.hitUAV();

                                    UAVSimulation.addToDangerList((uavGroup.getCurrZone().getX() + " " +
                                            uavGroup.getCurrZone().getY() + " " + uavGroup.getCurrZone().getZ()));
                                    UAVSimulation.addToResults("- UAV group " + uavGroup.getId() +
                                            " was hit above zone " + uavGroup.getCurrZone().getId() +
                                            " [" + uavGroup.getCurrZone().getX() + " " + uavGroup.getCurrZone().getY() + " "
                                            + uavGroup.getCurrZone().getZ() + "]" + ", uavs: " + uavGroup.getUavCountInGroup() + "\n");

                                    if (uavGroup.getUavCountInGroup() == 0) {
                                        UAVSimulation.addToResults("-------------------------------------------------------------------------\n");
                                        UAVSimulation.addToResults("FAIL: UAV Group " + uavGroup.getId() +
                                                " was eliminated in zone" + uavGroup.getCurrZone().getId() +
                                                " [" + uavGroup.getCurrZone().getX() + " " + uavGroup.getCurrZone().getY() + " "
                                                + uavGroup.getCurrZone().getZ() + "]\n");
                                        double routeProcessed = (double) (uavGroup.getCurrZoneIndex() + 1) / UAVSimulation.route.size() * 100;
                                        UAVSimulation.addToResults("\troute processed: " + String.format("%.4f", routeProcessed) + " %\n");
                                        UAVSimulation.addToResults("-------------------------------------------------------------------------\n");
                                        UAVSimulation.processedRouteStats[uavGroup.getId()] = routeProcessed;
                                        UAVSimulation.leftUAVSInGroups[uavGroup.getId()] = uavGroup.getUavCountInGroup();
                                        continue;
                                    }
                                }

                                System.out.println("group " + uavGroup.getId() + " , uavs " + uavGroup.getUavCountInGroup() +
                                        ", efCM " + UAVSimulation.listCM.get(indexEffectiveCM).getCountermeasure().getCountermeasureName() +
                                        " id " + indexEffectiveCM +
                                        ", channels " + UAVSimulation.listCM.get(indexEffectiveCM).getCountermeasure().getNChannelsLeft());

                            }
                        } // nchannels
                        countermeasure.setLastTimeShelling(getTime());
                    } // terminators

                }

                if (uavGroup.getUavCountInGroup() > 0) {
                    int prevZone = currZoneIndex;
                    currZoneIndex = endZoneIndex;
                    i = currZoneIndex;
                    uavGroup.setCurrZone(UAVSimulation.route.get(currZoneIndex));
                    uavGroup.setCurrZoneIndex(currZoneIndex);
                    double time = getTime() + uavGroup.getGroupTimePerZone();
                    String parsedTime = String.format("%.4f", time);
                    UAVSimulation.addToResults("+ UAV group " + uavGroup.getId() + " with " + uavGroup.getUavCountInGroup() +
                            " uavs succeeded " + "[" + UAVSimulation.route.get(prevZone).getX() + " " +
                            UAVSimulation.route.get(prevZone).getY() + " " + UAVSimulation.route.get(prevZone).getZ() + "]" +
                            " to zone " + uavGroup.getCurrZone().getId() + "[" + uavGroup.getCurrZone().getX() + " " +
                            uavGroup.getCurrZone().getY() + " " + uavGroup.getCurrZone().getZ() + "]" +
                            ", \t\ttime: " + parsedTime + "\n");
                    UAVSimulation.eventQueue.add(new UAVEvent(time, uavGroup));
                }
            }

        } else {
            // UAV has reached target, terminate its mission
            UAVSimulation.addToResults("-------------------------------------------------------------------------\n");
            UAVSimulation.addToResults("SUCCESS: UAV group " + uavGroup.getId() + " reached the target \n");
            UAVSimulation.addToResults("\tuav count: " + uavGroup.getUavCountInGroup() + "\n");
            UAVSimulation.addToResults("-------------------------------------------------------------------------\n");
            UAVSimulation.nUAVinResult += uavGroup.getUavCountInGroup();
            UAVSimulation.leftUAVSInGroups[uavGroup.getId()] = uavGroup.getUavCountInGroup();
            double routeProcessed = (double) (uavGroup.getCurrZoneIndex() + 1) / UAVSimulation.route.size() * 100;
            UAVSimulation.processedRouteStats[uavGroup.getId()] = routeProcessed;
        }
    }

    private double getUavCharacteristics(UAVGroup uavGroup, ZoneCountermeasure countermeasure) {
        double uavCharacteristics;
        if (countermeasure.getCmType().equals("ППО")) {
            uavCharacteristics = uavGroup.getResistance();
        } else if (countermeasure.getCmType().equals("РЕБ")) {
            uavCharacteristics = uavGroup.getNoiseImmunity();
        } else {
            uavCharacteristics = uavGroup.getStealth();
        }
        return uavCharacteristics;
    }

    private double getEffectiveDamageProbability(double speed, double uavCharacteristics, double damageProbability) {
        double effectiveDamageProbability = damageProbability * (1 - (uavCharacteristics / 100));

        double speedThreshold1 = Constants.ZONE_SIDE_IN_METERS / Constants.AVERAGE_TIME_UNIT;
        double speedThreshold2 = 2 * speedThreshold1;
        double speedThreshold3 = 3 * speedThreshold1;

        if (speed > speedThreshold3) {
            effectiveDamageProbability *= 0.7;
        } else if (speed > speedThreshold2) {
            effectiveDamageProbability *= 0.8;
        } else if (speed > speedThreshold1) {
            effectiveDamageProbability *= 0.9;
        } else {
            effectiveDamageProbability *= 1.1;
        }

        return effectiveDamageProbability;
    }

    private void recalculateChannels(double timeForRecharge, double currTime) {

    }

}