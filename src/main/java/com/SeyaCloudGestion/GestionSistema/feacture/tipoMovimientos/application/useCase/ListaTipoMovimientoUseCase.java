package com.SeyaCloudGestion.GestionSistema.feacture.tipoMovimientos.application.useCase;

import com.SeyaCloudGestion.GestionSistema.feacture.tipoMovimientos.application.dto.request.RequestListaTipoMovimiento;
import com.SeyaCloudGestion.GestionSistema.feacture.tipoMovimientos.application.dto.response.ResponseListaTipoMovimiento;
import com.SeyaCloudGestion.GestionSistema.feacture.tipoMovimientos.domain.services.TipoMovimientoService;
import org.springframework.stereotype.Component;

@Component
public class ListaTipoMovimientoUseCase {
    private final TipoMovimientoService tipoMovimientoService;

    public ListaTipoMovimientoUseCase(TipoMovimientoService tipoMovimientoService) {
        this.tipoMovimientoService = tipoMovimientoService;
    }
    public ResponseListaTipoMovimiento ListaTipoMovimiento(RequestListaTipoMovimiento request) {
        try {
            ResponseListaTipoMovimiento response = tipoMovimientoService.ListaTipoMovimiento(request);

            if (response.isExito()) {
            }

            return response;

        } catch (IllegalArgumentException | SecurityException e) {
            ResponseListaTipoMovimiento response = new ResponseListaTipoMovimiento();
            response.setExito(false);
            response.setMessage(e.getMessage());
            response.setTipoMovimientos(java.util.List.of());
            return response;

        } catch (Exception e) {
            String mensajeError = "Error inesperado al listar los tipos de movimiento: " + e.getMessage();
            System.err.println("[ERROR] " + mensajeError);

            ResponseListaTipoMovimiento response = new ResponseListaTipoMovimiento();
            response.setExito(false);
            response.setMessage(mensajeError);
            response.setTipoMovimientos(java.util.List.of());
            return response;
        }
    }
}
