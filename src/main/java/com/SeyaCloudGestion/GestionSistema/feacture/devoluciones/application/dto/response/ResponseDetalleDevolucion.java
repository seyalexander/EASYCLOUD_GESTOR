package com.SeyaCloudGestion.GestionSistema.feacture.devoluciones.application.dto.response;

import com.SeyaCloudGestion.GestionSistema.common.response.ResponseGeneral;
import com.SeyaCloudGestion.GestionSistema.feacture.devoluciones.infraestructure.persistence.model.DevolucionModel;
import lombok.Data;

@Data
public class ResponseDetalleDevolucion extends ResponseGeneral {

    private DevolucionModel devolucion;
}