package com.protectify.api.iam.application.internal.commandservices;

import com.protectify.api.iam.application.internal.outboundservices.acl.ExternalProfileRoleService;
import com.protectify.api.iam.application.internal.outboundservices.hashing.HashingService;
import com.protectify.api.iam.application.internal.outboundservices.tokens.TokenService;
import com.protectify.api.iam.domain.exceptions.RoleNotFoundException;
import com.protectify.api.iam.domain.model.aggregates.User;
import com.protectify.api.iam.domain.model.commands.SignInCommand;
import com.protectify.api.iam.domain.model.commands.SignUpCommand;
import com.protectify.api.iam.domain.services.UserCommandService;
import com.protectify.api.iam.infrastructure.persistence.jpa.repositories.RoleRepository;
import com.protectify.api.iam.infrastructure.persistence.jpa.repositories.UserRepository;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserCommandServiceImpl implements UserCommandService {

    private final UserRepository userRepository;
    private final HashingService hashingService;
    private final TokenService tokenService;
    private final RoleRepository roleRepository;
    private final ExternalProfileRoleService externalProfileRoleService;
    private final ApplicationEventPublisher eventPublisher;

    public UserCommandServiceImpl(UserRepository userRepository,
                                  HashingService hashingService,
                                  TokenService tokenService,
                                  RoleRepository roleRepository,
                                  ExternalProfileRoleService externalProfileRoleService,
                                  ApplicationEventPublisher eventPublisher) {
        this.userRepository = userRepository;
        this.hashingService = hashingService;
        this.tokenService = tokenService;
        this.roleRepository = roleRepository;
        this.externalProfileRoleService = externalProfileRoleService;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public Optional<ImmutablePair<User, String>> handle(SignInCommand command) {
        var user = userRepository.findByUsername(command.username());
        if (user.isEmpty())
            throw new RuntimeException("User not found");
        if (!hashingService.matches(command.password(), user.get().getPassword()))
            throw new RuntimeException("Invalid password");
        var token = tokenService.generateToken(user.get().getUsername());
        return Optional.of(ImmutablePair.of(user.get(), token));
    }

    @Override
    public Optional<User> handle(SignUpCommand command) {
        if (userRepository.existsByUsername(command.username()))
            throw new RuntimeException("Username already exists");
        var roles = command.roles().stream()
                .map(role -> roleRepository.findByName(role.getName())
                        .orElseThrow(() -> new RoleNotFoundException(role.getStringName())))
                .toList();
        var user = new User(command.username(), hashingService.encode(command.password()), roles);
        userRepository.save(user);

        roles.forEach(role -> {
            if (role.getStringName().equals("ROLE_OWNER")) {
                externalProfileRoleService.createOwner(user.getId(), user);
            }
        });
        return userRepository.findByUsername(command.username());
    }
}