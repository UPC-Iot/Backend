package com.protectify.api.iam.domain.model.queries;

import com.protectify.api.iam.domain.model.valueobjects.Roles;

public record GetRoleByNameQuery(Roles name) {
}