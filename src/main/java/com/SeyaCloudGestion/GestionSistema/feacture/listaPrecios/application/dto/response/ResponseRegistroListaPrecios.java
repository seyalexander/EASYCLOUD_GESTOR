package com.SeyaCloudGestion.GestionSistema.feacture.listaPrecios.application.dto.response;

import com.SeyaCloudGestion.GestionSistema.common.response.ResponseGeneral;
import com.SeyaCloudGestion.GestionSistema.feacture.listaPrecios.infraestructure.persistence.model.ListaPreciosModel;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ResponseRegistroListaPrecios extends ResponseGeneral implements Serializable {
    private List<ListaPreciosModel> listaPrecios;
}
