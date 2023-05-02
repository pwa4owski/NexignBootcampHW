package nexign.bootcamp.crm.security;

import nexign.bootcamp.crm.security.checkers.AbonentChecker;
import nexign.bootcamp.crm.security.checkers.ManagerChecker;
import nexign.bootcamp.crm.security.checkers.UserRoleChecker;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {

    private final AbonentChecker abonentChecker;
    private final ManagerChecker managerChecker;

    public MyUserDetailsService(AbonentChecker abonentChecker, ManagerChecker managerChecker) {
        this.abonentChecker = abonentChecker;
        this.managerChecker = managerChecker;
    }

    /**
     * Для определения роли пользователя использован паттерн ChainOfResponsibility,
     * что делает данный модуль легко-расширяемым, поскольку вся логика проверки
     * на соответствие той, или иной роли, инкапсулирована внутри классов обработчиков:
     * в нашем случае - {@link AbonentChecker} и {@link ManagerChecker}. Достаточно добавить
     * в цепь новую реализацию {@link UserRoleChecker}.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserRoleChecker userRoleChecker = UserRoleChecker.link(
                abonentChecker,
                managerChecker
        );
        UserPrincipal userPrincipal = userRoleChecker.check(username);
        if(userPrincipal == null){
            throw new UsernameNotFoundException("Такого аккаунта не существует");
        }
        return userPrincipal;
    }

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }
}
