package assegnazione.ore.repository;

import assegnazione.ore.entity.HourWorked;
import assegnazione.ore.entity.dto.OperationDTO;
import assegnazione.ore.entity.dto.TableDTO;
import assegnazione.ore.entity.dto.UserDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface HourWorkedRepository extends JpaRepository<HourWorked, Integer> {
    /**
     * This query retrieve from the HourWorked entity:
     * - the total hour worked from one user,
     * - the total of days worked from the user,
     * - the total of day in a month
     * - the total of hours in a mount
     * - the sum of illness's days
     */
    @Query("""
             SELECT NEW assegnazione.ore.entity.dto.OperationDTO(
                 u.lastName,
                 SUM(CASE WHEN hw.hour <> 0 THEN 1 ELSE 0 END),
                 COUNT(*),
                 SUM(hw.hour + hw.hoursOff + hw.holiday),
                 SUM(hw.illness),
                 SUM(hw.hoursOff),
                 SUM(hw.hour)
             )
             FROM HourWorked hw
             INNER JOIN hw.user u
             WHERE hw.month = :month AND u.lastName = :lastName
             GROUP BY u.idUser
            """)
    OperationDTO getInformation(@Param("lastName") String lastName, @Param("month") String month);



    @Query("""
                SELECT NEW assegnazione.ore.entity.dto.TableDTO(hw.day,
                                                                 hw.number,
                                                                 hw.hour,
                                                                 hw.place,
                                                                 :month,
                                                                 hw.year,
                                                                 hw.extraWork)
                FROM HourWorked hw
            """)
    List<TableDTO> getTableValue(@Param("month") String month);



    @Query("""
                select sum(hw.hour+ hw.extraWork) as totale_ore_lavorate
             FROM HourWorked hw
                where hw.month = :month
            """)
    Double getTotalHourWorked(String month);


    @Query("""
                select sum(hw.illness) as giorni_malattia
             FROM HourWorked hw
            """)
    Double getIllnessDay();


    @Query("""
                   select sum(hw.hour + hw.hoursOff + hw.illness ) as totale_ore_mensili
             FROM HourWorked hw
                   where month =:month
            """)
    Double getTotalHoursForMonth(String month);

    @Query("""
                   select count(*) as giorni_lavorati
             FROM HourWorked hw
                      where hw.hour <> 0 and hw.month =:month
            """)
    Double getWorkedDay(String month);

    @Query("""
             select count(*) as giorni_totali_mese
             FROM HourWorked hw
             WHERE hw.month=:month
            """)
    Integer getTotalDayForMonth(String month);


    @Query("""
             select sum(hw.extraWork) as ore_straordinario
             FROM HourWorked hw
             WHERE hw.month=:month
            """)
    Double getTotalExtraWork(String month);

    @Query("""
        SELECT NEW assegnazione.ore.entity.dto.UserDTO(u.name, u.lastName)
        FROM HourWorked hw
        INNER JOIN hw.user u
        WHERE  hw.month = :month
        GROUP BY u.name, u.lastName
        """)
    UserDTO getUserDTO(String month);


}
