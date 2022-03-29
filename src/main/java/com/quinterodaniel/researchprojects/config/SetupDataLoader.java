package com.quinterodaniel.researchprojects.config;

import java.util.Calendar;
import java.util.Date;
import java.util.Set;

import com.quinterodaniel.researchprojects.dto.SocialProvider;
import com.quinterodaniel.researchprojects.model.AppUser;
import com.quinterodaniel.researchprojects.model.Role;
import com.quinterodaniel.researchprojects.repo.RoleRepository;
import com.quinterodaniel.researchprojects.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {

	private boolean alreadySetup = false;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	@Transactional
	public void onApplicationEvent(final ContextRefreshedEvent event) {
		if (alreadySetup) {
			return;
		}
		// Create initial roles
		Role studentRole = createRoleIfNotFound(Role.ROLE_STUDENT);
		Role adminRole = createRoleIfNotFound(Role.ROLE_ADMIN);
		Role professorRole = createRoleIfNotFound(Role.ROLE_PROFESSOR);
		createUserIfNotFound("admin@javachinna.com", Set.of(studentRole, adminRole, professorRole));
		alreadySetup = true;
	}

	// @Transactional
	private final AppUser createUserIfNotFound(final String email, Set<Role> roles) {
		AppUser appUser = userRepository.findByEmail(email);
		if (appUser == null) {
			appUser = new AppUser();
			appUser.setDisplayName("Admin");
			appUser.setEmail(email);
			appUser.setPassword(passwordEncoder.encode("admin@"));
			appUser.setRoles(roles);
			appUser.setProvider(SocialProvider.LOCAL.getProviderType());
			appUser.setEnabled(true);
			Date now = Calendar.getInstance().getTime();
			appUser.setCreatedDate(now);
			appUser.setModifiedDate(now);
			appUser = userRepository.save(appUser);
		}
		return appUser;
	}

	// @Transactional
	private final Role createRoleIfNotFound(final String name) {
		Role role = roleRepository.findByName(name);
		if (role == null) {
			role = roleRepository.save(new Role(name));
		}
		return role;
	}
}