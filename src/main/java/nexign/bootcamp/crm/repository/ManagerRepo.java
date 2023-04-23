package nexign.bootcamp.crm.repository;

import nexign.bootcamp.crm.entity.Manager;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ManagerRepo extends JpaRepository<Manager, Integer> {
    Optional<Manager> findByLogin(String login);
}
