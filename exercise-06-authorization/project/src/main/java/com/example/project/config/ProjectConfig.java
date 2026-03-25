package com.example.project.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class ProjectConfig {

    @Bean
    public UserDetailsService userDetailsService() {
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();

        UserDetails user1= User.withUsername("john")
                .password("12345")
                .authorities("READ")
                .build();
        UserDetails user2= User.withUsername("jane")
                .password("12345")
                .roles("MANAGER")
                .build();
        manager.createUser(user1);
        manager.createUser(user2);

        return manager;
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.httpBasic(Customizer.withDefaults());

        http.authorizeHttpRequests( c-> {
                    c.requestMatchers("/ciao").hasRole("MANAGER");
                    c.requestMatchers(HttpMethod.GET,"/endpoint-a").permitAll();
                    c.requestMatchers(HttpMethod.POST,"/endpoint-a").authenticated();
                    c.requestMatchers("/common/**").authenticated();
                    c.requestMatchers("/product/{code:^[0-9]*$}").permitAll();

                    c.anyRequest().hasAnyAuthority("READ", "WRITE");

                }

        )
        .csrf(c->c.disable());
        return http.build();
    }
}
