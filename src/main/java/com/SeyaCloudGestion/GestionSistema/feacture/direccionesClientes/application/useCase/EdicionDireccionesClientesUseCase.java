package com.SeyaCloudGestion.GestionSistema.feacture.direccionesClientes.application.useCase;

import com.SeyaCloudGestion.GestionSistema.feacture.direccionesClientes.application.dto.request.RequestEditarAllDireccionesClientes;
import com.SeyaCloudGestion.GestionSistema.feacture.direccionesClientes.application.dto.response.ResponseEditarAllDireccionesClientes;
import com.SeyaCloudGestion.GestionSistema.feacture.direccionesClientes.domain.services.DireccionesClientesService;
import org.springframework.stereotype.Component;

@Component
public class EdicionDireccionesClientesUseCase {
    private  final DireccionesClientesService direccionesClientesService;

    public EdicionDireccionesClientesUseCase(DireccionesClientesService direccionesClientesService) {
        this.direccionesClientesService = direccionesClientesService;
    }

    public ResponseEditarAllDireccionesClientes EdicionAllDireccionesClientes(RequestEditarAllDireccionesClientes request) {
        try {
            ResponseEditarAllDireccionesClientes response = direccionesClientesService.EditarAllDireccionesClientes(request);
            if(response.isExito()){}

            return response;

        }catch (IllegalArgumentException | SecurityException e){
            ResponseEditarAllDireccionesClientes response = new ResponseEditarAllDireccionesClientes();
            response.setExito(false);
            response.setMessage(e.getMessage());
            return response;
        }
        catch (Exception e){
            String mensajeError = "Error inesperado al actualizar las direcciones del cliente: " + e.getMessage();
            System.err.println("[ERROR] " + mensajeError);
            ResponseEditarAllDireccionesClientes response = new ResponseEditarAllDireccionesClientes();
            response.setExito(false);
            response.setMessage(mensajeError);
            return response;
        }
    }
}