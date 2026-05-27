package com.SeyaCloudGestion.GestionSistema.feacture.cuentasPorCobrar.application.dto.response;

import com.SeyaCloudGestion.GestionSistema.common.response.ResponseGeneral;
import com.SeyaCloudGestion.GestionSistema.feacture.cuentasPorCobrar.infraestructure.persistence.model.CuentasPorCobrarModel;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ResponseListaCuentasPorCobrar extends ResponseGeneral implements Serializable {

    private List<CuentasPorCobrarModel> cuentasPorCobrares;
}