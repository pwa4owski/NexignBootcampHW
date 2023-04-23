package nexign.bootcamp.crm.security.checkers;

import nexign.bootcamp.crm.entity.Manager;
import nexign.bootcamp.crm.repository.ManagerRepo;
import nexign.bootcamp.crm.security.UserPrincipal;
import nexign.bootcamp.crm.security.UserRole;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ManagerChecker extends UserRoleChecker{
    private final ManagerRepo repository;

    public ManagerChecker(ManagerRepo repository) {
        this.repository = repository;
    }

    @Override
    public UserPrincipal check(String username) {
        final Optional<Manager> managerOptional = repository.findByLogin(username);
        if(managerOptional.isPresent()){
            var manager = managerOptional.get();
            return UserPrincipal.builder()
                    .userRole(UserRole.ROLE_MANAGER)
                    .userId(manager.getId())
                    .username(manager.getLogin())
                    .password(manager.getPassword())
                    .build();
        }
        return checkNext(username);
    }
}
