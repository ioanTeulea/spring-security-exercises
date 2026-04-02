package com.example.project.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="token_settings")
@Getter
@Setter
public class TokenSettings {

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private int id;

    @Column(name="access_token_ttl")
    private int accessTokenTTL;

    private String type;

    @OneToOne
    private Client client;

}
