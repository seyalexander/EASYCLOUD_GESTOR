package com.SeyaCloudGestion.GestionSistema.feacture.tipoMovimientos.application.useCase;

import com.SeyaCloudGestion.GestionSistema.feacture.tipoMovimientos.application.dto.request.RequestEditarEstadoTipoMovimiento;
import com.SeyaCloudGestion.GestionSistema.feacture.tipoMovimientos.application.dto.response.ResponseEditarEstadoTipoMovimiento;
import com.SeyaCloudGestion.GestionSistema.feacture.tipoMovimientos.domain.services.TipoMovimientoService;
import org.springframework.stereotype.Component;

@Component
public class EdicionTipoMovimientoEstadoUseCase {
    private final TipoMovimientoService tipoMovimientoService;

    public EdicionTipoMovimientoEstadoUseCase(TipoMovimientoService tipoMovimientoService) {
        this.tipoMovimientoService = tipoMovimientoService;
    }
    public ResponseEditarEstadoTipoMovimiento AnularTipoMovimiento(long idTipoMovimiento) {
        try {
            RequestEditarEstadoTipoMovimiento request = new RequestEditarEstadoTipoMovimiento();
            request.setIdTipoMovimiento(idTipoMovimiento);

            ResponseEditarEstadoTipoMovimiento response = tipoMovimientoService.EditarEstadoTipoMovimiento(request, 0);

            if (response.isExito()) {
            }

            return response;

        } catch (IllegalArgumentException | SecurityException e) {
            ResponseEditarEstadoTipoMovimiento response = new ResponseEditarEstadoTipoMovimiento();
            response.setExito(false);
            response.setMessage(e.getMessage());
            return response;

        } catch (Exception e) {
            String mensajeError = "Error inesperado al actualizar el tipo de movimiento: " + e.getMessage();
            System.err.println("[ERROR] " + mensajeError);

            ResponseEditarEstadoTipoMovimiento response = new ResponseEditarEstadoTipoMovimiento();
            response.setExito(false);
            response.setMessage(mensajeError);
            return response;
        }
    }

    public ResponseEditarEstadoTipoMovimiento ActivarTipoMovimiento(long idTipoMovimiento) {
        try {
            RequestEditarEstadoTipoMovimiento request = new RequestEditarEstadoTipoMovimiento();
            request.setIdTipoMovimiento(idTipoMovimiento);

            ResponseEditarEstadoTipoMovimiento response = tipoMovimientoService.EditarEstadoTipoMovimiento(request, 1);

            if (response.isExito()) {
            }

            return response;

        } catch (IllegalArgumentException | SecurityException e) {
            ResponseEditarEstadoTipoMovimiento response = new ResponseEditarEstadoTipoMovimiento();
            response.setExito(false);
            response.setMessage(e.getMessage());
            return response;

        } catch (Exception e) {
            String mensajeError = "Error inesperado al actualizar el tipo de movimiento: " + e.getMessage();
            System.err.println("[ERROR] " + mensajeError);

            ResponseEditarEstadoTipoMovimiento response = new ResponseEditarEstadoTipoMovimiento();
            response.setExito(false);
            response.setMessage(mensajeError);
            return response;
        }
    }
}
