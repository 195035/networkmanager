package com.weeia.networkmanager.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalAuthentication
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String MAPPING_PAGE_LOGIN = "/login";
    private static final String MAPPING_PAGE_LOGOUT = "/logout";
    private static final String MAPPING_PAGE_REGISTER = "/register";

    private final PasswordEncoder encoder;

    @Autowired
    public SecurityConfig(PasswordEncoder encoder) {
        this.encoder = encoder;
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/css/**");
        web.ignoring().antMatchers("/static/**");
        web.ignoring().antMatchers("/view/**");
        web.ignoring().antMatchers("/webjars/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/", MAPPING_PAGE_REGISTER)
                    .permitAll()
                .antMatchers("/api/*")
                    .hasAnyAuthority("USER")
                    .anyRequest().authenticated()
                .and().httpBasic()
                .and().csrf().disable();

    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().withUser("admin").password(encoder.encode("admin")).roles("ADMIN", "USER");
        auth.inMemoryAuthentication().withUser("user").password(encoder.encode("user")).roles("USER");
    }

}
