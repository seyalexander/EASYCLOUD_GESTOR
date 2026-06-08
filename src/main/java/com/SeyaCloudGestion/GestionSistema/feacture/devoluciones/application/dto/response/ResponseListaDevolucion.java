package com.SeyaCloudGestion.GestionSistema.feacture.devoluciones.application.dto.response;

import com.SeyaCloudGestion.GestionSistema.common.response.ResponseGeneral;
import com.SeyaCloudGestion.GestionSistema.feacture.devoluciones.infraestructure.persistence.model.DevolucionModel;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ResponseListaDevolucion extends ResponseGeneral implements Serializable {

    private List<DevolucionModel> devoluciones;
}