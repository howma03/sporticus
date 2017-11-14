package com.sporticus.web.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ConfigurationSecurity extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

//    @Bean
//    public HttpSessionEventPublisher httpSessionEventPublisher() {
//        return new HttpSessionEventPublisher();
//    }

    @Autowired
    public ConfigurationSecurity(final UserDetailsService svc) {
        this.userDetailsService = svc;
    }

    @Autowired
    public void configureGlobal(final AuthenticationManagerBuilder auth)
            throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(
                passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http.sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED);
        http.sessionManagement().maximumSessions(2);
        http.sessionManagement()
                .invalidSessionUrl("/invalidSession.html");

        http.httpBasic().and()
                .authorizeRequests()
                .antMatchers("/webjars/**").permitAll()
                .antMatchers("/resources/**").permitAll()
                .antMatchers("/papi/**").permitAll()

                .antMatchers("/landing*").permitAll()
                .antMatchers("/landing/**").permitAll()
                .antMatchers("/registration*").permitAll()
                .antMatchers("/registration/**").permitAll()
                .antMatchers("/password*").permitAll()
                .antMatchers("/password/**").permitAll()
                .antMatchers("/login*").permitAll()
                .antMatchers("/logout*").permitAll()
                .antMatchers("/accessDenied*").permitAll()
                .antMatchers("/home*").access("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
                .antMatchers("/home/**").access("hasAnyRole('ROLE_USER','ROLE_ADMIN')")

                .antMatchers("/app/**").access("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
                .antMatchers("/api/**").access("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
                .anyRequest().authenticated()

                .and()
                .formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/login")
                .defaultSuccessUrl("/home", true)
                .failureUrl("/accessDenied")
                .and()
                .exceptionHandling().accessDeniedPage("/accessDenied")

                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login")
                .clearAuthentication(true)
                .deleteCookies("JSESSIONID")
                .invalidateHttpSession(true)

                .and()
                .exceptionHandling().accessDeniedPage("/accessDenied")
                // .and().rememberMe()
                .and().csrf().disable();
    }

    @Bean
    public AuthenticationFailureHandler authenticationFailureHandler() {
        return new SimpleUrlAuthenticationFailureHandler();
    }
}
