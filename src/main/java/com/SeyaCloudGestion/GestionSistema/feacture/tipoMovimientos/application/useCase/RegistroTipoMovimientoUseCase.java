package com.SeyaCloudGestion.GestionSistema.feacture.tipoMovimientos.application.useCase;

import com.SeyaCloudGestion.GestionSistema.feacture.tipoMovimientos.application.dto.request.RequestRegistroTipoMovimiento;
import com.SeyaCloudGestion.GestionSistema.feacture.tipoMovimientos.application.dto.response.ResponseRegistroTipoMovimiento;
import com.SeyaCloudGestion.GestionSistema.feacture.tipoMovimientos.domain.services.TipoMovimientoService;
import org.springframework.stereotype.Component;

@Component
public class RegistroTipoMovimientoUseCase {
    private final TipoMovimientoService tipoMovimientoService;

    public RegistroTipoMovimientoUseCase(TipoMovimientoService tipoMovimientoService) {
        this.tipoMovimientoService = tipoMovimientoService;
    }
    public ResponseRegistroTipoMovimiento RegistroTipoMovimiento(RequestRegistroTipoMovimiento request) {
        try {
            //verifiaciones
            if (request.getEsEntrada() == 1) {
                if (!request.getCodigo().esIngreso()) {
                    throw new IllegalArgumentException(
                            "el movimiento es de entrada , el código seleccionado ("
                                    + request.getCodigo() + ") debe comenzar con 'INGRESO_'."
                    );
                }
            } else if (request.getEsEntrada() == 0) {
                if (!request.getCodigo().esEgreso()) {
                    throw new IllegalArgumentException(
                            "el movimiento es de egreso , el código seleccionado ("
                                    + request.getCodigo() + ") debe comenzar con 'EGRESO_'."
                    );
                }
            }
            ResponseRegistroTipoMovimiento response = tipoMovimientoService.RegistroTipoMovimiento(request);

            if (response.isExito()) {
            }

            return response;

        } catch (IllegalArgumentException | SecurityException e) {
            ResponseRegistroTipoMovimiento response = new ResponseRegistroTipoMovimiento();
            response.setExito(false);
            response.setMessage(e.getMessage());
            return response;

        } catch (Exception e) {
            String mensajeError = "Error inesperado al registrar el tipo de movimiento: " + e.getMessage();
            System.err.println("[ERROR] " + mensajeError);

            ResponseRegistroTipoMovimiento response = new ResponseRegistroTipoMovimiento();
            response.setExito(false);
            response.setMessage(mensajeError);
            return response;
        }
    }
}
