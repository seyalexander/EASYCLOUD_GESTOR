package com.SeyaCloudGestion.GestionSistema.feacture.cuentasPorCobrar.application.useCase;

import com.SeyaCloudGestion.GestionSistema.feacture.cuentasPorCobrar.application.dto.request.RequestAbonarCuentaPorCobrar;
import com.SeyaCloudGestion.GestionSistema.feacture.cuentasPorCobrar.application.dto.request.RequestAnularCuentaPorCobrar;
import com.SeyaCloudGestion.GestionSistema.feacture.cuentasPorCobrar.application.dto.response.ResponseAbonarCuentaPorCobrar;
import com.SeyaCloudGestion.GestionSistema.feacture.cuentasPorCobrar.application.dto.response.ResponseAnularCuentaPorCobrar;
import com.SeyaCloudGestion.GestionSistema.feacture.cuentasPorCobrar.application.dto.response.ResponseDetalleCuentasPorCobrar;
import com.SeyaCloudGestion.GestionSistema.feacture.cuentasPorCobrar.domain.services.CuentasPorCobrarService;
import com.SeyaCloudGestion.GestionSistema.feacture.cuentasPorCobrar.infraestructure.persistence.model.EstadoCuenta;
import org.springframework.stereotype.Component;

@Component
public class AnularCuentaPorCobrarUseCase {
    private final CuentasPorCobrarService cuentasPorCobrarService;
    private final DetalleCuentasPorCobrarUseCase detalleCuentasPorCobrarUseCase;

    public AnularCuentaPorCobrarUseCase(CuentasPorCobrarService cuentasPorCobrarService, DetalleCuentasPorCobrarUseCase detalleCuentasPorCobrarUseCase) {
        this.cuentasPorCobrarService = cuentasPorCobrarService;
        this.detalleCuentasPorCobrarUseCase = detalleCuentasPorCobrarUseCase;
    }
    public ResponseAnularCuentaPorCobrar EdicionAllCuentasPorCobrar(RequestAnularCuentaPorCobrar request) {
        try {
            //get id cuenta
            ResponseDetalleCuentasPorCobrar responseBDCuenta = detalleCuentasPorCobrarUseCase.DetalleCuentasPorCobrar(request.getIdCuentaPorCobrar());
            if (!responseBDCuenta.isExito() || responseBDCuenta.getCuentasPorCobrar() == null) {
                throw new IllegalArgumentException("La cuneta a anular no existe.");
            }

            EstadoCuenta estado = responseBDCuenta.getCuentasPorCobrar().getEstado();
            //verificar que no este pagada
            if (estado.equals(EstadoCuenta.PAGADO)) {
                throw new IllegalArgumentException("No se puede anular una cuenta ya saldada.");
            }
            //anulada
            if (estado.equals(EstadoCuenta.ANULADO)) {
                throw new IllegalArgumentException("No se puede anular una cuenta que ya esta anulada.");
            }

            RequestAbonarCuentaPorCobrar requestAnular = new RequestAbonarCuentaPorCobrar();
            requestAnular.setIdCuentaPorCobrar(request.getIdCuentaPorCobrar());
            requestAnular.setFechaVencimiento(responseBDCuenta.getCuentasPorCobrar().getFechaVencimiento());
            double montoPendienteActual=0;
            estado = EstadoCuenta.ANULADO;

            ResponseAbonarCuentaPorCobrar responseAnular = cuentasPorCobrarService.AbonarCuentasPorCobrar(requestAnular,estado,montoPendienteActual);
            ResponseAnularCuentaPorCobrar response = new ResponseAnularCuentaPorCobrar();
            if (responseAnular.isExito()) {
                response.setExito(true);
            }else {
                response.setExito(false);
                response.setMessage(responseAnular.getMessage());
            }

            return response;

        } catch (IllegalArgumentException | SecurityException e) {
            ResponseAnularCuentaPorCobrar response = new ResponseAnularCuentaPorCobrar();
            response.setExito(false);
            response.setMessage(e.getMessage());
            return response;

        } catch (Exception e) {
            String mensajeError = "Error inesperado al anular la cuenta por cobrar: " + e.getMessage();
            System.err.println("[ERROR] " + mensajeError);

            ResponseAnularCuentaPorCobrar response = new ResponseAnularCuentaPorCobrar();
            response.setExito(false);
            response.setMessage(mensajeError);
            return response;
        }
    }
}