package com.SeyaCloudGestion.GestionSistema.feacture.tipoMovimientos.application.useCase;

import com.SeyaCloudGestion.GestionSistema.feacture.kardex.infraestructure.persistence.model.TipoMovimientoKardex;
import com.SeyaCloudGestion.GestionSistema.feacture.tipoMovimientos.application.dto.request.RequestDetallePorCodigoTipoMovimiento;
import com.SeyaCloudGestion.GestionSistema.feacture.tipoMovimientos.application.dto.request.RequestDetalleTipoMovimiento;
import com.SeyaCloudGestion.GestionSistema.feacture.tipoMovimientos.application.dto.response.ResponseDetalleTipoMovimiento;
import com.SeyaCloudGestion.GestionSistema.feacture.tipoMovimientos.domain.services.TipoMovimientoService;
import org.springframework.stereotype.Component;

@Component
public class DetallePorCodigoTipoMovimientoUseCase {
    private final TipoMovimientoService tipoMovimientoService;

    public DetallePorCodigoTipoMovimientoUseCase(TipoMovimientoService tipoMovimientoService) {
        this.tipoMovimientoService = tipoMovimientoService;
    }
    public ResponseDetalleTipoMovimiento DetalleTipoMovimiento(TipoMovimientoKardex codigo) {
        try {
            RequestDetallePorCodigoTipoMovimiento request = new RequestDetallePorCodigoTipoMovimiento();
            request.setCodigo(codigo);

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
