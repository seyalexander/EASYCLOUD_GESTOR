package com.SeyaCloudGestion.GestionSistema.feacture.direccionesClientes.application.useCase;

import com.SeyaCloudGestion.GestionSistema.feacture.direccionesClientes.application.dto.request.RequestEditarEstadoDireccionesClientes;
import com.SeyaCloudGestion.GestionSistema.feacture.direccionesClientes.application.dto.response.ResponseEditarEstadoDireccionesClientes;
import com.SeyaCloudGestion.GestionSistema.feacture.direccionesClientes.domain.services.DireccionesClientesService;
import org.springframework.stereotype.Component;

@Component
public class EdicionDireccionesClientesEstadoUseCase {
    private final DireccionesClientesService direccionesClientesService;

    public EdicionDireccionesClientesEstadoUseCase(DireccionesClientesService direccionesClientesService) {
        this.direccionesClientesService = direccionesClientesService;
    }

    public ResponseEditarEstadoDireccionesClientes AnularDireccionesClientes(long idDireccionCliente) {
        try {
            RequestEditarEstadoDireccionesClientes request = new RequestEditarEstadoDireccionesClientes();
            request.setIdDireccionesClientes(idDireccionCliente);
            ResponseEditarEstadoDireccionesClientes response = direccionesClientesService.EditarEstadoDireccionesClientes(request,0);
            if(response.isExito()){}
            return response;
        } catch (IllegalArgumentException | SecurityException e) {
            ResponseEditarEstadoDireccionesClientes response = new ResponseEditarEstadoDireccionesClientes();
            response.setExito(false);
            response.setMessage(e.getMessage());
            return response;
        } catch (Exception e) {
            String mensajeError = "Error inesperado al anular la dirección del cliente: " + e.getMessage();
            System.err.println("[ERROR] " + mensajeError);
            ResponseEditarEstadoDireccionesClientes response = new ResponseEditarEstadoDireccionesClientes();
            response.setExito(false);
            response.setMessage(mensajeError);
            return response;
        }
    }
    public ResponseEditarEstadoDireccionesClientes ActivarDireccionesClientes(long idDireccionCliente) {
        try {
            RequestEditarEstadoDireccionesClientes request = new RequestEditarEstadoDireccionesClientes();
            request.setIdDireccionesClientes(idDireccionCliente);
            ResponseEditarEstadoDireccionesClientes response = direccionesClientesService.EditarEstadoDireccionesClientes(request,1);
            if(response.isExito()){}
            return response;
        } catch (IllegalArgumentException | SecurityException e) {
            ResponseEditarEstadoDireccionesClientes response = new ResponseEditarEstadoDireccionesClientes();
            response.setExito(false);
            response.setMessage(e.getMessage());
            return response;
        } catch (Exception e) {
            String mensajeError = "Error inesperado al anular la dirección del cliente: " + e.getMessage();
            System.err.println("[ERROR] " + mensajeError);
            ResponseEditarEstadoDireccionesClientes response = new ResponseEditarEstadoDireccionesClientes();
            response.setExito(false);
            response.setMessage(mensajeError);
            return response;
        }
    }
}