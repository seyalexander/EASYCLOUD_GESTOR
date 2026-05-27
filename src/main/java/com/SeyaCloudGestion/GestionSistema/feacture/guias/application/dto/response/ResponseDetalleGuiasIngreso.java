package com.SeyaCloudGestion.GestionSistema.feacture.guias.application.dto.response;

import com.SeyaCloudGestion.GestionSistema.common.response.ResponseGeneral;
import com.SeyaCloudGestion.GestionSistema.feacture.guias.infraestructure.persistence.model.GuiasIngresoModel;
import lombok.Data;

@Data
public class ResponseDetalleGuiasIngreso extends ResponseGeneral {

    private GuiasIngresoModel guiasIngreso;
}