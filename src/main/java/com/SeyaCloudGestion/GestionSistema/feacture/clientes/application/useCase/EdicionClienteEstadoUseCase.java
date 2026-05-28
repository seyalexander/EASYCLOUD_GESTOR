package com.SeyaCloudGestion.GestionSistema.feacture.clientes.application.useCase;

import com.SeyaCloudGestion.GestionSistema.feacture.clientes.application.dto.request.RequestEditarAllCliente;
import com.SeyaCloudGestion.GestionSistema.feacture.clientes.application.dto.request.RequestEditarEstadoCliente;
import com.SeyaCloudGestion.GestionSistema.feacture.clientes.application.dto.response.ResponseEditarAllCliente;
import com.SeyaCloudGestion.GestionSistema.feacture.clientes.application.dto.response.ResponseEditarEstadoCliente;
import com.SeyaCloudGestion.GestionSistema.feacture.clientes.domain.services.ClienteService;
import org.springframework.stereotype.Component;

@Component
public class EdicionClienteEstadoUseCase {
    private final ClienteService  clienteService;

    public EdicionClienteEstadoUseCase(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    public ResponseEditarEstadoCliente AnularCliente(long id) {
        try {
            RequestEditarEstadoCliente request = new RequestEditarEstadoCliente();
            request.setIdCliente(id);
            ResponseEditarEstadoCliente response = clienteService.EditarEstadoCliente(request,0);
            if(response.isExito()){}
            return response;

        }catch (IllegalArgumentException | SecurityException e){
            ResponseEditarEstadoCliente response = new ResponseEditarEstadoCliente();
            response.setExito(false);
            response.setMessage(e.getMessage());
            return response;
        }
        catch (Exception e){
            String mensajeError = "Error inesperado al anular el cliente: " + e.getMessage();
            System.err.println("[ERROR] " + mensajeError);
            ResponseEditarEstadoCliente response = new ResponseEditarEstadoCliente();
            response.setExito(false);
            response.setMessage(mensajeError);
            return response;
        }
    }

    public ResponseEditarEstadoCliente ActivarCliente(long id) {
        try {
            RequestEditarEstadoCliente request = new RequestEditarEstadoCliente();
            request.setIdCliente(id);
            ResponseEditarEstadoCliente response = clienteService.EditarEstadoCliente(request,1);
            if(response.isExito()){}
            return response;

        }catch (IllegalArgumentException | SecurityException e){
            ResponseEditarEstadoCliente response = new ResponseEditarEstadoCliente();
            response.setExito(false);
            response.setMessage(e.getMessage());
            return response;
        }
        catch (Exception e){
            String mensajeError = "Error inesperado al anular el cliente: " + e.getMessage();
            System.err.println("[ERROR] " + mensajeError);
            ResponseEditarEstadoCliente response = new ResponseEditarEstadoCliente();
            response.setExito(false);
            response.setMessage(mensajeError);
            return response;
        }
    }
}
