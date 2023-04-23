package nexign.bootcamp.crm.repository;

import nexign.bootcamp.crm.entity.Abonent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AbonentRepo extends JpaRepository<Abonent, Integer> {
    Optional<Abonent> findByPhoneNumber(String phoneNumber);
}
