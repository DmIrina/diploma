package dipl.uavbackend.service;

import dipl.uavbackend.entity.EffectiveCountermeasure;
import dipl.uavbackend.entity.RouteParam;
import dipl.uavbackend.entity.Zone;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ZoneService {

    Zone getZone(int id, int z, int y, int x, List<EffectiveCountermeasure> effectiveCountermeasures, RouteParam params);

    @Cacheable("getZonesCache")
    Zone[][][] getZones(List<EffectiveCountermeasure> effectiveCountermeasures, RouteParam params);
}
