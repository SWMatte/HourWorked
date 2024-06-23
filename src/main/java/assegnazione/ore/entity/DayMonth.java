package assegnazione.ore.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *  This class return a specific month with days ex: " 22-06-2024 - saturday ", the specific format is made on the controller
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DayMonth {
    private String day;
    private String number;



}
