package com.SeyaCloudGestion.GestionSistema.feacture.stockHistorico.application.dto.response;

import com.SeyaCloudGestion.GestionSistema.common.response.ResponseGeneral;
import com.SeyaCloudGestion.GestionSistema.feacture.stockHistorico.infraestructure.persistence.model.StockHistoricoModel;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ResponseListaStockHistorico extends ResponseGeneral implements Serializable {

    private List<StockHistoricoModel> stockHistoricos;
}