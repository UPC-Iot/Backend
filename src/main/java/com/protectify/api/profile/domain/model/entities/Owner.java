package com.protectify.api.profile.domain.model.entities;

import com.protectify.api.iam.domain.model.aggregates.User;
import com.protectify.api.profile.domain.model.commands.CreateOwnerCommand;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class Owner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Owner() {
    }

    public Owner(CreateOwnerCommand command, User user) {
        this.user = user;
    }

    public Long getUserId() {
        return user.getId();
    }
}