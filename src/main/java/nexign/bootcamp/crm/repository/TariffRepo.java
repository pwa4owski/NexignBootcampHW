package nexign.bootcamp.crm.repository;

import nexign.bootcamp.crm.entity.Tariff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TariffRepo extends JpaRepository<Tariff, Integer> {

}
