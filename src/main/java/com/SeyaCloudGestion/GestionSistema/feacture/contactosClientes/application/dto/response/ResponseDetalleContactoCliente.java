package com.SeyaCloudGestion.GestionSistema.feacture.contactosClientes.application.dto.response;

import com.SeyaCloudGestion.GestionSistema.common.response.ResponseGeneral;
import com.SeyaCloudGestion.GestionSistema.feacture.contactosClientes.infraestructure.persistence.model.ContactoClienteModel;
import lombok.Data;

@Data
public class ResponseDetalleContactoCliente extends ResponseGeneral {

    private ContactoClienteModel contactoCliente;
}