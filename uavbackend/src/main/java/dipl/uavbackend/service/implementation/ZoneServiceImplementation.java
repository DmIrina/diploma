package dipl.uavbackend.service.implementation;

import dipl.uavbackend.entity.EffectiveCountermeasure;
import dipl.uavbackend.entity.RouteParam;
import dipl.uavbackend.entity.Zone;
import dipl.uavbackend.entity.ZoneCountermeasure;
import dipl.uavbackend.service.RouteService;
import dipl.uavbackend.service.ZoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

import static dipl.uavbackend.utils.Constants.NB_MIN;

@Service
public class ZoneServiceImplementation implements ZoneService {

    // в кожну зону (ноду) додаємо показники РЕБ, ППО (стрілецьке та ЗРК), РЛС (РЛС та пеленгатори)
    // показники додаємо як сумарну вірогідність успішної дії всіх засобів (окремо по РЕБ,ППО,РЛС), що
    // діють у зоні на відповідному шарі по висоті

    @Autowired
    RouteService routeParamService;

    @Cacheable("getZoneCache")
    @Override
    public Zone getZone(int id, int z, int y, int x, List<EffectiveCountermeasure> effectiveCountermeasures, RouteParam params) {

        int[] layers = params.getLayersArray();
        Zone zone = new Zone(id, z, y, x);

        int cmCount = 0;
        for (EffectiveCountermeasure item : effectiveCountermeasures) {
            int centerX = item.getX();
            int centerY = item.getY();

            // Визначаємо радіус круга
            double radius = (double) item.getCountermeasure().getDistance() / 1000;

            // Обчислюємо відстань між точками (x, y) і (centerX, centerY)
            double distanceToCenter = Math.sqrt(Math.pow(x - centerX, 2) + Math.pow(y - centerY, 2));

            var hMax = item.getCountermeasure().getHMax();
            var hMin = item.getCountermeasure().getHMin();

            //  LAYERS[z] - нижня границя слоя - перевірка, чи засіб протидії перекриває слой
            boolean inLayerDown = hMax >= layers[z] && hMin <= layers[z];
            boolean inLayerIn = hMax <= layers[z + 1] && hMin >= layers[z];
            boolean inLayerUp = hMax >= layers[z + 1] && hMin <= layers[z + 1];

            // ініціалізація зон прольоту  показниками небезпеки та достовірності об'єктів протидії

            // Перевіряємо, чи відстань менше або дорівнює радіусу круга
            if (distanceToCenter <= radius && (inLayerDown || inLayerIn || inLayerUp)) {
                // Якщо відстань менше або дорівнює радіусу, то точка (x, y) належить колу
                // збільшуємо вірогідності знищення/придушення/виявлення зони на вірогідності від засобу протидії

                var countermeasure = item.getCountermeasure();
                if (countermeasure.getCmType().equals("ППО")) {

                    double countermeasureDestruction = countermeasure.getDamageProbability(); // показник небезпеки
                    zone.setDestruction(1 - (1 - zone.getDestruction()) * (1 - countermeasureDestruction));   // додаємно до наявного значення зчитане
                    zone.setDestructionCredibility(1 - (1 - zone.getDestructionCredibility()) * (1 - item.getCredibility())); // достовірнсть

                    // додати ризик ураження до списку
                    zone.getTerminators().add(new ZoneCountermeasure(cmCount, countermeasure.getNChannels(),
                            countermeasure.getDamageProbability(), item.getCredibility(), countermeasure.getCmType(),
                            countermeasure.getNChannels(), 0, 0.0));

                    // з Credibility тут як середня температура по лікарні
                    // бо складаємо до купи кулемет та С-300
                } else if (item.getCountermeasure().getCmType().equals("РЕБ")) {
                    double countermeasureJamming = item.getCountermeasure().getDamageProbability();  // від засоба протидії
                    var zoneJammingNew = 1 - (1 - zone.getJamming()) * (1 - countermeasureJamming);
                    zone.setJamming(zoneJammingNew);
                    zone.setJammingCredibility(1 - (1 - zone.getJammingCredibility()) * (1 - item.getCredibility()));

                    // додати ризик ураження до списку
                    zone.getTerminators().add(new ZoneCountermeasure(cmCount, countermeasure.getNChannels(),
                            countermeasure.getDamageProbability(), item.getCredibility(), countermeasure.getCmType(),
                            countermeasure.getNChannels(), 0, 0.0));
                } else {
                    double countermeasureDetection = item.getCountermeasure().getDamageProbability();
                    zone.setDetection(1 - (1 - zone.getDetection()) * (1 - countermeasureDetection));
                    zone.setDetectionCredibility(1 - (1 - zone.getDetectionCredibility()) * (1 - item.getCredibility()));

                    // додати ризик виявлення до списку
                    zone.getDetectors().add(new ZoneCountermeasure(cmCount, countermeasure.getNChannels(),
                            countermeasure.getDamageProbability(), item.getCredibility(), countermeasure.getCmType(),
                            countermeasure.getNChannels(), 0, 0.0));
                }
            }
            cmCount++;
        }
        // розраховуємо  комплексне значення небезпеки зони прольоту
        double damageProbability = 1 - (1 - zone.getDestruction()) * (1 - 0.7 * zone.getJamming()) * (1 - 0.5 * zone.getDetection());
        // додамо до всіх нод, аби маршрут обирався пряміший
        if (damageProbability < NB_MIN) {
            damageProbability = NB_MIN;
        }

        if (zone.getDetectors().size() == 0) {
            zone.setDetection(0.05);
        }

        if (zone.getTerminators().size() == 0) {
            damageProbability = 0.05;
        }

        zone.setDamageProbability(damageProbability);
        return zone;
    }

    @Cacheable("getZonesCache")
    @Override
    public Zone[][][] getZones(List<EffectiveCountermeasure> effectiveCountermeasures, RouteParam params) {
        System.out.println("getZones");
        int x = params.getDimX();
        int y = params.getDimY();
        int z = params.getLayersArray().length - 1;
        Zone[][][] zones = new Zone[z][y][x];
        int id = 0;
        for (int i = 0; i < z; i++) {
            for (int j = 0; j < y; j++) {
                for (int k = 0; k < x; k++) {
                    Zone zone = getZone(id++, i, j, k, effectiveCountermeasures, params); // Оновлено індекси
                    zones[i][j][k] = zone;
                }
            }
        }
        return zones;
    }
}

