package com.protectify.api.security.domain.model.entities;

import com.protectify.api.security.domain.model.aggregates.House;
import com.protectify.api.security.domain.model.commands.CreateVisitorCommand;
import com.protectify.api.security.domain.model.commands.UpdateVisitorCommand;
import com.protectify.api.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;

import java.time.LocalDate;

@Entity
@Getter
public class Visitor extends AuditableAbstractAggregateRoot<Visitor> {
    @NotNull
    @Size(max = 100)
    private String firstname;

    @NotNull
    @Size(max = 100)
    private String lastname;

    @NotNull
    private String photo;

    @NotNull
    private String role;

    private LocalDate lastVisit;

    @ManyToOne
    @JoinColumn(name = "house_id", nullable = false)
    private House house;

    public Visitor() {
    }

    public Visitor(CreateVisitorCommand command, House house) {
        this.firstname = command.firstname();
        this.lastname = command.lastname();
        this.photo = command.photo();
        this.role = command.role();
        this.lastVisit = command.lastVisit();
        this.house = house;
    }

    public Visitor update(UpdateVisitorCommand command) {
        this.firstname = command.firstname();
        this.lastname = command.lastname();
        this.photo = command.photo();
        this.role = command.role();
        this.lastVisit = command.lastVisit();
        return this;
    }

    public Long getHouseId() {
        return this.house.getId();
    }
}