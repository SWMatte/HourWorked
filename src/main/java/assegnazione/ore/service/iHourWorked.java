package assegnazione.ore.service;

import assegnazione.ore.entity.dto.OperationDTO;

public interface iHourWorked {


    OperationDTO countDayAndHour(String lastName, String month);
}
