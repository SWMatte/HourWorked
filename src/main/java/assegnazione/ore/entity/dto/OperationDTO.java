package assegnazione.ore.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OperationDTO {


    private Long totalDayForMonth;
    private Long numberDayWorked;
    private Double sumHourWorked; //TODO: queste ore sono quelle effettivamente lavorate senza la malattia / permesso
    private Double totalHourForMonth;  // TODO: queste ore sono le totali in un mese
    private Long illnessDay;
    private Long dayOff;


}
