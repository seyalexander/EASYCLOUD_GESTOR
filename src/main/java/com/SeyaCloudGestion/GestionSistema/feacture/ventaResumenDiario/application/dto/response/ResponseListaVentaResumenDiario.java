package com.SeyaCloudGestion.GestionSistema.feacture.ventaResumenDiario.application.dto.response;

import com.SeyaCloudGestion.GestionSistema.common.response.ResponseGeneral;
import com.SeyaCloudGestion.GestionSistema.feacture.ventaResumenDiario.infraestructure.persistence.model.VentaResumenDiarioModel;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ResponseListaVentaResumenDiario extends ResponseGeneral implements Serializable {

    private List<VentaResumenDiarioModel> ventaResumenDiarios;
}