package com.SeyaCloudGestion.GestionSistema.feacture.empresa.application.dto.response;

import com.SeyaCloudGestion.GestionSistema.common.response.ResponseGeneral;
import com.SeyaCloudGestion.GestionSistema.feacture.empresa.infraestructure.persistence.model.EmpresaModel;
import lombok.Data;

@Data
public class ResponseDetalleEmpresa extends ResponseGeneral {
    EmpresaModel empresa;
}
