package com.SeyaCloudGestion.GestionSistema.feacture.movimientosCaja.application.dto.response;

import com.SeyaCloudGestion.GestionSistema.common.response.ResponseGeneral;
import com.SeyaCloudGestion.GestionSistema.feacture.movimientosCaja.infraestructure.persistence.model.MovimientoCajaModel;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ResponseListaMovimientoCaja extends ResponseGeneral implements Serializable {

    private List<MovimientoCajaModel> movimientoCajas;
}