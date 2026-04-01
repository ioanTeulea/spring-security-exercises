package com.example.project.entities;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;

import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

@Entity
@Table(name = "clients")
@Getter
@Setter
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="client_id")
    private String clientId;

    private String secret;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private List<AuthenticationMethod> authenticationMethods;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private List<GrantType> grantType;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private List<RedirectUrl>redirectUrls;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private List<Scope> scopes;

    public static RegisteredClient mapToRegisteredClient(Client client) {
        RegisteredClient registeredClient =
                RegisteredClient
                        .withId(String.valueOf(client.getId()))
                        .clientId(client.getClientId())
                        .clientSecret(client.getSecret())
                        .clientAuthenticationMethods(authenticationMethods(client.getAuthenticationMethods()))
                        .authorizationGrantTypes(authorizationGrantType(client.getGrantType()))
                        .redirectUris(redirectUrls(client.getRedirectUrls()))
                        .scopes(scopes(client.getScopes()))
                        .build();

        return registeredClient;
    }

    private static Consumer<Set<AuthorizationGrantType>> authorizationGrantType(List<GrantType> grantTypes) {
        return authGrantTypes -> {
            for (GrantType grantType : grantTypes) {
                authGrantTypes.add(new AuthorizationGrantType(grantType.getGrantType()));
            }
        };
    }
    private static Consumer<Set<ClientAuthenticationMethod>> authenticationMethods(List<AuthenticationMethod> authenticationMethods) {
        return authMethods -> {
            for (AuthenticationMethod method : authenticationMethods) {
                authMethods.add(new ClientAuthenticationMethod(method.getAuthenticationMethod()));
            }
        };
    }
    private static Consumer<Set<String>> redirectUrls(List<RedirectUrl> redirectUrls) {
        return urls -> {
            for (RedirectUrl redirectUrl : redirectUrls) {
                urls.add(redirectUrl.getUrl());
            }
        };
    }
    private static Consumer<Set<String>> scopes(List<Scope> scopes) {
        return scopeSet -> {
            for (Scope scope : scopes) {
                scopeSet.add(scope.getScope());
            }
        };
    }
}
