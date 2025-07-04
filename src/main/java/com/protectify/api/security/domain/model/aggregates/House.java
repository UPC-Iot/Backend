package com.protectify.api.security.domain.model.aggregates;

import com.protectify.api.security.domain.model.commands.CreateHouseCommand;
import com.protectify.api.security.domain.model.commands.UpdateHouseCommand;
import com.protectify.api.profile.domain.model.entities.Owner;
import com.protectify.api.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.*;
import lombok.Getter;

@Entity
@Getter
public class House extends AuditableAbstractAggregateRoot<House> {
    @NotNull(message = "La dirección es obligatoria")
    @Size(max = 200, message = "La dirección no puede superar los 200 caracteres")
    private String address;

    @NotNull(message = "El nombre es obligatorio")
    @NotBlank(message = "El nombre no puede estar vacío")
    private String name;

    private String description;

    @OneToOne
    @JoinColumn(name = "owner_id")
    private Owner owner;

    public House() {
    }

    public House(CreateHouseCommand command, Owner owner) {
        this.address = command.address();
        this.name = command.name();
        this.description = command.description();
        this.owner = owner;
    }

    public House update(UpdateHouseCommand command) {
        this.address = command.address();
        this.name = command.name();
        this.description = command.description();
        return this;
    }

    public Long getOwnerId() {
        return this.owner.getId();
    }
}