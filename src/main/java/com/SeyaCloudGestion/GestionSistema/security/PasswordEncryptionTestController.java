package com.SeyaCloudGestion.GestionSistema.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controlador para pruebas de encriptación y desencriptación
 * Usar solo para desarrollo y pruebas, NO en producción
 */
@Slf4j
@RestController
@RequestMapping("/api/security/test")
public class PasswordEncryptionTestController {

    @Autowired
    private PasswordEncryption passwordEncryption;

    /**
     * Endpoint para encriptar una contraseña de prueba
     * GET /api/security/test/encrypt?password=miPassword123
     */
    @GetMapping("/encrypt")
    public String encryptTest(@RequestParam String password) {
        try {
            String encrypted = passwordEncryption.encrypt(password);
            log.info("Contraseña encriptada: {}", encrypted);
            return "{\"original\": \"" + password + "\", \"encrypted\": \"" + encrypted + "\"}";
        } catch (Exception e) {
            return "{\"error\": \"" + e.getMessage() + "\"}";
        }
    }

    /**
     * Endpoint para desencriptar una contraseña de prueba
     * GET /api/security/test/decrypt?encrypted=...
     */
    @GetMapping("/decrypt")
    public String decryptTest(@RequestParam String encrypted) {
        try {
            String decrypted = passwordEncryption.decrypt(encrypted);
            log.info("Contraseña desencriptada: {}", decrypted);
            return "{\"encrypted\": \"" + encrypted + "\", \"decrypted\": \"" + decrypted + "\"}";
        } catch (Exception e) {
            return "{\"error\": \"" + e.getMessage() + "\"}";
        }
    }

    /**
     * Obtener la semilla actual (solo para verificación)
     * GET /api/security/test/seed
     */
    @GetMapping("/seed")
    public String getSeed() {
        return "{\"seed\": \"" + passwordEncryption.getCurrentSeed() + "\"}";
    }
}
