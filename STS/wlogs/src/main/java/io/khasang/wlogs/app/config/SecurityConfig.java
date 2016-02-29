package io.khasang.wlogs.app.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.access.vote.AffirmativeBased;
import org.springframework.security.access.vote.AuthenticatedVoter;
import org.springframework.security.access.vote.RoleHierarchyVoter;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;
import org.springframework.security.web.access.expression.WebExpressionVoter;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    protected DriverManagerDataSource dataSource;
    @Autowired
    protected BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    protected DefaultWebSecurityExpressionHandler defaultWebSecurityExpressionHandler;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .inMemoryAuthentication()
                        .passwordEncoder(this.bCryptPasswordEncoder)
                        .withUser("root").password("$2a$10$mf.PoIoy3rL5rbnMyjrL2.JrybwIV6VRduXcJVg1wUCL89MWlwdd.").roles("SUPER_ADMIN")
                        .and();
        auth
                .jdbcAuthentication()
                        .passwordEncoder(this.bCryptPasswordEncoder)
                        .dataSource(this.dataSource)
                        .usersByUsernameQuery("SELECT username, password, enabled FROM wlogs_users WHERE ? IN (username, email)")
                        .authoritiesByUsernameQuery("SELECT username, role FROM wlogs_user_roles WHERE username=?");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                        .antMatchers("/auth/login").anonymous()
                        .antMatchers(HttpMethod.POST, "/users").anonymous()
                        .antMatchers(HttpMethod.GET, "/users").anonymous()
                        .antMatchers("/css/**", "/js/**", "/images/**").permitAll()
                        .antMatchers("/**").fullyAuthenticated()
                        .expressionHandler(this.defaultWebSecurityExpressionHandler)
                        .and()
                .formLogin()
                        .loginPage("/auth/login")
                        .defaultSuccessUrl("/")
                        .and()
                .logout()
                        .and()
                ;
    }

    @Bean
    public RoleHierarchyImpl roleHierarchy() {
        RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
        roleHierarchy.setHierarchy("SUPER_ADMIN > ADMIN > USER");
        return roleHierarchy;
    }

    @Bean
    public RoleHierarchyVoter roleVoter(RoleHierarchyImpl roleHierarchy) {
        return new RoleHierarchyVoter(roleHierarchy);
    }

    @Bean
    public DefaultWebSecurityExpressionHandler defaultWebSecurityExpressionHandler(RoleHierarchyImpl roleHierarchy) {
        DefaultWebSecurityExpressionHandler defaultWebSecurityExpressionHandler = new DefaultWebSecurityExpressionHandler();
        defaultWebSecurityExpressionHandler.setRoleHierarchy(roleHierarchy);
        return defaultWebSecurityExpressionHandler;
    }

    @Bean
    public AffirmativeBased accessDesignManager(RoleHierarchyVoter roleVoter, DefaultWebSecurityExpressionHandler defaultWebSecurityExpressionHandler) {
        List<AccessDecisionVoter<? extends Object>> args = new ArrayList<>();
        WebExpressionVoter webExpressionVoter = new WebExpressionVoter();
        webExpressionVoter.setExpressionHandler(defaultWebSecurityExpressionHandler);
        AuthenticatedVoter authenticatedVoter = new AuthenticatedVoter();
        args.add(roleVoter);
        args.add(authenticatedVoter);
        args.add(webExpressionVoter);
        return new AffirmativeBased(args);
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
