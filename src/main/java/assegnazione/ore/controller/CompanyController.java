package assegnazione.ore.controller;

import assegnazione.ore.BaseService;
import assegnazione.ore.entity.Company;
import assegnazione.ore.entity.User;
import assegnazione.ore.service.Element;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/")
@AllArgsConstructor
@Slf4j
public class CompanyController extends BaseService {

    @Autowired private final Element<Company> companyElement;


    @PostMapping("addCompany")
    public ResponseEntity<?> addCompany(@RequestBody Company company) {
        log.info("Enter into: "+getCurrentClassName()+" start method: "+ getCurrentMethodName());
        try {
            companyElement.addElement(company);
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
}
