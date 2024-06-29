package assegnazione.ore.repository;

import assegnazione.ore.entity.HourWorked;
import assegnazione.ore.entity.dto.OperationDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface HourWorkedRepository extends JpaRepository<HourWorked, Integer> {
    /**
     *
     *  This query retrieve from the HourWorked entity:
     *  - the total hour worked from one user,
     *  - the total of days worked from the user,
     *  - the total of day in a month
     *  - the total of hours in a mount
     *  - the sum of illness's days
     */
    @Query("""
         SELECT NEW assegnazione.ore.entity.dto.OperationDTO(
             u.lastName,
             SUM(CASE WHEN hw.hour <> 0 THEN 1 ELSE 0 END),
             COUNT(*),
             SUM(hw.hour + hw.dayOff + hw.holiday),
             SUM(hw.illness),
             SUM(hw.dayOff),
             SUM(hw.hour)
         )
         FROM HourWorked hw
         INNER JOIN hw.user u
         WHERE hw.month = :month AND u.lastName = :lastName
         GROUP BY u.idUser
        """)
    OperationDTO getInformation(@Param("lastName") String lastName, @Param("month") String month);
}