package com.quinterodaniel.researchprojects.service;

import com.quinterodaniel.researchprojects.dto.LocalUser;
import com.quinterodaniel.researchprojects.exception.ResourceNotFoundException;
import com.quinterodaniel.researchprojects.model.AppUser;
import com.quinterodaniel.researchprojects.util.GeneralUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
