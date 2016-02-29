package io.khasang.wlogs.app.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.expression.SecurityExpressionHandler;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.access.vote.AffirmativeBased;
import org.springframework.security.access.vote.AuthenticatedVoter;
import org.springframework.security.access.vote.RoleHierarchyVoter;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;
import org.springframework.security.web.access.expression.WebExpressionVoter;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableWebSecurity
@Import(AppConfig.class)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    protected DriverManagerDataSource dataSource;
    @Autowired
    protected BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    protected SecurityExpressionHandler<FilterInvocation> defaultWebSecurityExpressionHandler;
    @Autowired
    protected AffirmativeBased accessDesignManager;

    @Override
    public void configure(WebSecurity web) throws Exception {
        web
                .ignoring()
                        .antMatchers("/css/**", "/js/**", "/images/**");
    }

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
                        .accessDecisionManager(this.accessDesignManager)
                        .expressionHandler(this.defaultWebSecurityExpressionHandler)
                        .antMatchers("/auth/login").anonymous()
                        .antMatchers("/users").anonymous()
                        .anyRequest().fullyAuthenticated()
                        .and()
                .formLogin()
                        .loginPage("/auth/login")
                        .defaultSuccessUrl("/")
                        .and()
                .logout();
    }

    @Bean
    public RoleHierarchyImpl roleHierarchy() {
        RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
        roleHierarchy.setHierarchy("ROLE_SUPER_ADMIN > ROLE_ADMIN > ROLE_USER");
        return roleHierarchy;
    }

    @Bean
    public RoleHierarchyVoter roleVoter(RoleHierarchyImpl roleHierarchy) {
        return new RoleHierarchyVoter(roleHierarchy);
    }

    @Bean
    public SecurityExpressionHandler<FilterInvocation> defaultWebSecurityExpressionHandler(RoleHierarchyImpl roleHierarchy) {
        DefaultWebSecurityExpressionHandler defaultWebSecurityExpressionHandler = new DefaultWebSecurityExpressionHandler();
        defaultWebSecurityExpressionHandler.setRoleHierarchy(roleHierarchy);
        return defaultWebSecurityExpressionHandler;
    }

    @Bean
    public AffirmativeBased accessDesignManager(RoleHierarchyVoter roleVoter, SecurityExpressionHandler<FilterInvocation> defaultWebSecurityExpressionHandler) {
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
