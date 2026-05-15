package com.SeyaCloudGestion.GestionSistema.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class PasswordSecurityService {

    @Autowired
    private PasswordEncryption passwordEncryption;


    public boolean validarPassword(String passwordPlano, String passwordEncriptada) {
        try {
            if (passwordPlano == null || passwordEncriptada == null) {
                log.warn("[validarPassword] Intento de validación con valores nulos - Seguridad: Rechazado");
                return false;
            }

            String passwordDesencriptada = passwordEncryption.decrypt(passwordEncriptada);
            boolean sonIguales = passwordPlano.equals(passwordDesencriptada);

            if (sonIguales) {
                log.info("[validarPassword] Validación exitosa - Contraseña correcta");
            } else {
                log.warn(
                        "[validarPassword] Validación fallida - Contraseña incorrecta (posible intento de acceso no autorizado)");
            }

            return sonIguales;

        } catch (Exception e) {
            log.error("[validarPassword] Error crítico al validar contraseña: {}", e.getMessage(), e);
            return false;
        }
    }

    public String encriptarPassword(String password) {
        try {
            String passwordEncriptada = passwordEncryption.encrypt(password);
            log.debug("[encriptarPassword] Contraseña encriptada exitosamente");
            return passwordEncriptada;
        } catch (Exception e) {
            log.error("[encriptarPassword] Error durante encriptación: {}", e.getMessage(), e);
            throw new RuntimeException("Error al encriptar la contraseña: " + e.getMessage());
        }
    }

    public String desencriptarPassword(String passwordEncriptada) {
        try {
            return passwordEncryption.decrypt(passwordEncriptada);
        } catch (Exception e) {
            log.error("[desencriptarPassword] Error durante desencriptación: {}", e.getMessage(), e);
            throw new RuntimeException("Error al desencriptar la contraseña: " + e.getMessage());
        }
    }
}
