package com.isep.dfe.users.models.dtos;

import java.math.BigDecimal;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserResponse {
    private String fullName;
    private String phoneNumber;
    private String email;
    private String address;
    private String avatar;
    private BigDecimal balance;
    private String firebaseToken;
    private Long fidelity;
}
