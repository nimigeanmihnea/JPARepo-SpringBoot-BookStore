package application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

/**
 * Created by Mihnea on 22/05/2017.
 */

@Configuration
@EnableWebSecurity
public class SecureLogin extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserCredentialsService userCredentialsService;

    @Override
    public void configure(HttpSecurity httpSecurity) throws Exception{
        httpSecurity.csrf().disable();
        httpSecurity.authorizeRequests().antMatchers("/", "/resources/**", "/templates/**", "/static/**",
                "/css/**", "/js/**").permitAll();
    }

    public PasswordEncoder passwordEncoder(){
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder;
    }

    @RequestMapping(value = "/logout")
    public ModelAndView logout(HttpSession session){
        session.invalidate();
        return new ModelAndView("/login");
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception{
        try {
            auth.userDetailsService(this.userCredentialsService)
                    .passwordEncoder(passwordEncoder());
        }catch (Exception ex){ ex.printStackTrace(); }
    }
}
