package assegnazione.ore.service.impl;

import assegnazione.ore.BaseService;
import assegnazione.ore.Utility;
import assegnazione.ore.entity.User;
import assegnazione.ore.repository.UserRepository;
import assegnazione.ore.service.Element;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class UserService extends BaseService implements Element<User> {

    @Autowired
    private final UserRepository userRepository;

    @Override
    public ResponseEntity<?> addElement(User element) throws Exception {
        log.info("Enter into: "+getCurrentClassName()+" start method: "+ getCurrentMethodName());
        if(element!=null){
           userRepository.save(element);
            log.info("Finish method: "+ getCurrentMethodName());
            return ResponseEntity.ok().build();
       }else {
            log.error("Error into: " + getCurrentClassName() + "method: "+ getCurrentMethodName());
            throw new RuntimeException("element is null");
       }
    }

    @Override
    public ResponseEntity<?> getInformations(int id) throws Exception {
        log.info("Enter into: "+getCurrentClassName()+" start method: "+ getCurrentMethodName());
        if(Utility.isNotNullOrEmpty(id)){
            log.info("Finish method: "+ getCurrentMethodName());
            return ResponseEntity.ok().body(userRepository.findById(id));
        }else {
            log.error("Error into: " + getCurrentClassName() + "method: "+ getCurrentMethodName());
            throw new RuntimeException("element is null");
        }
    }
}
