package assegnazione.ore.service;

import assegnazione.ore.entity.dto.OperationDTO;
import org.springframework.http.ResponseEntity;

public interface iUser {


    ResponseEntity<?> getUserInfo(String name, String lastName) throws Exception;
}
