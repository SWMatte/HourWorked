package assegnazione.ore.repository;

import assegnazione.ore.entity.Company;
import assegnazione.ore.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CompanyRepository extends JpaRepository<Company, Integer> {
}
