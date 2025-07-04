package com.protectify.api.profile.domain.model.entities;

import com.protectify.api.iam.domain.model.aggregates.User;
import com.protectify.api.profile.domain.model.commands.CreateNotificationCommand;
import com.protectify.api.profile.domain.model.commands.UpdateNotificationCommand;
import com.protectify.api.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Entity
@Getter
public class Notification extends AuditableAbstractAggregateRoot<Notification> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "El título es requerido")
    @NotBlank(message = "El título no puede estar vacío")
    private String title;

    @NotNull(message = "El mensaje es requerido")
    @NotBlank(message = "El mensaje no puede estar vacío")
    private String message;

    @NotNull(message = "La fecha es requerida")
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDateTime date;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Notification() {
    }

    public Notification(CreateNotificationCommand command, User user) {
        this.title = command.title();
        this.message = command.message();
        this.date = command.date();
        this.user = user;
    }

    public Notification update(UpdateNotificationCommand command) {
        this.title = command.title();
        this.message = command.message();
        this.date = command.date();
        return this;
    }

    public Long getUserId() {
        return this.user.getId();
    }
}