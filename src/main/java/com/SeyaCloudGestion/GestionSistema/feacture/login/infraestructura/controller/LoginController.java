package com.SeyaCloudGestion.GestionSistema.feacture.login.infraestructura.controller;

import com.SeyaCloudGestion.GestionSistema.feacture.login.application.dto.request.RequestLogin;
import com.SeyaCloudGestion.GestionSistema.feacture.login.application.dto.response.ResponseLogin;
import com.SeyaCloudGestion.GestionSistema.feacture.login.application.useCase.LoginUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class LoginController {

    @Autowired
    private LoginUseCase loginUseCase;

    @PostMapping("/login")
    public ResponseLogin login(@RequestBody RequestLogin request) {
        return loginUseCase.ejecutar(request);
    }
}
