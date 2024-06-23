package assegnazione.ore.controller;

import assegnazione.ore.BaseService;
import assegnazione.ore.entity.DayMonth;
import assegnazione.ore.entity.dto.DayWorkedDTO;
import assegnazione.ore.entity.dto.OperationDTO;
import assegnazione.ore.service.Element;
import assegnazione.ore.service.iHourWorked;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.format.TextStyle;

import java.time.LocalDate;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping("api/v1/")
@AllArgsConstructor
@Slf4j
public class HourController extends BaseService {
    @Autowired
    private final Element<DayWorkedDTO> workDayService;


    @Autowired
    private final iHourWorked iHourWorked;

    /**
     * @param month represent the specific month
     * @param year  represent the specific year
     * @return a list of day's of month
     */
    @GetMapping("getMonth")
    public List<DayMonth> getMonth(@RequestParam int month, @RequestParam int year) {
        log.info("Enter into: " + getCurrentClassName() + " start method: " + getCurrentMethodName());
        List<DayMonth> days = new ArrayList<>();
        LocalDate date = LocalDate.of(year, month, 1);
        int lengthOfMonth = date.lengthOfMonth();

        for (int day = 1; day <= lengthOfMonth; day++) {
            LocalDate currentDate = date.withDayOfMonth(day);
            String dayOfWeek = currentDate.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.ITALIAN);
            DayMonth dayMonth = DayMonth.builder()
                    .day(dayOfWeek)
                    .number(String.format("%02d-%02d-%04d", day, month, year)) // format 2 cifre decimali per giorno/mese/ 4 per l'anno
                    .build();
            days.add(dayMonth);
        }
        log.info("Finish method: " + getCurrentMethodName());

        return days;
    }


    @PostMapping("sendHourWorked")
    public ResponseEntity<?> sendHourWorked(@RequestBody DayWorkedDTO dayWorkedDTO) {
        log.info("Enter into: " + getCurrentClassName() + " start method: " + getCurrentMethodName());

        try {
            workDayService.addElement(dayWorkedDTO);
            log.info("Finish method: " + getCurrentMethodName());
            return ResponseEntity.ok().build();
        } catch (EntityNotFoundException e) {
            log.error("Error into: " + getCurrentClassName() + "method: " + getCurrentMethodName());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            log.error("Error into: " + getCurrentClassName() + "method: " + getCurrentMethodName());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }


    @PostMapping("operationHourAndDay")
    public OperationDTO countDayAndHour(@RequestParam String lastName, @RequestParam String month) {
        log.info("Enter into: " + getCurrentClassName() + " start method: " + getCurrentMethodName());
        return iHourWorked.countDayAndHour(lastName, month);

    }
}
