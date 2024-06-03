package dipl.uavbackend.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
public class ZoneCountermeasure implements Serializable {
    private int indexInEffectiveCMList;
    private int nChannels;
    private double damageProbability;
    private double credibility;
    private String cmType;

    @Setter(AccessLevel.NONE)
    private int nChannelsLeft;

    @Setter(AccessLevel.NONE)
    private int uavCountEliminated;

    private double lastTimeShelling;

    public void useChannel() {
        nChannelsLeft--;
    }

}
