package com.kanchan.ShortenUrl.model;


import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class ShortUrl {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;

    @Column(unique = true)
    private String shorterUrl;

    @Column(unique = true)
    private String originalUrl;

}