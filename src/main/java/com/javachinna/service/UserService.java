package com.javachinna.service;

import java.util.Map;
import java.util.Optional;

import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;

import com.javachinna.dto.LocalUser;
import com.javachinna.dto.SignUpRequest;
import com.javachinna.exception.UserAlreadyExistAuthenticationException;
import com.javachinna.model.AppUser;

/**
 * @author Chinna
 * @since 26/3/18
 */
public interface UserService {

	public AppUser registerNewUser(SignUpRequest signUpRequest) throws UserAlreadyExistAuthenticationException;

	AppUser findUserByEmail(String email);

	Optional<AppUser> findUserById(Long id);

	LocalUser processUserRegistration(String registrationId, Map<String, Object> attributes, OidcIdToken idToken, OidcUserInfo userInfo);
}
