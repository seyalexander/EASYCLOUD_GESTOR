package com.SeyaCloudGestion.GestionSistema.feacture.almacenes.application.dto.response;

import com.SeyaCloudGestion.GestionSistema.common.response.ResponseGeneral;
import com.SeyaCloudGestion.GestionSistema.feacture.almacenes.infraestructure.persistence.model.AlmacenesModel;
import lombok.Data;

@Data
public class ResponseDetalleAlmacenes extends ResponseGeneral {

    private AlmacenesModel almacenes;
}