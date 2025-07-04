package com.protectify.api.profile.application.internal.commandservices;

import com.protectify.api.iam.domain.model.aggregates.User;
import com.protectify.api.profile.domain.exceptions.OwnerNotFoundException;
import com.protectify.api.profile.domain.exceptions.UserNotFoundException;
import com.protectify.api.profile.domain.model.commands.CreateOwnerCommand;
import com.protectify.api.profile.domain.model.commands.DeleteOwnerCommand;
import com.protectify.api.profile.domain.model.entities.Owner;
import com.protectify.api.profile.domain.services.OwnerCommandService;
import com.protectify.api.profile.infrastructure.persistence.jpa.repositories.OwnerRepository;
import org.springframework.stereotype.Service;

@Service
public class OwnerCommandServiceImpl implements OwnerCommandService {
    private final OwnerRepository ownerRepository;

    public OwnerCommandServiceImpl(OwnerRepository ownerRepository) {
        this.ownerRepository = ownerRepository;
    }

    @Override
    public Long handle(CreateOwnerCommand command, User user) {
        var sameUser = ownerRepository.findByUser_Id(command.userId());
        if (sameUser.isPresent()) {
            throw new UserNotFoundException(command.userId());
        }
        var owner = new Owner(command, user);
        ownerRepository.save(owner);
        return owner.getId();
    }

    @Override
    public void handle(DeleteOwnerCommand command) {
        var owner = ownerRepository.findById(command.id());
        if (owner.isEmpty()) {
            throw new OwnerNotFoundException(command.id());
        }
        ownerRepository.delete(owner.get());
    }
}