package com.SeyaCloudGestion.GestionSistema.feacture.ventaResumenDiario.application.dto.response;

import com.SeyaCloudGestion.GestionSistema.common.response.ResponseGeneral;
import com.SeyaCloudGestion.GestionSistema.feacture.ventaResumenDiario.infraestructure.persistence.model.VentaResumenDiarioModel;
import lombok.Data;

@Data
public class ResponseDetalleVentaResumenDiario extends ResponseGeneral {

    private VentaResumenDiarioModel ventaResumenDiario;
}