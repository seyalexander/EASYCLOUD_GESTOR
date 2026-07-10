package com.SeyaCloudGestion.GestionSistema.feacture.pagoProveedores.application.dto.response;

import com.SeyaCloudGestion.GestionSistema.common.response.ResponseGeneral;
import com.SeyaCloudGestion.GestionSistema.feacture.pagoProveedores.infraestructure.persistence.model.PagoProveedorModel;
import lombok.Data;

@Data
public class ResponseDetallePagoProveedores extends ResponseGeneral {

    private PagoProveedorModel pagoProveedores;
}