package com.SeyaCloudGestion.GestionSistema.feacture.almacenes.application.dto.response;

import com.SeyaCloudGestion.GestionSistema.common.response.ResponseGeneral;
import com.SeyaCloudGestion.GestionSistema.feacture.almacenes.infraestructure.persistence.model.AlmacenModel;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ResponseListaAlmacen extends ResponseGeneral implements Serializable {

    private List<AlmacenModel> almacenes;
}