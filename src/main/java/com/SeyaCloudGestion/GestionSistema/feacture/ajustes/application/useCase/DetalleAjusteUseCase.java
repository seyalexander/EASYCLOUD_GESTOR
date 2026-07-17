package com.SeyaCloudGestion.GestionSistema.feacture.ajustes.application.useCase;

import com.SeyaCloudGestion.GestionSistema.feacture.ajustes.application.dto.request.RequestDetalleAjuste;
import com.SeyaCloudGestion.GestionSistema.feacture.ajustes.application.dto.response.ResponseDetalleAjuste;
import com.SeyaCloudGestion.GestionSistema.feacture.ajustes.domain.services.AjustesService;
import org.springframework.stereotype.Component;

@Component
public class DetalleAjusteUseCase {

    private final AjustesService ajustesService;

    public DetalleAjusteUseCase(AjustesService ajustesService) {
        this.ajustesService = ajustesService;
    }

    public ResponseDetalleAjuste detalleAjustes(long idAjuste) {
        try {
            RequestDetalleAjuste request = new RequestDetalleAjuste();
            request.setIdAjuste(idAjuste);

            ResponseDetalleAjuste response = ajustesService.DetalleAjustes(request);

            if (response.isExito()) {
            }

            return response;

        } catch (IllegalArgumentException | SecurityException e) {
            ResponseDetalleAjuste response = new ResponseDetalleAjuste();
            response.setExito(false);
            response.setMessage(e.getMessage());
            return response;
        } catch (Exception e) {
            String mensajeError = "Error inesperado al obtener el detalle del ajuste de inventario: " + e.getMessage();
            System.err.println("[ERROR] " + mensajeError);
            ResponseDetalleAjuste response = new ResponseDetalleAjuste();
            response.setExito(false);
            response.setMessage(mensajeError);
            return response;
        }
    }
}