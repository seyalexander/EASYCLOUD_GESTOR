package com.SeyaCloudGestion.GestionSistema.feacture.pagoProveedores.application.useCase;

import com.SeyaCloudGestion.GestionSistema.feacture.cuentasPorCobrar.infraestructure.persistence.model.EstadoCuenta;
import com.SeyaCloudGestion.GestionSistema.feacture.cuentasPorPagar.application.dto.response.ResponseDetalleCuentasPorPagar;
import com.SeyaCloudGestion.GestionSistema.feacture.cuentasPorPagar.application.useCase.DetalleCuentasPorPagarUseCase;
import com.SeyaCloudGestion.GestionSistema.feacture.movimientosCaja.application.dto.request.RequestRegistroMovimientoCaja;
import com.SeyaCloudGestion.GestionSistema.feacture.movimientosCaja.application.dto.response.ResponseRegistroMovimientoCaja;
import com.SeyaCloudGestion.GestionSistema.feacture.movimientosCaja.application.useCase.RegistroMovimientoCajaUseCase;
import com.SeyaCloudGestion.GestionSistema.feacture.movimientosCaja.infraestructure.persistence.model.Movimiento;
import com.SeyaCloudGestion.GestionSistema.feacture.pagoProveedores.application.dto.request.RequestRegistroPagoProveedor;
import com.SeyaCloudGestion.GestionSistema.feacture.pagoProveedores.application.dto.response.ResponseRegistroPagoProveedor;
import com.SeyaCloudGestion.GestionSistema.feacture.pagoProveedores.domain.services.PagoProveedorService;
import com.SeyaCloudGestion.GestionSistema.feacture.tipoPagos.application.dto.response.ResponseDetalleTipoPagos;
import com.SeyaCloudGestion.GestionSistema.feacture.tipoPagos.application.useCase.DetalleTipoPagosUseCase;
import com.SeyaCloudGestion.GestionSistema.feacture.turnoCaja.application.dto.response.ResponseDetalleTurnoCaja;
import com.SeyaCloudGestion.GestionSistema.feacture.turnoCaja.application.useCase.DetalleTurnoCajaUseCase;
import com.SeyaCloudGestion.GestionSistema.feacture.turnoCaja.infraestructure.persistence.model.EstadoCaja;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

@Component
public class RegistroPagoProveedorUseCase {

    private final PagoProveedorService pagoProveedorService;
    private final DetalleCuentasPorPagarUseCase detalleCuentasPorPagarUseCase;
    private final DetalleTipoPagosUseCase detalleTipoPagosUseCase;
    private final DetalleTurnoCajaUseCase detalleTurnoCajaUseCase;
    private final RegistroMovimientoCajaUseCase registroMovimientoCajaUseCase;

    public RegistroPagoProveedorUseCase(
            PagoProveedorService pagoProveedorService, DetalleCuentasPorPagarUseCase detalleCuentasPorPagarUseCase, DetalleTipoPagosUseCase detalleTipoPagosUseCase, DetalleTurnoCajaUseCase detalleTurnoCajaUseCase, RegistroMovimientoCajaUseCase registroMovimientoCajaUseCase) {
        this.pagoProveedorService = pagoProveedorService;
        this.detalleCuentasPorPagarUseCase = detalleCuentasPorPagarUseCase;
        this.detalleTipoPagosUseCase = detalleTipoPagosUseCase;
        this.detalleTurnoCajaUseCase = detalleTurnoCajaUseCase;
        this.registroMovimientoCajaUseCase = registroMovimientoCajaUseCase;
    }
    @Transactional("sqlServerTransactionManager")
    public ResponseRegistroPagoProveedor RegistroPagoProveedor(RequestRegistroPagoProveedor request) {
        try {
            //get turno abierto
            ResponseDetalleTurnoCaja responseBDCaja = detalleTurnoCajaUseCase.DetalleTurnoCaja(request.getIdCaja(), EstadoCaja.ABIERTO);
            if (!responseBDCaja.isExito() || responseBDCaja.getTurnoCaja() == null) {
                throw new IllegalArgumentException("La caja no se encuentra abierta.");
            }
            long idTurnoCaja = responseBDCaja.getTurnoCaja().getIdTurnoCaja();
            //validar la cuenta por pagar
            ResponseDetalleCuentasPorPagar responseBDcuenta = detalleCuentasPorPagarUseCase.detalleCuentaPorPagar(request.getIdCuentaPorPagar());

            if (!responseBDcuenta.isExito()|| responseBDcuenta.getCuentasPorPagar() == null) {
                throw new IllegalArgumentException("No se encontró la cuenta por pagar con el id proporciano ");
            }
            //validar el tipo de pago
            ResponseDetalleTipoPagos responseBDtipoPago = detalleTipoPagosUseCase.DetalleTipoPagos(request.getIdTipoPago());
            if (!responseBDtipoPago.isExito()|| responseBDtipoPago.getTipoPagos() == null) {
                throw new IllegalArgumentException("No se encontró el tipo de pago con el id proporciano ");
            }
            //verificamos el monto a cobrar
            if (request.getMontoPagado() > responseBDcuenta.getCuentasPorPagar().getMontoPendiente()) {
                throw new IllegalArgumentException("El monto a pagar no puede superar el monto pendiente de la cuenta por pagar.");
            }
            //verificamos que no estea ya cancelada la cuenta por pagar
            if (responseBDcuenta.getCuentasPorPagar().getEstado().equals(EstadoCuenta.PAGADO)) {
                throw new IllegalArgumentException("La cuenta ya esta cancelada, no se puede registrar un pago adicional.");
            }
            //registramos la salida de la caja
            RequestRegistroMovimientoCaja movCaja = new RequestRegistroMovimientoCaja();
            movCaja.setIdTurnoCaja(idTurnoCaja);
            movCaja.setMonto(request.getMontoPagado());
            movCaja.setMovimiento(Movimiento.EGRESO);
            movCaja.setIdTipoPago(request.getIdTipoPago());
            movCaja.setConcepto("Abono (" + responseBDtipoPago.getTipoPagos().getDescripcion() + ") a cuenta por pagar - CP Nro: " + request.getIdCuentaPorPagar());

            ResponseRegistroMovimientoCaja responseMov = registroMovimientoCajaUseCase.registroMovimientoCaja(movCaja);
            if (!responseMov.isExito()) {
                throw new RuntimeException("No se pudo registrar el movimiento en la caja: " + responseMov.getMessage());
            }

            ResponseRegistroPagoProveedor response = pagoProveedorService.RegistroPagoProveedor(request);
            if (response.isExito()) {}

            return response;

        } catch (IllegalArgumentException | SecurityException e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            ResponseRegistroPagoProveedor response = new ResponseRegistroPagoProveedor();
            response.setExito(false);
            response.setMessage(e.getMessage());
            return response;
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            String mensajeError = "Error inesperado al registrar el pago del proveedor: " + e.getMessage();
            System.err.println("[ERROR] " + mensajeError);
            ResponseRegistroPagoProveedor response = new ResponseRegistroPagoProveedor();
            response.setExito(false);
            response.setMessage(mensajeError);
            return response;
        }
    }
}