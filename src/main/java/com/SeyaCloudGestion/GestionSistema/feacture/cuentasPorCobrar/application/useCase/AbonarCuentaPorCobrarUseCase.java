package com.SeyaCloudGestion.GestionSistema.feacture.cuentasPorCobrar.application.useCase;

import com.SeyaCloudGestion.GestionSistema.feacture.cuentasPorCobrar.application.dto.request.RequestAbonarCuentaPorCobrar;
import com.SeyaCloudGestion.GestionSistema.feacture.cuentasPorCobrar.application.dto.response.ResponseAbonarCuentaPorCobrar;
import com.SeyaCloudGestion.GestionSistema.feacture.cuentasPorCobrar.application.dto.response.ResponseDetalleCuentasPorCobrar;
import com.SeyaCloudGestion.GestionSistema.feacture.cuentasPorCobrar.domain.services.CuentasPorCobrarService;
import com.SeyaCloudGestion.GestionSistema.feacture.cuentasPorCobrar.infraestructure.persistence.model.EstadoCuenta;
import com.SeyaCloudGestion.GestionSistema.feacture.movimientosCaja.application.dto.request.RequestRegistroMovimientoCaja;
import com.SeyaCloudGestion.GestionSistema.feacture.movimientosCaja.application.dto.response.ResponseRegistroMovimientoCaja;
import com.SeyaCloudGestion.GestionSistema.feacture.movimientosCaja.application.useCase.RegistroMovimientoCajaUseCase;
import com.SeyaCloudGestion.GestionSistema.feacture.movimientosCaja.infraestructure.persistence.model.Movimiento;
import com.SeyaCloudGestion.GestionSistema.feacture.tipoPagos.application.dto.response.ResponseDetalleTipoPagos;
import com.SeyaCloudGestion.GestionSistema.feacture.tipoPagos.application.useCase.DetalleTipoPagosUseCase;
import com.SeyaCloudGestion.GestionSistema.feacture.turnoCaja.application.dto.response.ResponseDetalleTurnoCaja;
import com.SeyaCloudGestion.GestionSistema.feacture.turnoCaja.application.useCase.DetalleTurnoCajaUseCase;
import com.SeyaCloudGestion.GestionSistema.feacture.turnoCaja.infraestructure.persistence.model.EstadoCaja;
import org.springframework.stereotype.Component;

@Component
public class AbonarCuentaPorCobrarUseCase {
    private final CuentasPorCobrarService cuentasPorCobrarService;
    private final DetalleCuentasPorCobrarUseCase detalleCuentasPorCobrarUseCase;
    private final DetalleTipoPagosUseCase detalleTipoPagosUseCase;
    private final DetalleTurnoCajaUseCase detalleTurnoCajaUseCase;
    private final RegistroMovimientoCajaUseCase registroMovimientoCajaUseCase;

    public AbonarCuentaPorCobrarUseCase(CuentasPorCobrarService cuentasPorCobrarService, DetalleCuentasPorCobrarUseCase detalleCuentasPorCobrarUseCase, DetalleTipoPagosUseCase detalleTipoPagosUseCase, DetalleTurnoCajaUseCase detalleTurnoCajaUseCase, RegistroMovimientoCajaUseCase registroMovimientoCajaUseCase) {
        this.cuentasPorCobrarService = cuentasPorCobrarService;
        this.detalleCuentasPorCobrarUseCase = detalleCuentasPorCobrarUseCase;
        this.detalleTipoPagosUseCase = detalleTipoPagosUseCase;
        this.detalleTurnoCajaUseCase = detalleTurnoCajaUseCase;
        this.registroMovimientoCajaUseCase = registroMovimientoCajaUseCase;
    }
    public ResponseAbonarCuentaPorCobrar EdicionAllCuentasPorCobrar(RequestAbonarCuentaPorCobrar request) {
        try {
            //get turno abierto
            ResponseDetalleTurnoCaja responseBDCaja = detalleTurnoCajaUseCase.DetalleTurnoCaja(request.getIdCaja(), EstadoCaja.ABIERTO);
            if (!responseBDCaja.isExito() || responseBDCaja.getTurnoCaja() == null) {
                throw new IllegalArgumentException("La caja no se encuentra abierta.");
            }
            long idTurnoCaja = responseBDCaja.getTurnoCaja().getIdTurnoCaja();
            //validar el tipo de pago
            ResponseDetalleTipoPagos responseBDtipoPago = detalleTipoPagosUseCase.DetalleTipoPagos(request.getIdTipoPago());
            if (!responseBDtipoPago.isExito()|| responseBDtipoPago.getTipoPagos() == null) {
                throw new IllegalArgumentException("No se encontró el tipo de pago con el id proporciano ");
            }
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

            //registramos la salida de la caja
            RequestRegistroMovimientoCaja movCaja = new RequestRegistroMovimientoCaja();
            movCaja.setIdTurnoCaja(idTurnoCaja);
            movCaja.setMonto(request.getMontoAbonado());
            movCaja.setMovimiento(Movimiento.INGRESO);
            movCaja.setIdTipoPago(request.getIdTipoPago());
            movCaja.setConcepto("Abono (" + responseBDtipoPago.getTipoPagos().getDescripcion() + ") a cuenta por pagar - CP Nro: " + request.getIdCuentaPorCobrar());

            ResponseRegistroMovimientoCaja responseMov = registroMovimientoCajaUseCase.registroMovimientoCaja(movCaja);
            if (!responseMov.isExito()) {
                throw new RuntimeException("No se pudo registrar el movimiento en la caja: " + responseMov.getMessage());
            }
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