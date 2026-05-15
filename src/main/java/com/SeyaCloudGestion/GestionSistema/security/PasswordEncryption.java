package com.SeyaCloudGestion.GestionSistema.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;


@Slf4j
@Component
public class PasswordEncryption {

    private static final String ALGORITHM = "AES";
    private static final int KEY_SIZE = 256;


    @Value("${app.security.password-seed}")
    private String passwordSeed;

    public String encrypt(String passwordToEncrypt) {
        try {
            SecretKey secretKey = generateKeyFromSeed(passwordSeed);
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);

            byte[] encryptedPassword = cipher.doFinal(passwordToEncrypt.getBytes());
            return Base64.getEncoder().encodeToString(encryptedPassword);

        } catch (IllegalArgumentException e) {
            log.warn("[encrypt] Validación fallida: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("[encrypt] Error crítico al encriptar: {}", e.getMessage(), e);
            throw new RuntimeException("Error al encriptar la contraseña: " + e.getMessage());
        }
    }

    public String decrypt(String encryptedPassword) {
        try {

            if (encryptedPassword == null || encryptedPassword.trim().isEmpty()) {
                return "";
            }

            SecretKey secretKey = generateKeyFromSeed(passwordSeed);
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, secretKey);

            byte[] decodedPassword = Base64.getDecoder().decode(encryptedPassword);
            byte[] decryptedPassword = cipher.doFinal(decodedPassword);

            return new String(decryptedPassword);

        } catch (IllegalArgumentException e) {
            log.warn("[decrypt] Validación fallida: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("[decrypt] Error crítico al desencriptar: {}", e.getMessage(), e);
            throw new RuntimeException("Error al desencriptar la contraseña: " + e.getMessage());
        }
    }


    private SecretKey generateKeyFromSeed(String seed) throws Exception {
        byte[] seedBytes = new byte[32];
        byte[] seedData = seed.getBytes();
        System.arraycopy(seedData, 0, seedBytes, 0, Math.min(seedData.length, seedBytes.length));
        return new SecretKeySpec(seedBytes, 0, seedBytes.length, ALGORITHM);
    }

    public String getCurrentSeed() {
        return passwordSeed;
    }
}
