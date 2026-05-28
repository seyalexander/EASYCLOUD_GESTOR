package com.SeyaCloudGestion.GestionSistema.feacture.direccionesClientes.application.useCase;

import com.SeyaCloudGestion.GestionSistema.feacture.direccionesClientes.application.dto.request.RequestRegistroDireccionesClientes;
import com.SeyaCloudGestion.GestionSistema.feacture.direccionesClientes.application.dto.response.ResponseRegistroDireccionesClientes;
import com.SeyaCloudGestion.GestionSistema.feacture.direccionesClientes.domain.services.DireccionesClientesService;
import org.springframework.stereotype.Component;

@Component
public class RegistroDireccionesClientesUseCase {

    private final DireccionesClientesService direccionesClientesService;

    public RegistroDireccionesClientesUseCase(DireccionesClientesService direccionesClientesService) {
        this.direccionesClientesService = direccionesClientesService;
    }
    public ResponseRegistroDireccionesClientes RegistroDireccionesClientes(RequestRegistroDireccionesClientes request) {
        try {
            ResponseRegistroDireccionesClientes response = direccionesClientesService.RegistroDireccionesClientes(request);
            if(response.isExito()){}
            return response;
        }catch (IllegalArgumentException | SecurityException e){
            ResponseRegistroDireccionesClientes response = new ResponseRegistroDireccionesClientes();
            response.setExito(false);
            response.setMessage(e.getMessage());
            return response;
        }
        catch (Exception e){
            String mensajeError = "Error inesperado al registrar la dirección del cliente: " + e.getMessage();
            System.err.println("[ERROR] " + mensajeError);
            ResponseRegistroDireccionesClientes response = new ResponseRegistroDireccionesClientes();
            response.setExito(false);
            response.setMessage(mensajeError);
            return response;
        }
    }
}