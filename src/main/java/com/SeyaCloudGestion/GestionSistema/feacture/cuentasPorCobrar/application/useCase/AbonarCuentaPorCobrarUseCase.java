package com.SeyaCloudGestion.GestionSistema.feacture.cuentasPorCobrar.application.useCase;

import com.SeyaCloudGestion.GestionSistema.feacture.cuentasPorCobrar.application.dto.request.RequestAbonarCuentaPorCobrar;
import com.SeyaCloudGestion.GestionSistema.feacture.cuentasPorCobrar.application.dto.response.ResponseAbonarCuentaPorCobrar;
import com.SeyaCloudGestion.GestionSistema.feacture.cuentasPorCobrar.application.dto.response.ResponseDetalleCuentasPorCobrar;
import com.SeyaCloudGestion.GestionSistema.feacture.cuentasPorCobrar.domain.services.CuentasPorCobrarService;
import com.SeyaCloudGestion.GestionSistema.feacture.cuentasPorCobrar.infraestructure.persistence.model.EstadoCuenta;
import org.springframework.stereotype.Component;

@Component
public class AbonarCuentaPorCobrarUseCase {
    private final CuentasPorCobrarService cuentasPorCobrarService;
    private final DetalleCuentasPorCobrarUseCase detalleCuentasPorCobrarUseCase;

    public AbonarCuentaPorCobrarUseCase(CuentasPorCobrarService cuentasPorCobrarService, DetalleCuentasPorCobrarUseCase detalleCuentasPorCobrarUseCase) {
        this.cuentasPorCobrarService = cuentasPorCobrarService;
        this.detalleCuentasPorCobrarUseCase = detalleCuentasPorCobrarUseCase;
    }
    public ResponseAbonarCuentaPorCobrar EdicionAllCuentasPorCobrar(RequestAbonarCuentaPorCobrar request) {
        try {
            //get id cuenta
            ResponseDetalleCuentasPorCobrar responseBDCuenta = detalleCuentasPorCobrarUseCase.DetalleCuentasPorCobrar(request.getIdCuentaPorCobrar());
            if (!responseBDCuenta.isExito() || responseBDCuenta.getCuentasPorCobrar() == null) {
                throw new IllegalArgumentException("La cuneta a abonar no existe.");
            }

            EstadoCuenta estado = responseBDCuenta.getCuentasPorCobrar().getEstado();
            //verificar que no este pagada
            if (estado.equals(EstadoCuenta.PAGADO)) {
                throw new IllegalArgumentException("No se puede abonar una cuenta ya saldada.");
            }
            //anulada
            if (estado.equals(EstadoCuenta.ANULADO)) {
                throw new IllegalArgumentException("No se puede abonar una cuenta anulada.");
            }
            //verificar el monto abonado
            double montoPendienteBD= responseBDCuenta.getCuentasPorCobrar().getMontoPendiente();
            if (request.getMontoAbonado()>montoPendienteBD) {
                throw new IllegalArgumentException("No se puede abonar mas de lo que debe.");
            }
            double montoPendienteActual = montoPendienteBD-request.getMontoAbonado();
            if (montoPendienteActual==0){
                estado=EstadoCuenta.PAGADO;
            }
            else {
                estado=EstadoCuenta.PENDIENTE;
            }
            ResponseAbonarCuentaPorCobrar response = cuentasPorCobrarService.AbonarCuentasPorCobrar(request,estado,montoPendienteActual);

            if (response.isExito()) {
            }

            return response;

        } catch (IllegalArgumentException | SecurityException e) {
            ResponseAbonarCuentaPorCobrar response = new ResponseAbonarCuentaPorCobrar();
            response.setExito(false);
            response.setMessage(e.getMessage());
            return response;

        } catch (Exception e) {
            String mensajeError = "Error inesperado al abonar la cuenta por cobrar: " + e.getMessage();
            System.err.println("[ERROR] " + mensajeError);

            ResponseAbonarCuentaPorCobrar response = new ResponseAbonarCuentaPorCobrar();
            response.setExito(false);
            response.setMessage(mensajeError);
            return response;
        }
    }
}