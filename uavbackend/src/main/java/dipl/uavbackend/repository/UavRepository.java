package dipl.uavbackend.repository;

import dipl.uavbackend.entity.Uav;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UavRepository extends JpaRepository<Uav, Long> {
}
