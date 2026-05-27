package com.SeyaCloudGestion.GestionSistema.feacture.tipoMovimientos.application.dto.response;

import com.SeyaCloudGestion.GestionSistema.common.response.ResponseGeneral;
import com.SeyaCloudGestion.GestionSistema.feacture.tipoMovimientos.infraestructure.persistence.model.TipoMovimientoModel;
import lombok.Data;

@Data
public class ResponseDetalleTipoMovimiento extends ResponseGeneral {

    private TipoMovimientoModel tipoMovimiento;
}