package uz.pdp.pcmarket.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public InMemoryUserDetailsManager userDetailsManager() {
        UserDetails admin = User.withUsername("SUPER_ADMIN")
                .passwordEncoder(s -> passwordEncoder().encode("root123"))
                .roles("SUPER_ADMIN")
                .build();
        UserDetails moderator = User.withUsername("MODERATOR")
                .passwordEncoder(s -> passwordEncoder().encode("root123"))
                .roles("MODERATOR")
                .build();
        UserDetails operator = User.withUsername("OPERATOR")
                .passwordEncoder(s -> passwordEncoder().encode("root123"))
                .roles("OPERATOR")
                .build();
        return new InMemoryUserDetailsManager(admin,moderator,operator);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        userDetailsManager();
        http
                .csrf()
                .disable()
                .authorizeHttpRequests(auth ->
                        auth
                                .antMatchers("/api/auth/login","/api/auth/creat")
                                .permitAll()
                                .anyRequest()
                                .authenticated())
                .httpBasic(withDefaults());
        return http.build();
    }

}
