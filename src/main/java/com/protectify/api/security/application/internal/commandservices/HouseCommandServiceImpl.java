package com.protectify.api.security.application.internal.commandservices;

import com.protectify.api.security.domain.exceptions.HouseNotFoundException;
import com.protectify.api.security.domain.exceptions.SameHouseException;
import com.protectify.api.security.domain.model.aggregates.House;
import com.protectify.api.security.domain.model.commands.CreateHouseCommand;
import com.protectify.api.security.domain.model.commands.UpdateHouseCommand;
import com.protectify.api.security.domain.model.commands.DeleteHouseCommand;
import com.protectify.api.security.domain.services.HouseCommandService;
import com.protectify.api.security.infrastructure.persistence.jpa.repositories.HouseRepository;
import com.protectify.api.profile.domain.model.entities.Owner;
import com.protectify.api.profile.infrastructure.persistence.jpa.repositories.OwnerRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class HouseCommandServiceImpl implements HouseCommandService {
    private final HouseRepository houseRepository;
    private final OwnerRepository ownerRepository;

    public HouseCommandServiceImpl(HouseRepository houseRepository, OwnerRepository ownerRepository) {
        this.houseRepository = houseRepository;
        this.ownerRepository = ownerRepository;
    }

    @Override
    public Long handle(CreateHouseCommand command) {
        Optional<Owner> owner = ownerRepository.findById(command.ownerId());
        if (owner.isEmpty()) {
            throw new RuntimeException("Owner not found with id " + command.ownerId());
        }
        // Lógica para evitar casas duplicadas (ajusta según tu criterio)
        var existingHouses = houseRepository.findByOwner_Id(command.ownerId());
        boolean exists = existingHouses.stream()
                .anyMatch(h -> h.getAddress().equals(command.address()) && h.getName().equals(command.name()));
        if (exists) {
            throw new SameHouseException(command.ownerId());
        }
        House house = new House(command, owner.get());
        houseRepository.save(house);
        return house.getId();
    }

    @Override
    public Optional<House> handle(UpdateHouseCommand command) {
        var house = houseRepository.findById(command.id());
        if (house.isEmpty()) {
            return Optional.empty();
        }
        House updatedHouse = houseRepository.save(house.get().update(command));
        return Optional.of(updatedHouse);
    }

    @Override
    public void handle(DeleteHouseCommand command) {
        var house = houseRepository.findById(command.id());
        if (house.isEmpty()) {
            throw new HouseNotFoundException(command.id());
        }
        houseRepository.delete(house.get());
    }
}