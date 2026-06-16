package com.SeyaCloudGestion.GestionSistema.feacture.almacenes.application.dto.response;

import com.SeyaCloudGestion.GestionSistema.common.response.ResponseGeneral;
import com.SeyaCloudGestion.GestionSistema.feacture.almacenes.infraestructure.persistence.model.AlmacenModel;
import lombok.Data;

@Data
public class ResponseDetalleAlmacen extends ResponseGeneral {

    private AlmacenModel almacen;
}