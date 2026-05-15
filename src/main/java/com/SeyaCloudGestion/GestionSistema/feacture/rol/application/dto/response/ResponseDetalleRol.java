package com.SeyaCloudGestion.GestionSistema.feacture.rol.application.dto.response;

import com.SeyaCloudGestion.GestionSistema.common.response.ResponseGeneral;
import com.SeyaCloudGestion.GestionSistema.feacture.rol.infraestructure.persistence.model.RolModel;
import lombok.Data;

@Data
public class ResponseDetalleRol extends ResponseGeneral {
    private RolModel rol;
}
