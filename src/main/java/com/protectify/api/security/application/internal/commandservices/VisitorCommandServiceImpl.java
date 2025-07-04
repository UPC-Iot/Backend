package com.protectify.api.security.application.internal.commandservices;

import com.protectify.api.security.application.internal.outboundservices.acl.ExternalProfilesService;
import com.protectify.api.security.domain.model.commands.CreateVisitorCommand;
import com.protectify.api.security.domain.model.commands.UpdateVisitorCommand;
import com.protectify.api.security.domain.model.commands.DeleteVisitorCommand;
import com.protectify.api.security.domain.model.entities.Visitor;
import com.protectify.api.security.domain.services.VisitorCommandService;
import com.protectify.api.security.infrastructure.persistence.jpa.repositories.VisitorRepository;
import com.protectify.api.security.domain.model.aggregates.House;
import com.protectify.api.security.infrastructure.persistence.jpa.repositories.HouseRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class VisitorCommandServiceImpl implements VisitorCommandService {
    private final VisitorRepository visitorRepository;
    private final HouseRepository houseRepository;

    public VisitorCommandServiceImpl(VisitorRepository visitorRepository, HouseRepository houseRepository) {
        this.visitorRepository = visitorRepository;
        this.houseRepository = houseRepository;
    }

    @Override
    public Long handle(CreateVisitorCommand command) {
        Optional<House> house = houseRepository.findById(command.houseId());
        if (house.isEmpty()) {
            throw new IllegalArgumentException("La casa con id " + command.houseId() + " no existe");
        }
        Visitor visitor = new Visitor(command, house.get());
        visitorRepository.save(visitor);
        return visitor.getId();
    }

    @Override
    public Optional<Visitor> handle(UpdateVisitorCommand command) {
        var visitor = visitorRepository.findById(command.id());
        if (visitor.isEmpty()) {
            return Optional.empty();
        }
        Visitor updatedVisitor = visitorRepository.save(visitor.get().update(command));
        return Optional.of(updatedVisitor);
    }

    @Override
    public void handle(DeleteVisitorCommand command) {
        var visitor = visitorRepository.findById(command.id());
        if (visitor.isEmpty()) {
            throw new IllegalArgumentException("El visitante con id " + command.id() + " no existe");
        }
        visitorRepository.delete(visitor.get());
    }
}