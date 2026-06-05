package com.SeyaCloudGestion.GestionSistema.feacture.login.application.dto.response;

import com.SeyaCloudGestion.GestionSistema.common.response.ResponseGeneral;
import com.SeyaCloudGestion.GestionSistema.feacture.login.infraestructure.persistence.model.LoginModel;
import lombok.Data;

@Data
public class ResponseDetalleLogin extends ResponseGeneral {

    private LoginModel login;
}