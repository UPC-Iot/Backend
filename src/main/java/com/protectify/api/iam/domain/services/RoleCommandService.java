package com.protectify.api.iam.domain.services;

import com.protectify.api.iam.domain.model.commands.SeedRolesCommand;

public interface RoleCommandService {
    void handle(SeedRolesCommand command);
}