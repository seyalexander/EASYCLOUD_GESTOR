package com.SeyaCloudGestion.GestionSistema.feacture.pagos.application.dto.response;

import com.SeyaCloudGestion.GestionSistema.common.response.ResponseGeneral;
import com.SeyaCloudGestion.GestionSistema.feacture.pagos.infraestructure.persistence.model.PagoModel;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ResponseListaPago extends ResponseGeneral implements Serializable {

    private List<PagoModel> pagos;
}