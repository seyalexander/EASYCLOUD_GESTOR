package com.SeyaCloudGestion.GestionSistema.feacture.sucursales.application.dto.response;

import com.SeyaCloudGestion.GestionSistema.common.response.ResponseGeneral;
import com.SeyaCloudGestion.GestionSistema.feacture.sucursales.infraestructure.persistence.model.SucursalesModel;
import lombok.Data;

@Data
public class ResponseDetalleSucursales extends ResponseGeneral {

    private SucursalesModel sucursales;
}