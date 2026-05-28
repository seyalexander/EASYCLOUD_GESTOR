package com.SeyaCloudGestion.GestionSistema.feacture.tipoClientes.application.useCase;

import com.SeyaCloudGestion.GestionSistema.feacture.serieDocumento.application.dto.request.RequestDetalleSeries;
import com.SeyaCloudGestion.GestionSistema.feacture.tipoClientes.application.dto.request.RequestEditarAllTipoClientes;
import com.SeyaCloudGestion.GestionSistema.feacture.tipoClientes.application.dto.response.ResponseEditarAllTipoClientes;
import com.SeyaCloudGestion.GestionSistema.feacture.tipoClientes.domain.services.TipoClientesService;
import org.springframework.stereotype.Component;

@Component
public class EdicionTipoClientesUseCase {
    private final TipoClientesService tipoClientesService;

    public EdicionTipoClientesUseCase(TipoClientesService tipoClientesService) {
        this.tipoClientesService = tipoClientesService;
    }

    public ResponseEditarAllTipoClientes EdicionAllTipoClientes(RequestEditarAllTipoClientes request) {
        try {
            ResponseEditarAllTipoClientes response = tipoClientesService.EditarAllTipoClientes(request);
            if (response.isExito()) {
            }
            return response;
        } catch (IllegalArgumentException | SecurityException e) {
            ResponseEditarAllTipoClientes response = new ResponseEditarAllTipoClientes();
            response.setExito(false);
            response.setMessage(e.getMessage());
            return response;
        } catch (Exception e) {
            String mensajeError = "Error inesperado al obtener los datos para editar el tipo cliente: " + e.getMessage();
            System.err.println("[ERROR] " + mensajeError);
            ResponseEditarAllTipoClientes response = new ResponseEditarAllTipoClientes();
            response.setExito(false);
            response.setMessage("Error inesperado: " + e.getMessage());
            return response;
        }
    }
}