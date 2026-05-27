package com.SeyaCloudGestion.GestionSistema.feacture.aperturaCaja.application.dto.response;

import com.SeyaCloudGestion.GestionSistema.common.response.ResponseGeneral;
import com.SeyaCloudGestion.GestionSistema.feacture.aperturaCaja.infraestructure.persistence.model.AperturaCajaModel;
import lombok.Data;

@Data
public class ResponseDetalleAperturaCaja extends ResponseGeneral {

    private AperturaCajaModel aperturaCaja;
}