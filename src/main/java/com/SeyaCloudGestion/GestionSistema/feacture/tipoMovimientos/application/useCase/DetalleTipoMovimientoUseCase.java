package com.SeyaCloudGestion.GestionSistema.feacture.tipoMovimientos.application.useCase;

import com.SeyaCloudGestion.GestionSistema.feacture.tipoMovimientos.application.dto.request.RequestDetalleTipoMovimiento;
import com.SeyaCloudGestion.GestionSistema.feacture.tipoMovimientos.application.dto.response.ResponseDetalleTipoMovimiento;
import com.SeyaCloudGestion.GestionSistema.feacture.tipoMovimientos.domain.services.TipoMovimientoService;
import org.springframework.stereotype.Component;

@Component
public class DetalleTipoMovimientoUseCase {
    private final TipoMovimientoService tipoMovimientoService;

    public DetalleTipoMovimientoUseCase(TipoMovimientoService tipoMovimientoService) {
        this.tipoMovimientoService = tipoMovimientoService;
    }
    public ResponseDetalleTipoMovimiento DetalleTipoMovimiento(long idTipoMovimiento) {
        try {
            RequestDetalleTipoMovimiento request = new RequestDetalleTipoMovimiento();
            request.setIdTipoMovimiento(idTipoMovimiento);

            ResponseDetalleTipoMovimiento response = tipoMovimientoService.DetalleTipoMovimiento(request);

            if (response.isExito()) {
            }

            return response;

        } catch (IllegalArgumentException | SecurityException e) {
            ResponseDetalleTipoMovimiento response = new ResponseDetalleTipoMovimiento();
            response.setExito(false);
            response.setMessage(e.getMessage());
            return response;

        } catch (Exception e) {
            String mensajeError = "Error inesperado al ver el detalle del tipo de movimiento: " + e.getMessage();
            System.err.println("[ERROR] " + mensajeError);

            ResponseDetalleTipoMovimiento response = new ResponseDetalleTipoMovimiento();
            response.setExito(false);
            response.setMessage(mensajeError);
            return response;
        }
    }
}
