package com.SeyaCloudGestion.GestionSistema.feacture.login.application.dto.response;

import com.SeyaCloudGestion.GestionSistema.common.response.ResponseGeneral;
import com.SeyaCloudGestion.GestionSistema.feacture.login.infraestructure.persistence.model.LoginModel;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ResponseListaLogin extends ResponseGeneral implements Serializable {

    private List<LoginModel> logines;
}