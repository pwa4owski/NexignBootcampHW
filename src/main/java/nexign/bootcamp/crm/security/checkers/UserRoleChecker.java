package nexign.bootcamp.crm.security.checkers;

import nexign.bootcamp.crm.security.UserPrincipal;
import org.springframework.stereotype.Component;

@Component
public abstract class UserRoleChecker {
    private  UserRoleChecker next;

    public static UserRoleChecker link(UserRoleChecker first, UserRoleChecker ... chain){
        UserRoleChecker node = first;
        for(UserRoleChecker nextInChain : chain){
            node.next = nextInChain;
            node = nextInChain;
        }
        return first;
    }

    public abstract UserPrincipal check(String username);

    protected UserPrincipal checkNext(String username){
        if(next == null){
            return null;
        }
        return next.check(username);
    }
}
