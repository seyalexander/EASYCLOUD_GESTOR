package com.SeyaCloudGestion.GestionSistema.feacture.aperturaCaja.application.dto.response;

import com.SeyaCloudGestion.GestionSistema.common.response.ResponseGeneral;
import com.SeyaCloudGestion.GestionSistema.feacture.aperturaCaja.infraestructure.persistence.model.AperturaCajaModel;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ResponseListaAperturaCaja extends ResponseGeneral implements Serializable {

    private List<AperturaCajaModel> aperturaCajas;
}