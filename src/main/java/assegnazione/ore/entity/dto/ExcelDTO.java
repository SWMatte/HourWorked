package assegnazione.ore.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Component
public class ExcelDTO {


    private List<TableDTO> listTableDTO;
    private Double getTotalHoursForMonth;
    private Double getWorkedDay;
    private Integer getTotalDayForMonth;
    private Double getIllnessDay;
    private Double getTotalHourWorked;
}



