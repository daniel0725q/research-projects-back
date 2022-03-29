package com.quinterodaniel.researchprojects.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.quinterodaniel.researchprojects.model.AppUser;
import com.quinterodaniel.researchprojects.model.Role;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.quinterodaniel.researchprojects.dto.LocalUser;
import com.quinterodaniel.researchprojects.dto.SocialProvider;
import com.quinterodaniel.researchprojects.dto.UserInfo;

/**
 * 
 * @author Chinna
 *
 */
public class GeneralUtils {

	public static List<SimpleGrantedAuthority> buildSimpleGrantedAuthorities(final Set<Role> roles) {
		List<SimpleGrantedAuthority> authorities = new ArrayList<>();
		for (Role role : roles) {
			authorities.add(new SimpleGrantedAuthority(role.getName()));
		}
		return authorities;
	}

	public static SocialProvider toSocialProvider(String providerId) {
		for (SocialProvider socialProvider : SocialProvider.values()) {
			if (socialProvider.getProviderType().equals(providerId)) {
				return socialProvider;
			}
		}
		return SocialProvider.LOCAL;
	}

	public static UserInfo buildUserInfo(LocalUser localUser) {
		List<String> roles = localUser.getAuthorities().stream().map(item -> item.getAuthority()).collect(Collectors.toList());
		AppUser appUser = localUser.getUser();
		return new UserInfo(appUser.getId().toString(), appUser.getDisplayName(), appUser.getEmail(), roles);
	}
}
