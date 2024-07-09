package com.isep.dfe.users.models.dtos;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ChangePasswordOnRecoveryRequest {
    private String email;
    private String newPassword;
    private String confirmationPassword;
}

