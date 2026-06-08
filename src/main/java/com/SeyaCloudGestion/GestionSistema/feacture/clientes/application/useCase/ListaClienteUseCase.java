package com.SeyaCloudGestion.GestionSistema.feacture.clientes.application.useCase;

import com.SeyaCloudGestion.GestionSistema.feacture.clientes.application.dto.request.RequestListaCliente;
import com.SeyaCloudGestion.GestionSistema.feacture.clientes.application.dto.response.ResponseListaCliente;
import com.SeyaCloudGestion.GestionSistema.feacture.clientes.domain.services.ClienteService;
import org.apache.coyote.Response;
import org.springframework.stereotype.Component;

@Component
public class ListaClienteUseCase {
    private final ClienteService clienteService;

    public ListaClienteUseCase(ClienteService clienteService) {
        this.clienteService = clienteService;
    }
    public ResponseListaCliente ListaCliente(RequestListaCliente request) {
        try {
            ResponseListaCliente response = clienteService.ListaCliente(request);
            if(response.isExito()){}
            return response;
        } catch (IllegalArgumentException | SecurityException e) {
            ResponseListaCliente response = new ResponseListaCliente();
            response.setExito(false);
            response.setMessage(e.getMessage());
            response.setClientes(java.util.List.of());
            return response;
        } catch (Exception e) {
            String mensajeError = "Error inesperado al listar los clientes: " + e.getMessage();
            System.err.println("[ERROR] " + mensajeError);
            ResponseListaCliente response = new ResponseListaCliente();
            response.setExito(false);
            response.setMessage(mensajeError);
            response.setClientes(java.util.List.of());
            return response;
        }
    }
}
