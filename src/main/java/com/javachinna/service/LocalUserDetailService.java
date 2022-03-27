package com.javachinna.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.javachinna.dto.LocalUser;
import com.javachinna.exception.ResourceNotFoundException;
import com.javachinna.model.AppUser;
import com.javachinna.util.GeneralUtils;

/**
 * 
 * @author Chinna
 *
 */
@Service("localUserDetailService")
public class LocalUserDetailService implements UserDetailsService {

	@Autowired
	private UserService userService;

	@Override
	@Transactional
	public LocalUser loadUserByUsername(final String email) throws UsernameNotFoundException {
		AppUser appUser = userService.findUserByEmail(email);
		if (appUser == null) {
			throw new UsernameNotFoundException("User " + email + " was not found in the database");
		}
		return createLocalUser(appUser);
	}

	@Transactional
	public LocalUser loadUserById(Long id) {
		AppUser appUser = userService.findUserById(id).orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
		return createLocalUser(appUser);
	}

	/**
	 * @param appUser
	 * @return
	 */
	private LocalUser createLocalUser(AppUser appUser) {
		return new LocalUser(appUser.getEmail(), appUser.getPassword(), appUser.isEnabled(), true, true, true, GeneralUtils.buildSimpleGrantedAuthorities(appUser.getRoles()), appUser);
	}
}
