package com.SeyaCloudGestion.GestionSistema.feacture.usuarios.application.dto.response;

import com.SeyaCloudGestion.GestionSistema.common.response.ResponseGeneral;
import com.SeyaCloudGestion.GestionSistema.feacture.usuarios.infraestructure.persistence.model.UsuariosModel;
import lombok.Data;

import java.util.List;

@Data
public class ResponseListaUsuario extends ResponseGeneral {
    List<UsuariosModel> usuarios;
}
