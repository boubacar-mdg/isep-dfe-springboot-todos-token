package com.isep.dfe.auth.services;

import com.isep.dfe.auth.models.dtos.AuthenticationRequest;
import com.isep.dfe.auth.models.dtos.AuthenticationResponse;
import com.isep.dfe.auth.models.dtos.RegistrationRequest;
import com.isep.dfe.users.models.entities.User;


public interface AuthenticationService {
  public AuthenticationResponse register(RegistrationRequest request);
  public AuthenticationResponse authenticate(AuthenticationRequest request);
  public void saveUserToken(User user, String jwtToken);
  public void revokeAllUserTokens(User user);
}
