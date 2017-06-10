package pl.tomaszmiller.AppNo2.models;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

/**
 * Created by Peniakoff on 10.06.2017.
 */
@Component
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS) //zasięg beana na sesję usera!
public class UserInfoBean {

    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isUserLogged() {
        return SecurityContextHolder.getContext().getAuthentication().getPrincipal() == null ? false : true; //here is the user's session
    }

}
