package com.SeyaCloudGestion.GestionSistema.utils;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtils {
    public static long getCurrentUserId() {

        Authentication authentication = SecurityContextHolder
                .getContext()
                .getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new RuntimeException("Usuario no autenticado");
        }

        Object principal = authentication.getPrincipal();

        if (!(principal instanceof Long id)) {
            throw new RuntimeException("Principal inválido");
        }

        return id;
    }

}
