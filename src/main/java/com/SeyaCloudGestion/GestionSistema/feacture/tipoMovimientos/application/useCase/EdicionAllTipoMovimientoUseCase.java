package com.SeyaCloudGestion.GestionSistema.feacture.tipoMovimientos.application.useCase;

import com.SeyaCloudGestion.GestionSistema.common.exceptions.ResourceNotFoundException;
import com.SeyaCloudGestion.GestionSistema.feacture.tipoMovimientos.application.dto.request.RequestDetalleTipoMovimiento;
import com.SeyaCloudGestion.GestionSistema.feacture.tipoMovimientos.application.dto.request.RequestEditarAllTipoMovimiento;
import com.SeyaCloudGestion.GestionSistema.feacture.tipoMovimientos.application.dto.response.ResponseDetalleTipoMovimiento;
import com.SeyaCloudGestion.GestionSistema.feacture.tipoMovimientos.application.dto.response.ResponseEditarAllTipoMovimiento;
import com.SeyaCloudGestion.GestionSistema.feacture.tipoMovimientos.domain.services.TipoMovimientoService;
import com.SeyaCloudGestion.GestionSistema.feacture.tipoMovimientos.domain.validations.VerificarCambiosTipoMovimiento;
import org.springframework.stereotype.Component;

@Component
public class EdicionAllTipoMovimientoUseCase {
    private final TipoMovimientoService tipoMovimientoService;
    private final VerificarCambiosTipoMovimiento verificarCambiosTipoMovimiento;

    public EdicionAllTipoMovimientoUseCase(TipoMovimientoService tipoMovimientoService, VerificarCambiosTipoMovimiento verificarCambiosTipoMovimiento) {
        this.tipoMovimientoService = tipoMovimientoService;
        this.verificarCambiosTipoMovimiento = verificarCambiosTipoMovimiento;
    }
    public ResponseEditarAllTipoMovimiento EdicionAllTipoMovimiento(RequestEditarAllTipoMovimiento request) {
        try {
            //get id
            RequestDetalleTipoMovimiento requestDetalle = new RequestDetalleTipoMovimiento();
            requestDetalle.setIdTipoMovimiento(request.getIdTipoMovimiento());

            ResponseDetalleTipoMovimiento detalleBD= tipoMovimientoService.DetalleTipoMovimiento(requestDetalle);

            if (!detalleBD.isExito() || detalleBD.getTipoMovimiento() == null) {
                throw new ResourceNotFoundException("El Id no existe.");
            }
            //verificar edicion
            if (!verificarCambiosTipoMovimiento.verificarCambios(detalleBD.getTipoMovimiento(), request)) {
                throw new IllegalArgumentException("No se detectaron cambios para actualizar.");
            }

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
