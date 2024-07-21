package assegnazione.ore.entity.dto;

import assegnazione.ore.entity.Location;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class TableDTO {
    private String day;
    private String number;
    private double hour;
    private Location place;
    private String month;
    private String year;




}
