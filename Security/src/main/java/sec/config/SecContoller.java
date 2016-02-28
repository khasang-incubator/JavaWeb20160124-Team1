package sec.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.sql.DataSource;


@Controller
@EnableWebSecurity
public class SecContoller extends WebSecurityConfigurerAdapter {
    @Autowired
    DataSource mDataSource;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication().
                dataSource(mDataSource).usersByUsernameQuery("SELECT username, password, valid FROM users WHERE username=?").
                authoritiesByUsernameQuery("SELECT username,role FROM authorities WHERE username=?");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin().and().
                authorizeRequests().
                antMatchers("/private").authenticated().
                anyRequest().permitAll();
    }
}
