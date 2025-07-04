package com.protectify.api.security.domain.model.entities;

import com.protectify.api.security.domain.model.aggregates.House;
import com.protectify.api.security.domain.model.commands.CreateAlertCommand;
import com.protectify.api.security.domain.model.commands.UpdateAlertCommand;
import com.protectify.api.security.domain.model.valueobjects.AlertStatus;
import com.protectify.api.security.domain.model.valueobjects.AlertType;
import com.protectify.api.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Getter
public class Alert extends AuditableAbstractAggregateRoot<Alert> {
    @NotNull
    @Enumerated(EnumType.STRING)
    private AlertType type;

    @NotNull
    private String message;

    @NotNull
    @Enumerated(EnumType.STRING)
    private AlertStatus status;

    @NotNull
    private LocalDateTime timestamp;

    @NotNull
    private String imageUrl;

    @ManyToOne
    @JoinColumn(name = "house_id", nullable = false)
    private House house;

    public Alert() {
    }

    public Alert(CreateAlertCommand command, House house) {
        this.type = AlertType.valueOf(command.type().toUpperCase());
        this.message = command.message();
        this.status = AlertStatus.valueOf(command.status().toUpperCase());
        this.timestamp = command.timestamp();
        this.imageUrl = command.imageUrl();
        this.house = house;
    }

    public Alert update(UpdateAlertCommand command) {
        this.type = AlertType.valueOf(command.type().toUpperCase());
        this.message = command.message();
        this.status = AlertStatus.valueOf(command.status().toUpperCase());
        this.timestamp = command.timestamp();
        this.imageUrl = command.imageUrl();
        return this;
    }

    public Long getHouseId() {
        return this.house.getId();
    }
}