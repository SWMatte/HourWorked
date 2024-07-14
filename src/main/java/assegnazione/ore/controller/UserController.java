package assegnazione.ore.controller;

import assegnazione.ore.BaseService;
import assegnazione.ore.entity.User;
import assegnazione.ore.entity.dto.DayWorkedDTO;
import assegnazione.ore.service.Element;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/")
@AllArgsConstructor
@Slf4j
public class UserController extends BaseService {

    @Autowired private final Element<User> userElement;



    @PostMapping("addUser")
    public ResponseEntity<?> addUser(@RequestBody User user) {
        log.info("Enter into: "+getCurrentClassName()+" start method: "+ getCurrentMethodName());
        try {
            userElement.addElement(user);
            log.info("Finish method: "+ getCurrentMethodName());
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            log.error("Error into: " + getCurrentClassName() + "method: "+ getCurrentMethodName());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            log.error("Error into: " + getCurrentClassName() + "method: "+ getCurrentMethodName());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("getUser")
    public ResponseEntity<?> getUserInformations(@RequestParam int id){
        log.info("Enter into: "+getCurrentClassName()+" start method: "+ getCurrentMethodName());
        try {

            log.info("Finish method: "+ getCurrentMethodName());
            return ResponseEntity.ok().body( userElement.getInformations(id));
        } catch (RuntimeException e) {
            log.error("Error into: " + getCurrentClassName() + "method: "+ getCurrentMethodName());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            log.error("Error into: " + getCurrentClassName() + "method: "+ getCurrentMethodName());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
