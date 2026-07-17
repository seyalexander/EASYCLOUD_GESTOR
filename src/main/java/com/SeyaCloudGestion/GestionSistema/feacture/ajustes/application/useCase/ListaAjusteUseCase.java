package com.SeyaCloudGestion.GestionSistema.feacture.ajustes.application.useCase;

import com.SeyaCloudGestion.GestionSistema.feacture.ajustes.application.dto.request.RequestListaAjuste;
import com.SeyaCloudGestion.GestionSistema.feacture.ajustes.application.dto.response.ResponseListaAjuste;
import com.SeyaCloudGestion.GestionSistema.feacture.ajustes.domain.services.AjustesService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ListaAjusteUseCase {

    private final AjustesService ajustesService;

    public ListaAjusteUseCase(AjustesService ajustesService) {
        this.ajustesService = ajustesService;
    }

    public ResponseListaAjuste listaAjustes(RequestListaAjuste request) {
        try {
            ResponseListaAjuste response = ajustesService.listaAjustes(request);

            if (response.isExito()) {
            }

            return response;

        } catch (IllegalArgumentException | SecurityException e) {
            ResponseListaAjuste response = new ResponseListaAjuste();
            response.setExito(false);
            response.setMessage(e.getMessage());
            response.setAjustes(List.of());
            return response;
        } catch (Exception e) {
            String mensajeError = "Error inesperado al listar los ajustes de inventario: " + e.getMessage();
            System.err.println("[ERROR] " + mensajeError);
            ResponseListaAjuste response = new ResponseListaAjuste();
            response.setExito(false);
            response.setMessage(mensajeError);
            response.setAjustes(List.of());
            return response;
        }
    }
}