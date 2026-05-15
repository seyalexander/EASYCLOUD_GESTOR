package com.SeyaCloudGestion.GestionSistema.feacture.usuarios.application.dto.response;

import com.SeyaCloudGestion.GestionSistema.common.response.ResponseGeneral;
import com.SeyaCloudGestion.GestionSistema.feacture.usuarios.infraestructure.persistence.model.UsuariosModel;
import lombok.Data;

@Data
public class ResponseDetalleUsuario extends ResponseGeneral {
    UsuariosModel usuario;
}
