package com.SeyaCloudGestion.GestionSistema.feacture.rol.application.dto.response;

import com.SeyaCloudGestion.GestionSistema.common.response.ResponseGeneral;
import com.SeyaCloudGestion.GestionSistema.feacture.rol.infraestructure.persistence.model.RolModel;
import lombok.Data;

import java.util.List;

@Data
public class ResponseListaRol extends ResponseGeneral {
    List<RolModel> roles;
}
