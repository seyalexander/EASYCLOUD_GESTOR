package com.SeyaCloudGestion.GestionSistema.feacture.movimientosStock.application.dto.response;

import com.SeyaCloudGestion.GestionSistema.common.response.ResponseGeneral;
import com.SeyaCloudGestion.GestionSistema.feacture.movimientosStock.infraestructure.persistence.model.MovimientoStockModel;
import lombok.Data;

@Data
public class ResponseDetalleMovimientoStock extends ResponseGeneral {

    private MovimientoStockModel movimientoStock;
}