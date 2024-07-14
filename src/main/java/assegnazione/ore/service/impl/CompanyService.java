package assegnazione.ore.service.impl;

import assegnazione.ore.BaseService;
import assegnazione.ore.Utility;
import assegnazione.ore.entity.Company;
import assegnazione.ore.entity.User;
import assegnazione.ore.repository.CompanyRepository;
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
public class CompanyService extends BaseService implements Element<Company> {

    @Autowired
    private final CompanyRepository companyRepository;

    @Override
    public ResponseEntity<?> addElement(Company element) throws Exception {
        log.info("Enter into: "+getCurrentClassName()+" start method: "+ getCurrentMethodName());
        if(element!=null){
            companyRepository.save(element);
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
            return ResponseEntity.ok().body( companyRepository.findById(id));
        }else {
            log.error("Error into: " + getCurrentClassName() + "method: "+ getCurrentMethodName());
            throw new RuntimeException("element is null");
        }
    }
}
