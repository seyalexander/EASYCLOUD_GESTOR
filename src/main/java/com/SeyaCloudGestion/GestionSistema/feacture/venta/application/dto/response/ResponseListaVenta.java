package com.SeyaCloudGestion.GestionSistema.feacture.venta.application.dto.response;

import com.SeyaCloudGestion.GestionSistema.common.response.ResponseGeneral;
import com.SeyaCloudGestion.GestionSistema.feacture.venta.infraestructure.persistence.model.VentaModel;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ResponseListaVenta extends ResponseGeneral implements Serializable {

    private List<VentaModel> ventas;
}