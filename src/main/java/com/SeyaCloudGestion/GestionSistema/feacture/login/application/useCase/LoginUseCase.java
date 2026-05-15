package com.SeyaCloudGestion.GestionSistema.feacture.login.application.useCase;

import com.SeyaCloudGestion.GestionSistema.feacture.login.application.dto.request.RequestLogin;
import com.SeyaCloudGestion.GestionSistema.feacture.login.application.dto.response.ResponseLogin;
import com.SeyaCloudGestion.GestionSistema.feacture.login.domain.services.LoginService;
import com.SeyaCloudGestion.GestionSistema.feacture.moneda.application.dto.response.ResponseDetalleMoneda;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class LoginUseCase {

    @Autowired
    private final LoginService loginService;

    private LoginUseCase(
            LoginService loginService
    ) {
        this.loginService = loginService;
    }

    public ResponseLogin ejecutar(RequestLogin request) {
        try {
            ResponseLogin response = loginService.Login(request);
            if(response.isExito()){}

            return response;

        }catch (IllegalArgumentException | SecurityException e){
            ResponseLogin response = new ResponseLogin();
            response.setExito(false);
            response.setMessage(e.getMessage());
            return response;
        }
        catch (Exception e){
            String mensajeError = "Error inesperado al ingresar el login: " + e.getMessage();
            System.err.println("[ERROR] " + mensajeError);
            ResponseLogin response = new ResponseLogin();
            response.setExito(false);
            response.setMessage(mensajeError);
            return response;
        }
    }
}
