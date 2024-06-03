package dipl.uavbackend.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
public class Zone implements Serializable {
    int id;
    int z, y, x;
    double destruction;
    double jamming;
    double detection;
    int channelsCount;

    @Setter(AccessLevel.NONE)
    List<ZoneCountermeasure> terminators = new ArrayList<>();

    @Setter(AccessLevel.NONE)
    List<ZoneCountermeasure> detectors = new ArrayList<>();

    double destructionCredibility;
    double jammingCredibility;
    double detectionCredibility;
    double damageProbability;

    public Zone(int id, int z, int y, int x) {
        this.id = id;
        this.z = z;
        this.y = y;
        this.x = x;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Zone zone = (Zone) o;
        return id == zone.id &&
                z == zone.z &&
                y == zone.y &&
                x == zone.x &&
                Double.compare(zone.destruction, destruction) == 0 &&
                Double.compare(zone.jamming, jamming) == 0 &&
                Double.compare(zone.detection, detection) == 0 &&
                channelsCount == zone.channelsCount &&
                Double.compare(zone.destructionCredibility, destructionCredibility) == 0 &&
                Double.compare(zone.jammingCredibility, jammingCredibility) == 0 &&
                Double.compare(zone.detectionCredibility, detectionCredibility) == 0 &&
                Double.compare(zone.damageProbability, damageProbability) == 0 &&
                Objects.equals(terminators, zone.terminators) &&
                Objects.equals(detectors, zone.detectors);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, z, y, x, destruction, jamming, detection, channelsCount,
                terminators, detectors, destructionCredibility, jammingCredibility, detectionCredibility, damageProbability);
    }
}