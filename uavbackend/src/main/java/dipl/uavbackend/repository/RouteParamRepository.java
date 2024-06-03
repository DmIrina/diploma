package dipl.uavbackend.repository;


import dipl.uavbackend.entity.RouteParam;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RouteParamRepository extends JpaRepository<RouteParam, Long> {
    List<RouteParam> findAllByOrderByTimestampDesc();

    RouteParam findFirstByOrderByTimestampDesc();
}
