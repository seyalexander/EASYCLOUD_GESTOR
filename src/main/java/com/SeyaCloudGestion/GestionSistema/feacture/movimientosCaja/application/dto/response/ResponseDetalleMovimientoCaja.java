package com.SeyaCloudGestion.GestionSistema.feacture.movimientosCaja.application.dto.response;

import com.SeyaCloudGestion.GestionSistema.common.response.ResponseGeneral;
import com.SeyaCloudGestion.GestionSistema.feacture.movimientosCaja.infraestructure.persistence.model.MovimientoCajaModel;
import lombok.Data;

@Data
public class ResponseDetalleMovimientoCaja extends ResponseGeneral {

    private MovimientoCajaModel movimientoCaja;
}