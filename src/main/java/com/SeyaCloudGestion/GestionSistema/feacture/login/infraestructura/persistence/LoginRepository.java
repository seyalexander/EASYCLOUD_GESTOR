package com.SeyaCloudGestion.GestionSistema.feacture.login.infraestructura.persistence;

import com.SeyaCloudGestion.GestionSistema.feacture.login.application.dto.request.RequestLogin;
import com.SeyaCloudGestion.GestionSistema.feacture.login.application.dto.response.ResponseLogin;
import com.SeyaCloudGestion.GestionSistema.feacture.login.domain.intefaces.ILogin;
import com.SeyaCloudGestion.GestionSistema.security.PasswordEncryption;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.security.Key;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.Date;

@Repository
@Transactional("sqlServerTransactionManager")
@Slf4j
public class LoginRepository implements ILogin {

    @Autowired
    @Qualifier("SQLSERVER")
    private DataSource con;

    @Autowired
    private PasswordEncryption passwordEncryption;

    @Override
    public ResponseLogin Login(RequestLogin request) {
        ResponseLogin usuario = null;

        String SQL = "{ call SEGURIDAD.sp_Login (?, ?) }";

        // Clave secreta para firmar el token (en producción, usar una variable de
        // entorno o config segura)
        final String SECRET_KEY = "easycloudAlexander$2026*_Desarrollo_Sistemas_Area_TI_dmkweb_secret_key_SEYA";
        final long EXPIRATION_TIME = 1000 * 60 * 60 * 4; // 4 horas
        final Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());

        try (Connection conn = con.getConnection();
                CallableStatement pstmt = conn.prepareCall(SQL)) {

            pstmt.setString(1, request.getUsuario());
            String claveEncriptada = passwordEncryption.encrypt(request.getClave());
            pstmt.setString(2, claveEncriptada);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                usuario = new ResponseLogin();
                usuario.setExito(true);
                String idUsuario = rs.getString("idUsuario");
                String nombre = rs.getString("nombre");
                usuario.setNombre(nombre);
                String apellido = rs.getString("apellido");
                usuario.setApellido(apellido);
                long idRol = rs.getLong("idRol");
                usuario.setIdRol(idRol);
                String descripcionRol = rs.getString("descripcionRol");
                usuario.setDescripcionRol(descripcionRol);

                // Generar el token JWT con los datos requeridos
                String token = Jwts.builder()
                        .setSubject(idUsuario)
                        .claim("nombre", nombre)
                        .claim("apellido", apellido)
                        .claim("idUsuario", idUsuario)
                        .setIssuedAt(new Date())
                        .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                        .signWith(key)
                        .compact();
                usuario.setToken(token);
            } else {
                usuario = new ResponseLogin();
                usuario.setExito(false);
                usuario.setMessage("Usuario o contraseña incorrectos");
            }
        } catch (Exception e) {
            log.error("Error al validar credenciales: ", e);
        }

        return usuario;
    }

}
