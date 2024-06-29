package assegnazione.ore.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class OperationDTO {
    private String lastName;
    private double numberDayWorked;
    private double totalDayForMonth;
    private double totalHourForMonth;
    private double illnessDay;
    private double dayOff;
    private double sumHourWorked;

}
