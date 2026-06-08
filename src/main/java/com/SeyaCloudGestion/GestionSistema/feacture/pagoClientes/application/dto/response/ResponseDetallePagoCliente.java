package com.SeyaCloudGestion.GestionSistema.feacture.pagoClientes.application.dto.response;

import com.SeyaCloudGestion.GestionSistema.common.response.ResponseGeneral;
import com.SeyaCloudGestion.GestionSistema.feacture.pagoClientes.infraestructure.persistence.model.PagoClienteModel;
import lombok.Data;

@Data
public class ResponseDetallePagoCliente extends ResponseGeneral {

    private PagoClienteModel pagoCliente;
}