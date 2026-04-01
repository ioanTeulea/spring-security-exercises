package com.example.project.repositories;

import com.example.project.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, Integer> {

    @Query("""
            SELECT c FROM Client c WHERE c.clientId = :clientId
            
""")
    Optional<Client> findByClientId(String clientId);
}
