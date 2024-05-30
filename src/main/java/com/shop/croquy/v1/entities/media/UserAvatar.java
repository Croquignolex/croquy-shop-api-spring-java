package com.shop.croquy.v1.entities.media;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.shop.croquy.v1.entities.User;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Entity
@NoArgsConstructor
@Table(name = "cs_user_avatars")
public class UserAvatar extends MediaBaseEntity {
    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
}