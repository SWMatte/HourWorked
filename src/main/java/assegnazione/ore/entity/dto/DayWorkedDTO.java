package assegnazione.ore.entity.dto;

import assegnazione.ore.entity.Company;
import assegnazione.ore.entity.HourWorked;
import assegnazione.ore.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DayWorkedDTO {
    private List<HourWorkedDTO> hourWorkedDTO;
    private User user;
    private Company company;
}
