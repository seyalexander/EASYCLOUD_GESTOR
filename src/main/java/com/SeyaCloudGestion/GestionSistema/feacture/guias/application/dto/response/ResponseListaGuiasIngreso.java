package com.SeyaCloudGestion.GestionSistema.feacture.guias.application.dto.response;

import com.SeyaCloudGestion.GestionSistema.common.response.ResponseGeneral;
import com.SeyaCloudGestion.GestionSistema.feacture.guias.infraestructure.persistence.model.GuiasIngresoModel;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ResponseListaGuiasIngreso extends ResponseGeneral implements Serializable {

    private List<GuiasIngresoModel> guiasIngresos;
}