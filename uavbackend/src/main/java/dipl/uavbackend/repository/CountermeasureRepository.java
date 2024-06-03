package dipl.uavbackend.repository;

import dipl.uavbackend.entity.Countermeasure;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CountermeasureRepository extends JpaRepository<Countermeasure, Long> {
}
