package com.javachinna.service;

import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;

import com.javachinna.model.AppUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.javachinna.dto.LocalUser;
import com.javachinna.dto.SignUpRequest;
import com.javachinna.dto.SocialProvider;
import com.javachinna.exception.OAuth2AuthenticationProcessingException;
import com.javachinna.exception.UserAlreadyExistAuthenticationException;
import com.javachinna.model.Role;
import com.javachinna.repo.RoleRepository;
import com.javachinna.repo.UserRepository;
import com.javachinna.security.oauth2.user.OAuth2UserInfo;
import com.javachinna.security.oauth2.user.OAuth2UserInfoFactory;
import com.javachinna.util.GeneralUtils;

import dev.samstevens.totp.secret.SecretGenerator;

/**
 * @author Chinna
 * @since 26/3/18
 */
@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private SecretGenerator secretGenerator;

	@Override
	@Transactional(value = "transactionManager")
	public AppUser registerNewUser(final SignUpRequest signUpRequest) throws UserAlreadyExistAuthenticationException {
		if (signUpRequest.getUserID() != null && userRepository.existsById(signUpRequest.getUserID())) {
			throw new UserAlreadyExistAuthenticationException("User with User id " + signUpRequest.getUserID() + " already exist");
		} else if (userRepository.existsByEmail(signUpRequest.getEmail())) {
			throw new UserAlreadyExistAuthenticationException("User with email id " + signUpRequest.getEmail() + " already exist");
		}
		AppUser appUser = buildUser(signUpRequest);
		Date now = Calendar.getInstance().getTime();
		appUser.setCreatedDate(now);
		appUser.setModifiedDate(now);
		appUser = userRepository.save(appUser);
		userRepository.flush();
		return appUser;
	}

	private AppUser buildUser(final SignUpRequest formDTO) {
		AppUser appUser = new AppUser();
		appUser.setDisplayName(formDTO.getDisplayName());
		appUser.setEmail(formDTO.getEmail());
		appUser.setPassword(passwordEncoder.encode(formDTO.getPassword()));
		final HashSet<Role> roles = new HashSet<Role>();
		roles.add(roleRepository.findByName(Role.ROLE_USER));
		appUser.setRoles(roles);
		appUser.setProvider(formDTO.getSocialProvider().getProviderType());
		appUser.setEnabled(true);
		appUser.setProviderUserId(formDTO.getProviderUserId());
		if (formDTO.isUsing2FA()) {
			appUser.setUsing2FA(true);
			appUser.setSecret(secretGenerator.generate());
		}
		return appUser;
	}

	@Override
	public AppUser findUserByEmail(final String email) {
		return userRepository.findByEmail(email);
	}

	@Override
	@Transactional
	public LocalUser processUserRegistration(String registrationId, Map<String, Object> attributes, OidcIdToken idToken, OidcUserInfo userInfo) {
		OAuth2UserInfo oAuth2UserInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(registrationId, attributes);
		if (StringUtils.isEmpty(oAuth2UserInfo.getName())) {
			throw new OAuth2AuthenticationProcessingException("Name not found from OAuth2 provider");
		} else if (StringUtils.isEmpty(oAuth2UserInfo.getEmail())) {
			throw new OAuth2AuthenticationProcessingException("Email not found from OAuth2 provider");
		}
		SignUpRequest userDetails = toUserRegistrationObject(registrationId, oAuth2UserInfo);
		AppUser appUser = findUserByEmail(oAuth2UserInfo.getEmail());
		if (appUser != null) {
			if (!appUser.getProvider().equals(registrationId) && !appUser.getProvider().equals(SocialProvider.LOCAL.getProviderType())) {
				throw new OAuth2AuthenticationProcessingException(
						"Looks like you're signed up with " + appUser.getProvider() + " account. Please use your " + appUser.getProvider() + " account to login.");
			}
			appUser = updateExistingUser(appUser, oAuth2UserInfo);
		} else {
			appUser = registerNewUser(userDetails);
		}

		return LocalUser.create(appUser, attributes, idToken, userInfo);
	}

	private AppUser updateExistingUser(AppUser existingAppUser, OAuth2UserInfo oAuth2UserInfo) {
		existingAppUser.setDisplayName(oAuth2UserInfo.getName());
		return userRepository.save(existingAppUser);
	}

	private SignUpRequest toUserRegistrationObject(String registrationId, OAuth2UserInfo oAuth2UserInfo) {
		return SignUpRequest.getBuilder().addProviderUserID(oAuth2UserInfo.getId()).addDisplayName(oAuth2UserInfo.getName()).addEmail(oAuth2UserInfo.getEmail())
				.addSocialProvider(GeneralUtils.toSocialProvider(registrationId)).addPassword("changeit").build();
	}

	@Override
	public Optional<AppUser> findUserById(Long id) {
		return userRepository.findById(id);
	}
}
