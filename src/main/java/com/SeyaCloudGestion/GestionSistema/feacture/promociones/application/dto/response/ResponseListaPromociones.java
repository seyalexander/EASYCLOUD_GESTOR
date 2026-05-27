package com.SeyaCloudGestion.GestionSistema.feacture.promociones.application.dto.response;

import com.SeyaCloudGestion.GestionSistema.common.response.ResponseGeneral;
import com.SeyaCloudGestion.GestionSistema.feacture.promociones.infraestructure.persistence.model.PromocionesModel;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ResponseListaPromociones extends ResponseGeneral implements Serializable {

    private List<PromocionesModel> promociones;
}