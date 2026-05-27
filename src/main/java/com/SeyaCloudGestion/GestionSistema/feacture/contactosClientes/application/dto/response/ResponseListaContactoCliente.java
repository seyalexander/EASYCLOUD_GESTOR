package com.SeyaCloudGestion.GestionSistema.feacture.contactosClientes.application.dto.response;

import com.SeyaCloudGestion.GestionSistema.common.response.ResponseGeneral;
import com.SeyaCloudGestion.GestionSistema.feacture.contactosClientes.infraestructure.persistence.model.ContactoClienteModel;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ResponseListaContactoCliente extends ResponseGeneral implements Serializable {

    private List<ContactoClienteModel> contactoClientes;
}