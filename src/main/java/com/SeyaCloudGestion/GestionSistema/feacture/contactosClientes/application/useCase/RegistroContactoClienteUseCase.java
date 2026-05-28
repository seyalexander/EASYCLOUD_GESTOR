package com.SeyaCloudGestion.GestionSistema.feacture.contactosClientes.application.useCase;

import com.SeyaCloudGestion.GestionSistema.feacture.contactosClientes.application.dto.request.RequestRegistroContactoCliente;
import com.SeyaCloudGestion.GestionSistema.feacture.contactosClientes.application.dto.response.ResponseRegistroContactoCliente;
import com.SeyaCloudGestion.GestionSistema.feacture.contactosClientes.domain.services.ContactoClienteService;
import org.springframework.stereotype.Component;

@Component
public class RegistroContactoClienteUseCase {
    private final ContactoClienteService contactoClienteService;

    public RegistroContactoClienteUseCase(ContactoClienteService contactoClienteService) {
        this.contactoClienteService = contactoClienteService;
    }
    public ResponseRegistroContactoCliente RegistroContactoCliente(RequestRegistroContactoCliente request) {
        try {
            ResponseRegistroContactoCliente response = contactoClienteService.RegistroContactoCliente(request);

            if (response.isExito()) {
            }

            return response;

        } catch (IllegalArgumentException | SecurityException e) {
            ResponseRegistroContactoCliente response = new ResponseRegistroContactoCliente();
            response.setExito(false);
            response.setMessage(e.getMessage());
            return response;

        } catch (Exception e) {
            String mensajeError = "Error inesperado al registrar el contacto del cliente: " + e.getMessage();
            System.err.println("[ERROR] " + mensajeError);

            ResponseRegistroContactoCliente response = new ResponseRegistroContactoCliente();
            response.setExito(false);
            response.setMessage(mensajeError);
            return response;
        }
    }
}
