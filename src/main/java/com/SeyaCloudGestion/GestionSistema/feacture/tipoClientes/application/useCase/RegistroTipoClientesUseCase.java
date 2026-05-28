package com.SeyaCloudGestion.GestionSistema.feacture.tipoClientes.application.useCase;

import com.SeyaCloudGestion.GestionSistema.feacture.tipoClientes.application.dto.request.RequestRegistroTipoClientes;
import com.SeyaCloudGestion.GestionSistema.feacture.tipoClientes.application.dto.response.ResponseRegistroTipoClientes;
import com.SeyaCloudGestion.GestionSistema.feacture.tipoClientes.domain.services.TipoClientesService;
import org.springframework.stereotype.Component;

@Component
public class RegistroTipoClientesUseCase {
    private final TipoClientesService tipoClientesService;

    public RegistroTipoClientesUseCase(TipoClientesService tipoClientesService) {
        this.tipoClientesService = tipoClientesService;
    }

    public ResponseRegistroTipoClientes RegistroTipoClientes(RequestRegistroTipoClientes request) {
        try {
            ResponseRegistroTipoClientes response =tipoClientesService.RegistroTipoClientes(request);
            if(response.isExito()){}

            return response;
        }catch (IllegalArgumentException | SecurityException e){
            ResponseRegistroTipoClientes response = new ResponseRegistroTipoClientes();
            response.setExito(false);
            response.setMessage(e.getMessage());
            return response;
        }
        catch (Exception e) {
            String mensajeError = "Error inesperado al registrar el tipo cliente: " + e.getMessage();
            System.err.println("[ERROR] " + mensajeError);
            ResponseRegistroTipoClientes response = new ResponseRegistroTipoClientes();
            response.setExito(false);
            response.setMessage("Error inesperado: " + e.getMessage());
            return response;
        }
    }
}