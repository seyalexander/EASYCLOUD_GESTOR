package com.SeyaCloudGestion.GestionSistema.feacture.listaPrecios.application.dto.response;

import com.SeyaCloudGestion.GestionSistema.common.response.ResponseGeneral;
import com.SeyaCloudGestion.GestionSistema.feacture.listaPrecios.infraestructure.persistence.model.ListaPreciosModel;
import lombok.Data;

@Data
public class ResponseDetalleListaPrecios extends ResponseGeneral {

    private ListaPreciosModel listaPrecios;
}