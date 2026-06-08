package com.SeyaCloudGestion.GestionSistema.feacture.cuentasPorPagar.application.dto.response;

import com.SeyaCloudGestion.GestionSistema.common.response.ResponseGeneral;
import com.SeyaCloudGestion.GestionSistema.feacture.cuentasPorPagar.infraestructure.persistence.model.CuentasPorPagarModel;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ResponseListaCuentasPorPagar extends ResponseGeneral implements Serializable {

    private List<CuentasPorPagarModel> cuentasPorPagares;
}