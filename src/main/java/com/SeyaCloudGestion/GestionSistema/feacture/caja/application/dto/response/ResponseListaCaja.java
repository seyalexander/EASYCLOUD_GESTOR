package com.SeyaCloudGestion.GestionSistema.feacture.caja.application.dto.response;

import com.SeyaCloudGestion.GestionSistema.common.response.ResponseGeneral;
import com.SeyaCloudGestion.GestionSistema.feacture.caja.infraestructure.persistence.model.CajaModel;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ResponseListaCaja extends ResponseGeneral implements Serializable {

    private List<CajaModel> cajas;
}