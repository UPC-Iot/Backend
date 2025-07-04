package com.protectify.api.profile.domain.model.aggregates;

import com.protectify.api.iam.domain.model.aggregates.User;
import com.protectify.api.profile.domain.model.commands.CreateProfileCommand;
import com.protectify.api.profile.domain.model.commands.UpdateProfileCommand;
import com.protectify.api.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.*;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Entity
@Getter
public class Profile extends AuditableAbstractAggregateRoot<Profile> {
    @NotNull(message = "First Name is required")
    @NotBlank(message = "First Name cannot be blank")
    private String firstName;
    @NotNull(message = "Last Name is required")
    @NotBlank(message = "Last Name cannot be blank")
    private String lastName;
    @NotNull(message = "Birth Date is required")
    @Past(message = "Birth Date must be in the past")
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDate birthDate;
    private String description;
    private String photo;

    // Dirección con máximo 200 caracteres
    @Size(max = 200, message = "La dirección no puede superar los 200 caracteres")
    private String address;

    // Teléfono con exactamente 9 dígitos
    @Pattern(regexp = "\\d{9}", message = "El teléfono debe tener 9 dígitos")
    private String phone;


    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Profile() {
    }

    public Profile(CreateProfileCommand command, User user){
        this.firstName = command.firstName();
        this.lastName = command.lastName();
        this.birthDate = command.birthDate();
        this.description = command.description();
        this.photo = command.photo();
        this.user = user;
        this.phone = command.phone();
        this.address = command.address();
    }

    public Profile update(UpdateProfileCommand command){
        this.firstName = command.firstName();
        this.lastName = command.lastName();
        this.birthDate = command.birthDate();
        this.description = command.description();
        this.photo = command.photo();
        this.phone = command.phone();
        this.address = command.address();
        return this;
    }

    public Long getUserId() {
        return this.user.getId();
    }


}