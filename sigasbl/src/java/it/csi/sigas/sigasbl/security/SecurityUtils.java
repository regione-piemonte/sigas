/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtils {
	public static boolean hasRoles(String[] roles) {
        // get security context from thread local
        SecurityContext context = SecurityContextHolder.getContext();
        if (context == null){
            return false;
        }
        
        org.springframework.security.core.Authentication authentication = context.getAuthentication();
        if (authentication == null){
            return false;
        }
        
        boolean hasRole = false;
        for(String role : roles){
        	hasRole = false;
	        for (GrantedAuthority auth : authentication.getAuthorities()) {
	            if (role.equals(auth.getAuthority())){
	                hasRole = true;
	            }
	        }
	        if(hasRole == false){
	        	return false;
	        }
        }
        
        return true;
    }
}
