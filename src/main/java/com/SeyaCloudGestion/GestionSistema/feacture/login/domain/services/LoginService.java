package com.SeyaCloudGestion.GestionSistema.feacture.login.domain.services;


import com.SeyaCloudGestion.GestionSistema.feacture.login.application.dto.request.RequestLogin;
import com.SeyaCloudGestion.GestionSistema.feacture.login.application.dto.response.ResponseLogin;
import com.SeyaCloudGestion.GestionSistema.feacture.login.domain.intefaces.ILogin;
import com.SeyaCloudGestion.GestionSistema.feacture.login.infraestructura.persistence.LoginRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class LoginService implements ILogin {

    private final LoginRepository loginRepository;

    private LoginService(LoginRepository loginRepository) {
        this.loginRepository = loginRepository;
    }

    @Override
    public ResponseLogin Login(RequestLogin request) {
        return loginRepository.Login(request);
    }
}
