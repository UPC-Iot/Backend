package com.protectify.api.security.domain.services;

import com.protectify.api.security.domain.model.commands.CreateVisitorCommand;
import com.protectify.api.security.domain.model.commands.UpdateVisitorCommand;
import com.protectify.api.security.domain.model.commands.DeleteVisitorCommand;
import com.protectify.api.security.domain.model.entities.Visitor;

import java.util.Optional;

public interface VisitorCommandService {
    Long handle(CreateVisitorCommand command);
    Optional<Visitor> handle(UpdateVisitorCommand command);
    void handle(DeleteVisitorCommand command);
}