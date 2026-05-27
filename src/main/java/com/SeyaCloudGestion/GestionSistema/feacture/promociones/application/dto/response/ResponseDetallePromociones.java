package com.SeyaCloudGestion.GestionSistema.feacture.promociones.application.dto.response;

import com.SeyaCloudGestion.GestionSistema.common.response.ResponseGeneral;
import com.SeyaCloudGestion.GestionSistema.feacture.promociones.infraestructure.persistence.model.PromocionesModel;
import lombok.Data;

@Data
public class ResponseDetallePromociones extends ResponseGeneral {

    private PromocionesModel promociones;
}