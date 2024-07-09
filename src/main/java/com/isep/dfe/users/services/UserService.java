package com.isep.dfe.users.services;

import java.security.Principal;

import com.isep.dfe.users.models.dtos.ChangePasswordRequest;
import com.isep.dfe.users.models.dtos.ChangePasswordResponse;

public interface UserService {
    public ChangePasswordResponse changePassword(ChangePasswordRequest request, Principal connectedUser);
}
