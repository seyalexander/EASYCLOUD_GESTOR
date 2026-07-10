package com.SeyaCloudGestion.GestionSistema.feacture.pagoProveedores.application.dto.response;

import com.SeyaCloudGestion.GestionSistema.common.response.ResponseGeneral;
import com.SeyaCloudGestion.GestionSistema.feacture.pagoProveedores.infraestructure.persistence.model.PagoProveedorModel;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ResponseListaPagoProveedor extends ResponseGeneral implements Serializable {

    private List<PagoProveedorModel> pagoProveedor;
}