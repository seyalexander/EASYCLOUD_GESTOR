package com.SeyaCloudGestion.GestionSistema.feacture.devoluciones.application.useCase;

import com.SeyaCloudGestion.GestionSistema.feacture.devoluciones.application.dto.request.RequestRegistroDevolucion;
import com.SeyaCloudGestion.GestionSistema.feacture.devoluciones.application.dto.response.ResponseRegistroDevolucion;
import com.SeyaCloudGestion.GestionSistema.feacture.devoluciones.domain.services.DevolucionService;
import com.SeyaCloudGestion.GestionSistema.feacture.cuentasPorCobrar.application.useCase.AnularCuentaPorCobrarUseCase;
import com.SeyaCloudGestion.GestionSistema.feacture.cuentasPorCobrar.application.dto.request.RequestAnularCuentaPorCobrar;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class RegistroDevolucionUseCase {

    private final DevolucionService devolucionService;
    private final AnularCuentaPorCobrarUseCase anularCuentaPorCobrarUseCase;

    public RegistroDevolucionUseCase(
            DevolucionService devolucionService,
            AnularCuentaPorCobrarUseCase anularCuentaPorCobrarUseCase
    ) {
        this.devolucionService = devolucionService;
        this.anularCuentaPorCobrarUseCase = anularCuentaPorCobrarUseCase;
    }

    @Transactional("sqlServerTransactionManager")
    public ResponseRegistroDevolucion registroDevolucion(RequestRegistroDevolucion request) {
        try {

            ResponseRegistroDevolucion response = devolucionService.RegistroDevolucion(request);

            if (!response.isExito()) {
                throw new IllegalArgumentException("No se pudo procesar el reingreso de mercadería: " + response.getMessage());
            }
            /*
            if (request.isVentaACredito() && request.getIdCuentaPorCobrar() > 0) {
                RequestAnularCuentaPorCobrar requestAnulacionCxc = new RequestAnularCuentaPorCobrar();
                requestAnulacionCxc.setIdCuentaPorCobrar(request.getIdCuentaPorCobrar());

                var responseCxc = anularCuentaPorCobrarUseCase.EdicionAllCuentasPorCobrar(requestAnulacionCxc);

                if (!responseCxc.isExito()) {
                    throw new IllegalArgumentException("Mercadería devuelta, pero falló la anulación del saldo: " + responseCxc.getMessage());
                }
            }
             */

            return response;

        } catch (IllegalArgumentException | SecurityException e) {
            ResponseRegistroDevolucion response = new ResponseRegistroDevolucion();
            response.setExito(false);
            response.setMessage(e.getMessage());
            return response;
        } catch (Exception e) {
            String mensajeError = "Error inesperado al registrar la devolución: " + e.getMessage();
            System.err.println("[ERROR] " + mensajeError);
            ResponseRegistroDevolucion response = new ResponseRegistroDevolucion();
            response.setExito(false);
            response.setMessage(mensajeError);
            return response;
        }
    }
}