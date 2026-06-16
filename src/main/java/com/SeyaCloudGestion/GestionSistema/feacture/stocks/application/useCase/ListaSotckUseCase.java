package com.SeyaCloudGestion.GestionSistema.feacture.stocks.application.useCase;

import com.SeyaCloudGestion.GestionSistema.feacture.stocks.application.dto.request.RequestListaSotck;
import com.SeyaCloudGestion.GestionSistema.feacture.stocks.application.dto.response.ResponseListaSotck;
import com.SeyaCloudGestion.GestionSistema.feacture.stocks.domain.services.SotckService;
import org.springframework.stereotype.Component;

@Component
public class ListaSotckUseCase {

    private final SotckService sotckService;

    public ListaSotckUseCase(SotckService sotckService) {
        this.sotckService = sotckService;
    }

    public ResponseListaSotck ListaSotck(RequestListaSotck request) {
        try {
            ResponseListaSotck response = sotckService.listaSotck(request);
            if (response.isExito()) {
            }

            return response;

        } catch (IllegalArgumentException | SecurityException e) {
            ResponseListaSotck response = new ResponseListaSotck();
            response.setExito(false);
            response.setMessage(e.getMessage());
            response.setSotcks(java.util.List.of());
            return response;
        } catch (Exception e) {
            String mensajeError = "Error inesperado al listar el stock: " + e.getMessage();
            System.err.println("[ERROR] " + mensajeError);

            ResponseListaSotck response = new ResponseListaSotck();
            response.setExito(false);
            response.setMessage(mensajeError);
            response.setSotcks(java.util.List.of());
            return response;
        }
    }
}