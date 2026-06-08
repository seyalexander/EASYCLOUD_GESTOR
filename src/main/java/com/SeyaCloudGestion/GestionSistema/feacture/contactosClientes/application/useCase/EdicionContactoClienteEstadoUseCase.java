package com.SeyaCloudGestion.GestionSistema.feacture.contactosClientes.application.useCase;

import com.SeyaCloudGestion.GestionSistema.feacture.contactosClientes.application.dto.request.RequestEditarEstadoContactoCliente;
import com.SeyaCloudGestion.GestionSistema.feacture.contactosClientes.application.dto.response.ResponseEditarEstadoContactoCliente;
import com.SeyaCloudGestion.GestionSistema.feacture.contactosClientes.domain.services.ContactoClienteService;
import org.springframework.stereotype.Component;

@Component
public class EdicionContactoClienteEstadoUseCase {
    private final ContactoClienteService contactoClienteService;

    public EdicionContactoClienteEstadoUseCase(ContactoClienteService contactoClienteService) {
        this.contactoClienteService = contactoClienteService;
    }
    public ResponseEditarEstadoContactoCliente AnularContactoCliente(long idContactoCliente) {
        try {
            RequestEditarEstadoContactoCliente request = new RequestEditarEstadoContactoCliente();
            request.setIdContactoCliente(idContactoCliente);

            ResponseEditarEstadoContactoCliente response = contactoClienteService.EditarEstadoContactoCliente(request, 0);

            if (response.isExito()) {
            }

            return response;

        } catch (IllegalArgumentException | SecurityException e) {

            ResponseEditarEstadoContactoCliente response = new ResponseEditarEstadoContactoCliente();
            response.setExito(false);
            response.setMessage(e.getMessage());

            return response;

        } catch (Exception e) {

            String mensajeError = "Error inesperado al anular el contacto del cliente: " + e.getMessage();
            System.err.println("[ERROR] " + mensajeError);

            ResponseEditarEstadoContactoCliente response = new ResponseEditarEstadoContactoCliente();
            response.setExito(false);
            response.setMessage(mensajeError);

            return response;
        }
    }

    public ResponseEditarEstadoContactoCliente ActivarContactoCliente(long idContactoCliente) {
        try {
            RequestEditarEstadoContactoCliente request = new RequestEditarEstadoContactoCliente();
            request.setIdContactoCliente(idContactoCliente);

            ResponseEditarEstadoContactoCliente response = contactoClienteService.EditarEstadoContactoCliente(request, 1);

            if (response.isExito()) {
            }

            return response;

        } catch (IllegalArgumentException | SecurityException e) {

            ResponseEditarEstadoContactoCliente response = new ResponseEditarEstadoContactoCliente();
            response.setExito(false);
            response.setMessage(e.getMessage());

            return response;

        } catch (Exception e) {

            String mensajeError = "Error inesperado al activar el contacto del cliente: " + e.getMessage();
            System.err.println("[ERROR] " + mensajeError);

            ResponseEditarEstadoContactoCliente response = new ResponseEditarEstadoContactoCliente();
            response.setExito(false);
            response.setMessage(mensajeError);

            return response;
        }
    }
}
