package pl.tomaszmiller.AppNo2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * Created by Peniakoff on 10.06.2017.
 */
@Configuration
@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {

    @Autowired
    LoginHandler loginHandler;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
            .antMatchers("/").permitAll()
            .antMatchers("/checkuser").permitAll()
            .antMatchers("/login").permitAll()
            .antMatchers("/administrator").hasRole("ADMIN")
            .anyRequest().authenticated();
//            .and()
//                .formLogin()
//                .successHandler(loginHandler)
//                .loginPage("/login")
//                .permitAll()
//                .and()
//                    .logout()
//                    .permitAll();
        http.exceptionHandling().accessDeniedPage("/403");
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
            .inMemoryAuthentication()
                .withUser("tomasz").password("mojehaslo1").roles("ADMIN") //it (and this all file) shouldn't to be commited but it's just for training
            .and()
                .withUser("user").password("test").roles("USER");
    }

}
