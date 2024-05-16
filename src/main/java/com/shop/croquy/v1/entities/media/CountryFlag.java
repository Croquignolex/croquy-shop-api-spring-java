package com.shop.croquy.v1.entities.media;

import com.fasterxml.jackson.annotation.JsonProperty;

import com.shop.croquy.v1.entities.Country;
import com.shop.croquy.v1.entities.User;
import jakarta.persistence.*;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
@Entity
@NoArgsConstructor
@Table(name = "country_flags")
public class CountryFlag {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    protected String id;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(name = "original_name", nullable = false)
    private String originalName;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(name = "size")
    private long size;

    @Column(name = "path", nullable = false, unique = true)
    private String path;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @OneToOne
    @JoinColumn(name = "country_id")
    private Country country;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ManyToOne
    @JoinColumn(name = "creator_id")
    protected User creator;

    @Column(name = "created_at")
    protected Date createdAt = new Date();
}