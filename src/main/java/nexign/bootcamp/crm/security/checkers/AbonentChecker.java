package nexign.bootcamp.crm.security.checkers;

import nexign.bootcamp.crm.entity.Abonent;
import nexign.bootcamp.crm.repository.AbonentRepo;
import nexign.bootcamp.crm.security.UserPrincipal;
import nexign.bootcamp.crm.security.UserRole;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AbonentChecker extends UserRoleChecker{
    private final AbonentRepo repository;

    public AbonentChecker(AbonentRepo repository) {
        this.repository = repository;
    }

    @Override
    public UserPrincipal check(String username) {
        final Optional<Abonent> abonentOptional = repository.findByPhoneNumber(username);
        if(abonentOptional.isPresent()){
            var abonent = abonentOptional.get();
            return UserPrincipal.builder()
                    .userRole(UserRole.ROLE_ABONENT)
                    .userId(abonent.getId())
                    .username(abonent.getPhoneNumber())
                    .password(abonent.getPassword())
                    .build();
        }
        return checkNext(username);
    }
}
