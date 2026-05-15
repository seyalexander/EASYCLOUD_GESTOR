package com.SeyaCloudGestion.GestionSistema.feacture.usuarios.domain.services;

import com.SeyaCloudGestion.GestionSistema.security.PasswordEncryption;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

/**
 * Servicio de encriptación y desencriptación de contraseñas
 * Centraliza la lógica de manejo de contraseñas en la aplicación usando BCrypt
 * 
 * El salt se genera automáticamente con cada encriptación y se incluye en el
 * hash resultante.
 * Formato del hash BCrypt:
 * $2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcg7b3XeKeUxWdeS86E36P4/lLm
 * - $2a$ = Algoritmo BCrypt
 * - 10 = Número de rondas (strength)
 * - N9qo8uLOickgx2ZMRZoMy = Salt (22 caracteres)
 * - eIjZAgcg7b3XeKeUxWdeS86E36P4/lLm = Hash
 */
@Service
@Slf4j
public class EncriptacionService {

    @Value("${encriptacion.password.min-length:8}")
    private int minLength;

    @Value("${encriptacion.password.require-uppercase:true}")
    private boolean requireUppercase;

    @Value("${encriptacion.password.require-lowercase:true}")
    private boolean requireLowercase;

    @Value("${encriptacion.password.require-numbers:true}")
    private boolean requireNumbers;

    @Autowired
    private PasswordEncryption passwordEncryption;

    public EncriptacionService(PasswordEncryption passwordEncryption) {
        this.passwordEncryption = passwordEncryption;
    }

    public String encriptarContraseña(String contraseniaPlana) {
        if (contraseniaPlana == null || contraseniaPlana.trim().isEmpty()) {
            log.error("Intento de encriptación con contraseña vacía");
            throw new IllegalArgumentException("La contraseña no puede estar vacía");
        }

        try {
            String contraseniaEncriptada = passwordEncryption.encrypt(contraseniaPlana);
            log.debug("Contraseña encriptada exitosamente con BCrypt (salt incluido en hash)");
            return contraseniaEncriptada;
        } catch (Exception e) {
            log.error("Error al encriptar la contraseña: {}", e.getMessage());
            throw new RuntimeException("Error al procesar la contraseña: " + e.getMessage());
        }
    }


    public String decrypt(String encryptedPassword) {
        try {

            if (encryptedPassword == null) {
                throw new IllegalArgumentException("La contraseña encriptada no puede ser null");
            }

            if (encryptedPassword.trim().isEmpty()) {
                return encryptedPassword;
            }

            String passwordEncriptada = passwordEncryption.encrypt(encryptedPassword);
            return passwordEncriptada;

        } catch (IllegalArgumentException e) {
            // Errores de validación
            log.warn("[decrypt] Validación fallida: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            // Errores criptográficos
            // Probables causas: semilla incorrecta, datos corrompidos, etc
            log.error("[decrypt] Error crítico al desencriptar: {}", e.getMessage(), e);
            throw new RuntimeException("Error al desencriptar la contraseña: " + e.getMessage());
        }
    }


    public boolean validarFortalezaContrasenia(String contrasenia) {
        if (contrasenia == null || contrasenia.trim().isEmpty()) {
            log.warn("Contraseña vacía");
            return false;
        }

        if (contrasenia.length() < minLength) {
            log.warn("Contraseña no cumple requisito de longitud mínima ({} caracteres)", minLength);
            return false;
        }

        if (requireUppercase && !contrasenia.matches(".*[A-Z].*")) {
            log.warn("Contraseña no contiene mayúsculas");
            return false;
        }

        if (requireLowercase && !contrasenia.matches(".*[a-z].*")) {
            log.warn("Contraseña no contiene minúsculas");
            return false;
        }

        if (requireNumbers && !contrasenia.matches(".*[0-9].*")) {
            log.warn("Contraseña no contiene números");
            return false;
        }

        log.info("Contraseña cumple con los requisitos de fortaleza configurados");
        return true;
    }
}
