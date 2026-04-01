package com.example.project.services;

import com.example.project.entities.*;
import com.example.project.repositories.ClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.stream.Collectors;


@Service
@Transactional(readOnly = true)
public class ClientService implements RegisteredClientRepository {

    private final ClientRepository clientRepository;

    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    @Transactional
    public void save(RegisteredClient registeredClient) {
        Client c=new Client();
        c.setClientId(registeredClient.getClientId());
        c.setSecret(registeredClient.getClientSecret());
        c.setAuthenticationMethods(
                registeredClient.getClientAuthenticationMethods()
                        .stream()
                        .map(a->AuthenticationMethod.from(a,c))
                        .collect(Collectors.toList())
        );
        c.setGrantType(registeredClient.getAuthorizationGrantTypes()
                .stream()
                .map(authorizationGrantType -> GrantType.from(authorizationGrantType,c))
                .collect(Collectors.toList())
          );
        c.setRedirectUrls(registeredClient.getRedirectUris()
                .stream()
                .map(redirectUri-> RedirectUrl.from(redirectUri,c))
                .collect(Collectors.toList())
        );
        c.setScopes(registeredClient.getScopes()
                .stream()
                .map(scope-> Scope.from(scope,c))
                .collect(Collectors.toList()));
        clientRepository.save(c);
    }

    @Override
    public RegisteredClient findById(String id) {
        Optional<Client> client=clientRepository.findById(Integer.parseInt(id));
        client.map(Client::mapToRegisteredClient)
                .orElseThrow(()->new RuntimeException("Client not found"));
        return null;

    }

    @Override
    public RegisteredClient findByClientId(String clientId) {
        Optional<Client> client=clientRepository.findByClientId(clientId);
        client.map(Client::mapToRegisteredClient)
                .orElseThrow(()->new RuntimeException("Client not found"));
        return null;
    }
}
