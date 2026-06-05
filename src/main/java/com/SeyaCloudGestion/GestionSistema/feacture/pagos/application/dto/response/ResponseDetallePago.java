package com.SeyaCloudGestion.GestionSistema.feacture.pagos.application.dto.response;

import com.SeyaCloudGestion.GestionSistema.common.response.ResponseGeneral;
import com.SeyaCloudGestion.GestionSistema.feacture.pagos.infraestructure.persistence.model.PagoModel;
import lombok.Data;

@Data
public class ResponseDetallePago extends ResponseGeneral {

    private PagoModel pago;
}