package com.example.project.services;

import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NameService {

    @PreAuthorize("#username == authentication.name")
    public String getName(String username) {
        return username;
    }

    @PostAuthorize("returnObject == authentication.name or hasAuthority('READ')")
    public String getDetails(String username) {
        return username;
    }
}
