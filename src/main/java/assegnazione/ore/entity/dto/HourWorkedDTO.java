package assegnazione.ore.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class HourWorkedDTO {
    private String month;

    private String day;

    private String number;

    private String year;

    private double hour;

    private String note;

    private String place;

    private double illness;

    private Double holiday;

    private double dayOff;





}
