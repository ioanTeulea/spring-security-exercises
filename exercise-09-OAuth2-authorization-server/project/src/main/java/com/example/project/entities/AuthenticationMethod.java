package com.example.project.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;

@Entity
@Table(name="authentication_methods")
@Getter
@Setter
public class AuthenticationMethod {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="authentication_method")
    private String authenticationMethod;

    @ManyToOne
    private Client client;

    public static AuthenticationMethod from(ClientAuthenticationMethod method,Client client) {
        AuthenticationMethod authMethod = new AuthenticationMethod();
        authMethod.setAuthenticationMethod(method.getValue());
        authMethod.setClient(client);
        return authMethod;
    }

}
