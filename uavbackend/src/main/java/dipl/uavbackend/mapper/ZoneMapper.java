package dipl.uavbackend.mapper;
import dipl.uavbackend.dto.ZoneDto;
import dipl.uavbackend.entity.Zone;

public class ZoneMapper {

    public static ZoneDto mapZoneToDto(Zone zone) {
        ZoneDto zoneDto = new ZoneDto();
        zoneDto.setX(zone.getX());
        zoneDto.setY(zone.getY());
        zoneDto.setZ(zone.getZ());
        return zoneDto;
    }
}
