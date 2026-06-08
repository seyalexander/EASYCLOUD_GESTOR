package com.SeyaCloudGestion.GestionSistema.feacture.contactosClientes.application.useCase;

import com.SeyaCloudGestion.GestionSistema.feacture.contactosClientes.application.dto.request.RequestEditarAllContactoCliente;
import com.SeyaCloudGestion.GestionSistema.feacture.contactosClientes.application.dto.response.ResponseEditarAllContactoCliente;
import com.SeyaCloudGestion.GestionSistema.feacture.contactosClientes.domain.services.ContactoClienteService;
import org.springframework.stereotype.Component;

@Component
public class EdicionContactoAllClienteUseCase {
    private final ContactoClienteService contactoClienteService;

    public EdicionContactoAllClienteUseCase(ContactoClienteService contactoClienteService) {
        this.contactoClienteService = contactoClienteService;
    }
    public ResponseEditarAllContactoCliente EdicionAllContactoCliente(RequestEditarAllContactoCliente request) {
        try {

            ResponseEditarAllContactoCliente response = contactoClienteService.EditarAllContactoCliente(request);

            if (response.isExito()) {
            }

            return response;

        } catch (IllegalArgumentException | SecurityException e) {

            ResponseEditarAllContactoCliente response = new ResponseEditarAllContactoCliente();
            response.setExito(false);
            response.setMessage(e.getMessage());

            return response;

        } catch (Exception e) {

            String mensajeError = "Error inesperado al actualizar el contacto del cliente: " + e.getMessage();
            System.err.println("[ERROR] " + mensajeError);

            ResponseEditarAllContactoCliente response = new ResponseEditarAllContactoCliente();
            response.setExito(false);
            response.setMessage(mensajeError);

            return response;
        }
    }
}
