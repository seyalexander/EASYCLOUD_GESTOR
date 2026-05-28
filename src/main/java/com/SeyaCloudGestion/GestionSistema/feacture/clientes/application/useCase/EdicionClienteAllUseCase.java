package com.SeyaCloudGestion.GestionSistema.feacture.clientes.application.useCase;

import com.SeyaCloudGestion.GestionSistema.feacture.clientes.application.dto.request.RequestEditarAllCliente;
import com.SeyaCloudGestion.GestionSistema.feacture.clientes.application.dto.request.RequestEditarEstadoCliente;
import com.SeyaCloudGestion.GestionSistema.feacture.clientes.application.dto.response.ResponseEditarAllCliente;
import com.SeyaCloudGestion.GestionSistema.feacture.clientes.application.dto.response.ResponseEditarEstadoCliente;
import com.SeyaCloudGestion.GestionSistema.feacture.clientes.domain.services.ClienteService;
import org.springframework.stereotype.Component;

@Component
public class EdicionClienteAllUseCase {
    private final ClienteService clienteService;

    public EdicionClienteAllUseCase(ClienteService clienteService) {
        this.clienteService = clienteService;
    }


    public ResponseEditarAllCliente EdicionAllCliente(RequestEditarAllCliente request) {
        try {
            ResponseEditarAllCliente response = clienteService.EditarAllCliente(request);
            if(response.isExito()){}

            return response;

        }catch (IllegalArgumentException | SecurityException e){
            ResponseEditarAllCliente response = new ResponseEditarAllCliente();
            response.setExito(false);
            response.setMessage(e.getMessage());
            return response;
        }
        catch (Exception e){
            String mensajeError = "Error inesperado al actualizar el estado del cliente: " + e.getMessage();
            System.err.println("[ERROR] " + mensajeError);
            ResponseEditarAllCliente response = new ResponseEditarAllCliente();
            response.setExito(false);
            response.setMessage(mensajeError);
            return response;
        }
    }
}
