package dev.artsupplier.paintingtracker.security;

import com.vaadin.flow.spring.security.VaadinWebSecurity;
import dev.artsupplier.paintingtracker.views.LoginView;
import jakarta.annotation.security.PermitAll;
import org.springframework.boot.actuate.endpoint.web.annotation.WebEndpoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.authentication.configurers.userdetails.DaoAuthenticationConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityPalikka extends VaadinWebSecurity {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final AccessDeniedHandler accessDeniedHandler;

    public SecurityPalikka(UserService userService, PasswordEncoder passwordEncoder, AccessDeniedHandler accessDeniedHandler) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.accessDeniedHandler = accessDeniedHandler;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth -> auth.requestMatchers("/VAADIN/**", "/frontend/**", "/webjars/**").permitAll() // permit static resources
                .requestMatchers("/about", "/login").permitAll()
                .requestMatchers("/").permitAll()
                .requestMatchers("/admin/**").hasRole("ADMIN")
                .requestMatchers("/sessions/**").hasAnyRole("USER", "ADMIN")
                //.requestMatchers("/login").permitAll()
                //session only is loged in as user

                //.anyRequest().authenticated()
        )
        .exceptionHandling(exception -> exception
                .accessDeniedHandler(accessDeniedHandler)
        )
        .formLogin(login -> login
            .loginPage("/login")
                .defaultSuccessUrl("/sessions", true) // redirect to /sessions after login
        )
        .logout(logout -> logout
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET"))
                .logoutSuccessUrl("/login")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
        );


        super.configure(http);
        setLoginView(http, LoginView.class, "/login"); // after login, redirect to /sessions
    }




    /*
    //use BCryptPasswordEncoder for password encoding
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
     */

    //daoauthenticationprovider
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService((UserDetailsService) userService);  //userService is a UserDetailsService
        authProvider.setPasswordEncoder(passwordEncoder); //BCryptPasswordEncoder
        return authProvider;
    }

    // Register the AuthenticationManager with Spring Security
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
