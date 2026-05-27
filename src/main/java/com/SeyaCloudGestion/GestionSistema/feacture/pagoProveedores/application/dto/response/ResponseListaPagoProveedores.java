package com.SeyaCloudGestion.GestionSistema.feacture.pagoProveedores.application.dto.response;

import com.SeyaCloudGestion.GestionSistema.common.response.ResponseGeneral;
import com.SeyaCloudGestion.GestionSistema.feacture.pagoProveedores.infraestructure.persistence.model.PagoProveedoresModel;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ResponseListaPagoProveedores extends ResponseGeneral implements Serializable {

    private List<PagoProveedoresModel> pagoProveedores;
}