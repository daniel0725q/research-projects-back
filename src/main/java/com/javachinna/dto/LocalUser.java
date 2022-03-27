package com.javachinna.dto;

import java.util.Collection;
import java.util.Map;

import com.javachinna.model.AppUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;

import com.javachinna.util.GeneralUtils;

/**
 * 
 * @author Chinna
 *
 */
public class LocalUser extends User implements OAuth2User, OidcUser {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2845160792248762779L;
	private final OidcIdToken idToken;
	private final OidcUserInfo userInfo;
	private Map<String, Object> attributes;
	private AppUser appUser;

	public LocalUser(final String userID, final String password, final boolean enabled, final boolean accountNonExpired, final boolean credentialsNonExpired,
			final boolean accountNonLocked, final Collection<? extends GrantedAuthority> authorities, final AppUser appUser) {
		this(userID, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities, appUser, null, null);
	}

	public LocalUser(final String userID, final String password, final boolean enabled, final boolean accountNonExpired, final boolean credentialsNonExpired,
					 final boolean accountNonLocked, final Collection<? extends GrantedAuthority> authorities, final AppUser appUser, OidcIdToken idToken,
					 OidcUserInfo userInfo) {
		super(userID, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
		this.appUser = appUser;
		this.idToken = idToken;
		this.userInfo = userInfo;
	}

	public static LocalUser create(AppUser appUser, Map<String, Object> attributes, OidcIdToken idToken, OidcUserInfo userInfo) {
		LocalUser localUser = new LocalUser(appUser.getEmail(), appUser.getPassword(), appUser.isEnabled(), true, true, true, GeneralUtils.buildSimpleGrantedAuthorities(appUser.getRoles()),
				appUser, idToken, userInfo);
		localUser.setAttributes(attributes);
		return localUser;
	}

	public void setAttributes(Map<String, Object> attributes) {
		this.attributes = attributes;
	}

	@Override
	public String getName() {
		return this.appUser.getDisplayName();
	}

	@Override
	public Map<String, Object> getAttributes() {
		return this.attributes;
	}

	@Override
	public Map<String, Object> getClaims() {
		return this.attributes;
	}

	@Override
	public OidcUserInfo getUserInfo() {
		return this.userInfo;
	}

	@Override
	public OidcIdToken getIdToken() {
		return this.idToken;
	}

	public AppUser getUser() {
		return appUser;
	}
}
