package com.example.project.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.settings.OAuth2TokenFormat;

import java.time.Duration;
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

    @OneToOne(mappedBy = "client", cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private TokenSettings tokenSettings;

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
                        .withId(String.valueOf(client.getId())) //imperative to set id as string as RegisteredClient requires it
                        .clientId(client.getClientId()) //imperative to set client id as string as RegisteredClient requires it
                        .clientSecret(client.getSecret())
                        .clientAuthenticationMethods(authenticationMethods(client.getAuthenticationMethods())) //imperative to set authentication methods as string as RegisteredClient requires it
                        .authorizationGrantTypes(authorizationGrantType(client.getGrantType())) //imperative to set grant types as string as RegisteredClient requires it
                        .redirectUris(redirectUrls(client.getRedirectUrls())) //imperatrive to set redirect urls as string as RegisteredClient requires it
                        .scopes(scopes(client.getScopes()))
                        .tokenSettings(org.springframework.security.oauth2.server.authorization.settings.TokenSettings
                                .builder()
                                .accessTokenTimeToLive(Duration.ofHours(client.getTokenSettings().getAccessTokenTTL()))
                                .accessTokenFormat(new OAuth2TokenFormat(client.getTokenSettings().getType()))
                                .build())
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
