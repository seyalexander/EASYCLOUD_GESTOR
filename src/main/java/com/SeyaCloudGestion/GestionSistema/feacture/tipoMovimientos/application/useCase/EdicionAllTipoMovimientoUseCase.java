package com.SeyaCloudGestion.GestionSistema.feacture.tipoMovimientos.application.useCase;

import com.SeyaCloudGestion.GestionSistema.feacture.tipoMovimientos.application.dto.request.RequestEditarAllTipoMovimiento;
import com.SeyaCloudGestion.GestionSistema.feacture.tipoMovimientos.application.dto.response.ResponseEditarAllTipoMovimiento;
import com.SeyaCloudGestion.GestionSistema.feacture.tipoMovimientos.domain.services.TipoMovimientoService;
import org.springframework.stereotype.Component;

@Component
public class EdicionAllTipoMovimientoUseCase {
    private final TipoMovimientoService tipoMovimientoService;

    public EdicionAllTipoMovimientoUseCase(TipoMovimientoService tipoMovimientoService) {
        this.tipoMovimientoService = tipoMovimientoService;
    }
    public ResponseEditarAllTipoMovimiento EdicionAllTipoMovimiento(RequestEditarAllTipoMovimiento request) {
        try {
            ResponseEditarAllTipoMovimiento response = tipoMovimientoService.EditarAllTipoMovimiento(request);

            if (response.isExito()) {
            }

            return response;

        } catch (IllegalArgumentException | SecurityException e) {
            ResponseEditarAllTipoMovimiento response = new ResponseEditarAllTipoMovimiento();
            response.setExito(false);
            response.setMessage(e.getMessage());
            return response;

        } catch (Exception e) {
            String mensajeError = "Error inesperado al actualizar el tipo de movimiento: " + e.getMessage();
            System.err.println("[ERROR] " + mensajeError);

            ResponseEditarAllTipoMovimiento response = new ResponseEditarAllTipoMovimiento();
            response.setExito(false);
            response.setMessage(mensajeError);
            return response;
        }
    }
}
