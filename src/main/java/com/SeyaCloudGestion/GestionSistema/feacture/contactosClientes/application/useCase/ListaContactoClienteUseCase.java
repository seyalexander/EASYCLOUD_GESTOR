package com.SeyaCloudGestion.GestionSistema.feacture.contactosClientes.application.useCase;

import com.SeyaCloudGestion.GestionSistema.feacture.contactosClientes.application.dto.request.RequestListaContactoCliente;
import com.SeyaCloudGestion.GestionSistema.feacture.contactosClientes.application.dto.response.ResponseListaContactoCliente;
import com.SeyaCloudGestion.GestionSistema.feacture.contactosClientes.domain.services.ContactoClienteService;
import org.springframework.stereotype.Component;

@Component
public class ListaContactoClienteUseCase {
    private final ContactoClienteService contactoClienteService;

    public ListaContactoClienteUseCase(ContactoClienteService contactoClienteService) {
        this.contactoClienteService = contactoClienteService;
    }

    public ResponseListaContactoCliente ListaContactoCliente(RequestListaContactoCliente request) {
        try {

            ResponseListaContactoCliente response = contactoClienteService.ListaContactoCliente(request);

            if (response.isExito()) {
            }

            return response;

        } catch (IllegalArgumentException | SecurityException e) {

            ResponseListaContactoCliente response = new ResponseListaContactoCliente();
            response.setExito(false);
            response.setMessage(e.getMessage());
            response.setContactoClientes(java.util.List.of());

            return response;

        } catch (Exception e) {

            String mensajeError = "Error inesperado al listar los contactos del cliente: " + e.getMessage();
            System.err.println("[ERROR] " + mensajeError);

            ResponseListaContactoCliente response = new ResponseListaContactoCliente();
            response.setExito(false);
            response.setMessage(mensajeError);
            response.setContactoClientes(java.util.List.of());

            return response;
        }
    }
}
