package com.SeyaCloudGestion.GestionSistema.feacture.contactosClientes.application.useCase;

import com.SeyaCloudGestion.GestionSistema.feacture.contactosClientes.application.dto.request.RequestDetalleContactoCliente;
import com.SeyaCloudGestion.GestionSistema.feacture.contactosClientes.application.dto.response.ResponseDetalleContactoCliente;
import com.SeyaCloudGestion.GestionSistema.feacture.contactosClientes.domain.services.ContactoClienteService;
import org.springframework.stereotype.Component;

@Component
public class DetalleContactoClienteUseCase {
    private final ContactoClienteService contactoClienteService;

    public DetalleContactoClienteUseCase(ContactoClienteService contactoClienteService) {
        this.contactoClienteService = contactoClienteService;
    }
    public ResponseDetalleContactoCliente DetalleContactoCliente(long idContactoCliente) {
        try {
            RequestDetalleContactoCliente request = new RequestDetalleContactoCliente();
            request.setIdContactoCliente(idContactoCliente);

            ResponseDetalleContactoCliente response = contactoClienteService.DetalleContactoCliente(request);

            if (response.isExito()) {
            }

            return response;

        } catch (IllegalArgumentException | SecurityException e) {

            ResponseDetalleContactoCliente response = new ResponseDetalleContactoCliente();
            response.setExito(false);
            response.setMessage(e.getMessage());

            return response;

        } catch (Exception e) {

            String mensajeError = "Error inesperado al ver el detalle del contacto del cliente: " + e.getMessage();
            System.err.println("[ERROR] " + mensajeError);

            ResponseDetalleContactoCliente response = new ResponseDetalleContactoCliente();
            response.setExito(false);
            response.setMessage(mensajeError);

            return response;
        }
    }
}
