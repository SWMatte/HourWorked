package assegnazione.ore.repository;

import assegnazione.ore.entity.HourWorked;
import assegnazione.ore.entity.dto.OperationDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface HourWorkedRepository extends JpaRepository<HourWorked, Integer> {



//    COUNT(CASE WHEN h.day_off = '0' THEN h.day END) AS workingDays
}
