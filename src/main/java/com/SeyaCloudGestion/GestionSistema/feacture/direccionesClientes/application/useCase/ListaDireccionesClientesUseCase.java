package com.SeyaCloudGestion.GestionSistema.feacture.direccionesClientes.application.useCase;

import com.SeyaCloudGestion.GestionSistema.feacture.direccionesClientes.application.dto.request.RequestListaDireccionesClientes;
import com.SeyaCloudGestion.GestionSistema.feacture.direccionesClientes.application.dto.response.ResponseListaDireccionesClientes;
import com.SeyaCloudGestion.GestionSistema.feacture.direccionesClientes.domain.services.DireccionesClientesService;
import org.springframework.stereotype.Component;

@Component
public class ListaDireccionesClientesUseCase {
    private final DireccionesClientesService direccionesClientesService;

    public ListaDireccionesClientesUseCase(DireccionesClientesService direccionesClientesService) {
        this.direccionesClientesService = direccionesClientesService;
    }

    public ResponseListaDireccionesClientes ListaDireccionesClientes(RequestListaDireccionesClientes request) {
        try {
            ResponseListaDireccionesClientes response = direccionesClientesService.ListaDireccionesClientes(request);
            if(response.isExito()){}
            return response;
        } catch (IllegalArgumentException | SecurityException e) {
            ResponseListaDireccionesClientes response = new ResponseListaDireccionesClientes();
            response.setExito(false);
            response.setMessage(e.getMessage());
            response.setDireccionesClientes(java.util.List.of());
            return response;
        } catch (Exception e) {
            String mensajeError = "Error inesperado al listar las direcciones de clientes: " + e.getMessage();
            System.err.println("[ERROR] " + mensajeError);
            ResponseListaDireccionesClientes response = new ResponseListaDireccionesClientes();
            response.setExito(false);
            response.setMessage(mensajeError);
            response.setDireccionesClientes(java.util.List.of());
            return response;
        }
    }
}