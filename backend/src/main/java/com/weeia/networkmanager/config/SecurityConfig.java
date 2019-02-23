package com.weeia.networkmanager.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebSecurity
@EnableGlobalAuthentication
@EnableWebMvc
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String MAPPING_PAGE_LOGIN = "/login";
    private static final String MAPPING_PAGE_LOGOUT = "/logout";
    private static final String MAPPING_PAGE_REGISTER = "/register";

    private final UserDetailsService userDetailsService;
    private PasswordEncoder encoder;
    private final DataSource dataSource;

    @Autowired
    public SecurityConfig(UserDetailsService userDetailsService, PasswordEncoder encoder, DataSource dataSource) {
        this.userDetailsService = userDetailsService;
        this.encoder = encoder;
        this.dataSource = dataSource;
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/css/**");
        web.ignoring().antMatchers("/static/**");
        web.ignoring().antMatchers("/templates/**");
        web.ignoring().antMatchers("/webjars/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/*","/public/**", MAPPING_PAGE_REGISTER)
                    .permitAll()
                .antMatchers("/web/**", "/api/**")
                    .hasAnyAuthority("ROLE_USER")
                    .anyRequest().authenticated()
//                    .permitAll()
                .and()
                    .formLogin()
                    .loginPage(MAPPING_PAGE_LOGIN)
                .permitAll()
                .and()
                .logout()
                    .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                    .logoutSuccessUrl(MAPPING_PAGE_LOGIN)
                .permitAll()
                .and()
                    .csrf().ignoringAntMatchers("/api/**")
        ;

    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(encoder);
    }

    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl db = new JdbcTokenRepositoryImpl();
        db.setDataSource(dataSource);
        return db;
    }

}
