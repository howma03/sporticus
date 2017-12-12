package com.sporticus.web.config;

import com.sporticus.util.logging.LogFactory;
import com.sporticus.util.logging.Logger;
import com.sporticus.web.controllers.ControllerAbstract;
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
import org.springframework.security.web.session.InvalidSessionStrategy;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ConfigurationSecurity extends WebSecurityConfigurerAdapter {

    private static final Logger LOGGER = LogFactory.getLogger(ConfigurationSecurity.class.getName());

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private HttpAuthenticationEntryPoint authenticationEntryPoint;

    @Autowired
    private AuthFailureHandler authFailureHandler;

    @Autowired
    private HttpLogoutSuccessHandler logoutSuccessHandler;

//    @Bean
//    public HttpSessionEventPublisher httpSessionEventPublisher() {
//        return new HttpSessionEventPublisher();
//    }

    @Autowired
    public ConfigurationSecurity(final UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
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
        http.sessionManagement().invalidSessionStrategy(new InvalidSessionStrategy() {
            @Override
            public void onInvalidSessionDetected(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
                LOGGER.debug(()->"Invalid session seen");
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            }
        });

        http.httpBasic().and()

                .exceptionHandling().authenticationEntryPoint(authenticationEntryPoint)
                .and()

                .authorizeRequests()

                .antMatchers("/resources/**").permitAll()
                .antMatchers("/papi/**").permitAll()
                .antMatchers("/app*").permitAll()
                .antMatchers("/app/**").permitAll()
                .antMatchers("/accessDenied*").permitAll()

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
                .logoutSuccessHandler(logoutSuccessHandler)
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
