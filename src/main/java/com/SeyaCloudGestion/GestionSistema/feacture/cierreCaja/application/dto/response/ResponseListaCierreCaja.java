package com.SeyaCloudGestion.GestionSistema.feacture.cierreCaja.application.dto.response;

import com.SeyaCloudGestion.GestionSistema.common.response.ResponseGeneral;
import com.SeyaCloudGestion.GestionSistema.feacture.cierreCaja.infraestructure.persistence.model.CierreCajaModel;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ResponseListaCierreCaja extends ResponseGeneral implements Serializable {

    private List<CierreCajaModel> cierreCajas;
}