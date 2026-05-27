package com.SeyaCloudGestion.GestionSistema.feacture.movimientosStock.application.dto.response;

import com.SeyaCloudGestion.GestionSistema.common.response.ResponseGeneral;
import com.SeyaCloudGestion.GestionSistema.feacture.movimientosStock.infraestructure.persistence.model.MovimientoStockModel;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ResponseListaMovimientoStock extends ResponseGeneral implements Serializable {

    private List<MovimientoStockModel> movimientoStocks;
}