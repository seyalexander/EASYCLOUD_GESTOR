package com.SeyaCloudGestion.GestionSistema.feacture.tipoClientes.application.useCase;

import com.SeyaCloudGestion.GestionSistema.feacture.tipoClientes.application.dto.request.RequestEditarEstadoTipoClientes;
import com.SeyaCloudGestion.GestionSistema.feacture.tipoClientes.application.dto.response.ResponseEditarEstadoTipoClientes;
import com.SeyaCloudGestion.GestionSistema.feacture.tipoClientes.domain.services.TipoClientesService;
import org.springframework.stereotype.Component;

@Component
public class EdicionTipoClientesEstadoUseCase {
    private final TipoClientesService tipoClientesService;

    public EdicionTipoClientesEstadoUseCase(TipoClientesService tipoClientesService) {
        this.tipoClientesService = tipoClientesService;
    }

    public ResponseEditarEstadoTipoClientes AnularTipoCliente(long idTipoCliente) {
        try {
            RequestEditarEstadoTipoClientes request = new RequestEditarEstadoTipoClientes();
            request.setIdTipoClientes(idTipoCliente);
            ResponseEditarEstadoTipoClientes response = tipoClientesService.EditarEstadoTipoClientes(request,0);
            if(response.isExito()){}

            return response;
        } catch (IllegalArgumentException | SecurityException e) {
            ResponseEditarEstadoTipoClientes response = new ResponseEditarEstadoTipoClientes();
            response.setExito(false);
            response.setMessage(e.getMessage());
            return response;
        } catch (Exception e) {
            String mensajeError = "Error inesperado al editar el estado del tipo cliente: " + e.getMessage();
            System.err.println("[ERROR] " + mensajeError);
            ResponseEditarEstadoTipoClientes response = new ResponseEditarEstadoTipoClientes();
            response.setExito(false);
            response.setMessage("Error inesperado: " + e.getMessage());
            return response;
        }
    }

    public ResponseEditarEstadoTipoClientes ActivarTipoCliente(long idTipoCliente) {
        try {
            RequestEditarEstadoTipoClientes request = new RequestEditarEstadoTipoClientes();
            request.setIdTipoClientes(idTipoCliente);
            ResponseEditarEstadoTipoClientes response = tipoClientesService.EditarEstadoTipoClientes(request,1);
            if(response.isExito()){}

            return response;
        } catch (IllegalArgumentException | SecurityException e) {
            ResponseEditarEstadoTipoClientes response = new ResponseEditarEstadoTipoClientes();
            response.setExito(false);
            response.setMessage(e.getMessage());
            return response;
        } catch (Exception e) {
            String mensajeError = "Error inesperado al editar el estado del tipo cliente: " + e.getMessage();
            System.err.println("[ERROR] " + mensajeError);
            ResponseEditarEstadoTipoClientes response = new ResponseEditarEstadoTipoClientes();
            response.setExito(false);
            response.setMessage("Error inesperado: " + e.getMessage());
            return response;
        }
    }
}