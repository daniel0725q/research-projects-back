package com.quinterodaniel.researchprojects.service;

import java.util.Map;
import java.util.Optional;

import com.quinterodaniel.researchprojects.dto.LocalUser;
import com.quinterodaniel.researchprojects.dto.SignUpRequest;
import com.quinterodaniel.researchprojects.exception.UserAlreadyExistAuthenticationException;
import com.quinterodaniel.researchprojects.model.AppUser;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;

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
