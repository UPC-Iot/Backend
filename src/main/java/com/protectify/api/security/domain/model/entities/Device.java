// src/main/java/com/protectify/api/security/domain/model/entities/Device.java
package com.protectify.api.security.domain.model.entities;

import com.protectify.api.security.domain.model.aggregates.House;
import com.protectify.api.security.domain.model.commands.CreateDeviceCommand;
import com.protectify.api.security.domain.model.commands.UpdateDeviceCommand;
import com.protectify.api.security.domain.model.valueobjects.DeviceStatus;
import com.protectify.api.security.domain.model.valueobjects.DeviceType;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Device {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "house_id", nullable = false)
    private House house;

    @NotNull
    @NotBlank
    private String name;

    @NotNull
    @Enumerated(EnumType.STRING)
    private DeviceType type;

    @NotNull
    @Pattern(regexp = "^([0-9]{1,3}\\.){3}[0-9]{1,3}$", message = "Invalid IP address")
    private String ipAddress;

    @NotNull
    @Min(1)
    @Max(65535)
    private Integer port;

    @NotNull
    @Enumerated(EnumType.STRING)
    private DeviceStatus status;

    @NotNull
    private Boolean active;

    @NotNull
    @Size(max = 128, message = "API Key cannot exceed 128 characters")
    private String apiKey;

    public Device(CreateDeviceCommand command, House house) {
        this.house = house;
        this.name = command.name();
        this.type = DeviceType.valueOf(command.type());
        this.ipAddress = command.ipAddress();
        this.port = command.port();
        this.status = DeviceStatus.valueOf(command.status().toUpperCase());
        this.active = command.active();
        this.apiKey = command.apiKey();
    }

    public Device update(UpdateDeviceCommand command) {
        this.name = command.name();
        this.type = DeviceType.valueOf(command.type());
        this.ipAddress = command.ipAddress();
        this.port = command.port();
        this.status = DeviceStatus.valueOf(command.status().toUpperCase());
        this.active = command.active();
        this.apiKey = command.apiKey();
        return this;
    }

    public Long getHouseId() {
        return this.house.getId();
    }
}