package com.SeyaCloudGestion.GestionSistema.feacture.tipoClientes.application.useCase;

import com.SeyaCloudGestion.GestionSistema.feacture.subFamilia.application.dto.response.ResponseListaSubFamilia;
import com.SeyaCloudGestion.GestionSistema.feacture.tipoClientes.application.dto.request.RequestListaTipoClientes;
import com.SeyaCloudGestion.GestionSistema.feacture.tipoClientes.application.dto.response.ResponseListaTipoClientes;
import com.SeyaCloudGestion.GestionSistema.feacture.tipoClientes.domain.services.TipoClientesService;
import org.springframework.stereotype.Component;

@Component
public class ListaTipoClientesUseCase {
    private final TipoClientesService tipoClientesService;

    public ListaTipoClientesUseCase(TipoClientesService tipoClientesService) {
        this.tipoClientesService = tipoClientesService;
    }

    public ResponseListaTipoClientes ListaTipoClientes(RequestListaTipoClientes request) {
        try {

            ResponseListaTipoClientes response = tipoClientesService.ListaTipoClientes(request);
            if(response.isExito()){}

            return response;
        } catch (IllegalArgumentException | SecurityException e) {
            ResponseListaTipoClientes response = new ResponseListaTipoClientes();
            response.setExito(false);
            response.setMessage(e.getMessage());
            response.setTipoClientes(java.util.List.of());
            return response;
        } catch (Exception e) {
            String mensajeError = "Error inesperado al listar los tipos cliente: " + e.getMessage();
            System.err.println("[ERROR] " + mensajeError);
            ResponseListaTipoClientes response = new ResponseListaTipoClientes();
            response.setExito(false);
            response.setMessage("Error inesperado: " + e.getMessage());
            response.setTipoClientes(java.util.List.of());
            return response;
        }
    }
}
