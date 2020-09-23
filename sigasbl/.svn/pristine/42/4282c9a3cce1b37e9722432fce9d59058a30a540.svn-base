/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import it.csi.sigas.sigasbl.integration.iride.Application;
import it.csi.sigas.sigasbl.integration.iride.Identita;

public class UserDetails extends User {

	private static final long serialVersionUID = -7408080353311998574L;

	private Identita identita;
	private Application application;

	public UserDetails(String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked,
			Collection<? extends GrantedAuthority> authorities, Identita identita, Application application) {
		super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
		this.identita = identita;
		this.application = application;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((application == null) ? 0 : application.hashCode());
		result = prime * result + ((identita == null) ? 0 : identita.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserDetails other = (UserDetails) obj;
		if (application == null) {
			if (other.application != null)
				return false;
		} else if (!application.equals(other.application))
			return false;
		if (identita == null) {
			if (other.identita != null)
				return false;
		} else if (!identita.equals(other.identita))
			return false;

		return true;
	}

	public Identita getIdentita() {
		return identita;
	}

	public void setIdentita(Identita identita) {
		this.identita = identita;
	}

	public Application getApplication() {
		return application;
	}

	public void setApplication(Application application) {
		this.application = application;
	}

}
