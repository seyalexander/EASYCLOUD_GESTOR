package com.SeyaCloudGestion.GestionSistema.feacture.tipoPromociones.application.dto.response;

import com.SeyaCloudGestion.GestionSistema.common.response.ResponseGeneral;
import com.SeyaCloudGestion.GestionSistema.feacture.tipoPromociones.infraestructure.persistence.model.TipoPromocionesModel;
import lombok.Data;

@Data
public class ResponseDetalleTipoPromociones extends ResponseGeneral {

    private TipoPromocionesModel tipoPromociones;
}