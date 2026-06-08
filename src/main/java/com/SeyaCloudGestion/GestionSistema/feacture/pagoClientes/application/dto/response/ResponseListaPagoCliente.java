package com.SeyaCloudGestion.GestionSistema.feacture.pagoClientes.application.dto.response;

import com.SeyaCloudGestion.GestionSistema.common.response.ResponseGeneral;
import com.SeyaCloudGestion.GestionSistema.feacture.pagoClientes.infraestructure.persistence.model.PagoClienteModel;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ResponseListaPagoCliente extends ResponseGeneral implements Serializable {

    private List<PagoClienteModel> pagoClientes;
}