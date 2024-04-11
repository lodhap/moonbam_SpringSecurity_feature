package com.example.config;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

public class SecurityUtils {
    public static boolean isAuthenticated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return false;
        }
        return !(authentication instanceof AnonymousAuthenticationToken);
    }

    public static String getUserName() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (isAuthenticated() && authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            return userDetails.getUsername();
        }
        return null;
    }
}

// 참고 인터페이스 구조 
//public interface Authentication extends Principal, Serializable {
//
//	Collection<? extends GrantedAuthority> getAuthorities();
//    
//	Object getCredentials();
//    
//	Object getDetails();
// 
//	Object getPrincipal();
// 
//	boolean isAuthenticated();
//    
//	void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException;
// 
//}