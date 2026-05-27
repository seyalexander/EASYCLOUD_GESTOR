package com.SeyaCloudGestion.GestionSistema.feacture.tipoPromociones.application.dto.response;

import com.SeyaCloudGestion.GestionSistema.common.response.ResponseGeneral;
import com.SeyaCloudGestion.GestionSistema.feacture.tipoPromociones.infraestructure.persistence.model.TipoPromocionesModel;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ResponseListaTipoPromociones extends ResponseGeneral implements Serializable {

    private List<TipoPromocionesModel> tipoPromociones;
}