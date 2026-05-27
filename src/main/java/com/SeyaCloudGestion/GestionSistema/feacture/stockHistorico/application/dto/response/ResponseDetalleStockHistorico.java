package com.SeyaCloudGestion.GestionSistema.feacture.stockHistorico.application.dto.response;

import com.SeyaCloudGestion.GestionSistema.common.response.ResponseGeneral;
import com.SeyaCloudGestion.GestionSistema.feacture.stockHistorico.infraestructure.persistence.model.StockHistoricoModel;
import lombok.Data;

@Data
public class ResponseDetalleStockHistorico extends ResponseGeneral {

    private StockHistoricoModel stockHistorico;
}