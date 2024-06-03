package dipl.uavbackend.mapper;

import dipl.uavbackend.dto.CountermeasureDto;
import dipl.uavbackend.entity.Countermeasure;

public class CountermeasureMapper {
    public static CountermeasureDto mapToCountermeasureDto(Countermeasure countermeasure) {
        return new CountermeasureDto(
                countermeasure.getId(),
                countermeasure.getCountermeasureName(),
                countermeasure.getCmType(),
                countermeasure.getHMin(),
                countermeasure.getHMax(),
                countermeasure.getDistance(),
                countermeasure.getNChannels(),
                countermeasure.getDamageProbability()
        );
    }

    public static Countermeasure mapToCountermeasure(CountermeasureDto countermeasureDto) {
        return new Countermeasure(
                countermeasureDto.getId(),
                countermeasureDto.getCountermeasureName(),
                countermeasureDto.getCmType(),
                countermeasureDto.getHMin(),
                countermeasureDto.getHMax(),
                countermeasureDto.getDistance(),
                countermeasureDto.getNChannels(),
                countermeasureDto.getDamageProbability(),
                countermeasureDto.getNChannels()
        );
    }
}
