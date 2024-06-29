package assegnazione.ore.service.impl;

import assegnazione.ore.BaseService;
import assegnazione.ore.Utility;
import assegnazione.ore.entity.Company;
import assegnazione.ore.entity.Location;
import assegnazione.ore.entity.User;
import assegnazione.ore.entity.dto.DayWorkedDTO;
import assegnazione.ore.entity.HourWorked;
import assegnazione.ore.entity.dto.OperationDTO;
import assegnazione.ore.repository.CompanyRepository;
import assegnazione.ore.repository.HourWorkedRepository;
import assegnazione.ore.repository.UserRepository;
import assegnazione.ore.service.Element;
import assegnazione.ore.service.iHourWorked;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Data
@Slf4j
public class WorkDayService extends BaseService implements Element<DayWorkedDTO>, iHourWorked {

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final HourWorkedRepository hourWorkedRepository;


    @Autowired
    private final CompanyRepository companyRepository;

    @Override
    public ResponseEntity<?> addElement(DayWorkedDTO dayWorkedDTO) throws Exception {
        try {
            log.info("Enter into: " + getCurrentClassName() + " start method: " + getCurrentMethodName());
            User user = userRepository.findById(dayWorkedDTO.getUser().getIdUser())
                    .orElseThrow(EntityNotFoundException::new);


            Company company = companyRepository.findById(dayWorkedDTO.getCompany().getIdCompany()).orElseThrow(EntityNotFoundException::new);

            dayWorkedDTO.getHourWorkedDTO().forEach(hoursWorkedDTO -> {
                HourWorked hourWorked = HourWorked.builder()
                        .month(hoursWorkedDTO.getMonth())
                        .day(hoursWorkedDTO.getDay())
                        .number(hoursWorkedDTO.getNumber())
                        .holiday(hoursWorkedDTO.getHoliday())
                        .hour(hoursWorkedDTO.getHour())
                        .year(hoursWorkedDTO.getYear())
                        .note(hoursWorkedDTO.getNote())
                        .place(Location.valueOf(hoursWorkedDTO.getPlace()))
                        .illness(hoursWorkedDTO.getIllness())
                        .dayOff(hoursWorkedDTO.getDayOff())
                        .user(user)
                        .company(company)
                        .build();
                hourWorkedRepository.save(hourWorked);
            });
            log.info("Finish method: " + getCurrentMethodName());
            return ResponseEntity.ok().build();
        } catch (EntityNotFoundException e) {
            log.error("Error into: " + getCurrentClassName() + "method: " + getCurrentMethodName());
            throw new EntityNotFoundException();
        } catch (Exception e) {
            log.error("Error into: " + getCurrentClassName() + "method: " + getCurrentMethodName());
            throw new Exception();
        }
    }


    @Override
    public OperationDTO countDayAndHour(String lastName, String month) {
        if (Utility.isNotNullOrEmpty(lastName) && Utility.isNotNullOrEmpty(month)) {
             OperationDTO  operationDTO =  hourWorkedRepository.getInformation(lastName,month);

            return operationDTO;
         } else {
            throw new RuntimeException(String.format("Some value of '%s' or '%s' are empty", lastName, month));
        }


    }
}
