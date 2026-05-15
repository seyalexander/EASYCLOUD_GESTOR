package com.SeyaCloudGestion.GestionSistema.feacture.empresa.application.dto.response;

import com.SeyaCloudGestion.GestionSistema.common.response.ResponseGeneral;
import com.SeyaCloudGestion.GestionSistema.feacture.empresa.infraestructure.persistence.model.EmpresaModel;
import lombok.Data;

import java.util.List;

@Data
public class ResponseListaEmpresa extends ResponseGeneral  {
    private List<EmpresaModel> empresas;
}
