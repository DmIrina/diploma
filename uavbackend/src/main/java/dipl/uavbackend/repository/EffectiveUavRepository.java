package dipl.uavbackend.repository;

import dipl.uavbackend.entity.EffectiveUav;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EffectiveUavRepository extends JpaRepository<EffectiveUav, Long> {
}
