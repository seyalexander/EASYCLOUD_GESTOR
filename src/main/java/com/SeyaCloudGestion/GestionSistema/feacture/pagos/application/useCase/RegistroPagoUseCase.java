package com.SeyaCloudGestion.GestionSistema.feacture.pagos.application.useCase;

import com.SeyaCloudGestion.GestionSistema.feacture.cuentasPorCobrar.application.dto.response.ResponseDetalleCuentasPorCobrar;
import com.SeyaCloudGestion.GestionSistema.feacture.cuentasPorCobrar.application.useCase.DetalleCuentasPorCobrarUseCase;
import com.SeyaCloudGestion.GestionSistema.feacture.cuentasPorCobrar.infraestructure.persistence.model.EstadoCuenta;
import com.SeyaCloudGestion.GestionSistema.feacture.movimientosCaja.application.dto.request.RequestRegistroMovimientoCaja;
import com.SeyaCloudGestion.GestionSistema.feacture.movimientosCaja.application.useCase.RegistroMovimientoCajaUseCase;
import com.SeyaCloudGestion.GestionSistema.feacture.movimientosCaja.domain.services.MovimientoCajaService;
import com.SeyaCloudGestion.GestionSistema.feacture.movimientosCaja.infraestructure.persistence.model.Movimiento;
import com.SeyaCloudGestion.GestionSistema.feacture.pagos.application.dto.request.RequestRegistroPago;
import com.SeyaCloudGestion.GestionSistema.feacture.pagos.application.dto.response.ResponseRegistroPago;
import com.SeyaCloudGestion.GestionSistema.feacture.pagos.domain.services.PagoService;
import com.SeyaCloudGestion.GestionSistema.feacture.tipoPagos.application.dto.response.ResponseDetalleTipoPagos;
import com.SeyaCloudGestion.GestionSistema.feacture.tipoPagos.application.useCase.DetalleTipoPagosUseCase;
import com.SeyaCloudGestion.GestionSistema.feacture.venta.application.dto.response.ResponseDetalleVenta;
import com.SeyaCloudGestion.GestionSistema.feacture.venta.application.useCase.DetalleVentaUseCase;
import com.SeyaCloudGestion.GestionSistema.feacture.venta.infraestructure.persistence.model.CondicionPago;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class RegistroPagoUseCase {

    private final PagoService pagoService;
    private final DetalleVentaUseCase detalleVentaUseCase;
    private final DetalleTipoPagosUseCase detalleTipoPagosUseCase;
    private final RegistroMovimientoCajaUseCase registroMovimientoCajaUseCase;
    public RegistroPagoUseCase(PagoService pagoService, DetalleVentaUseCase detalleVentaUseCase, DetalleTipoPagosUseCase detalleTipoPagosUseCase, RegistroMovimientoCajaUseCase registroMovimientoCajaUseCase) {
        this.pagoService = pagoService;
        this.detalleVentaUseCase = detalleVentaUseCase;
        this.detalleTipoPagosUseCase = detalleTipoPagosUseCase;
        this.registroMovimientoCajaUseCase = registroMovimientoCajaUseCase;
    }

    @Transactional("sqlServerTransactionManager")
    public ResponseRegistroPago registrarPago(RequestRegistroPago request) {
        try {
            //idventa
            ResponseDetalleVenta responsBDventa = detalleVentaUseCase.DetalleVenta(request.getIdVenta());
            if (!responsBDventa.isExito() || responsBDventa.getVenta() == null) {
                throw new IllegalArgumentException("La venta no existe.");
            }

            //idTipoPago
            ResponseDetalleTipoPagos responsBDTipoPago =detalleTipoPagosUseCase.DetalleTipoPagos(request.getIdTipoPago());
            if (!responsBDTipoPago.isExito() || responsBDTipoPago.getTipoPagos() == null) {
                throw new IllegalArgumentException("El tipo de pago no existe.");
            }
            //verificar el monto a pagar
            //si es al contado tendra que pagar todo (targeta and efectivo o ambos)
            if (responsBDventa.getVenta().getCondicionPago().equals(CondicionPago.CONTADO)){
                //si es mas
                if (responsBDventa.getVenta().getTotal()<request.getMonto()) {
                    throw new IllegalArgumentException("El monto ingresado supera el total requerido para esta venta al contado.");
                }
                //si es menos
                if (responsBDventa.getVenta().getTotal()>request.getMonto()) {
                    throw new IllegalArgumentException("El monto ingresado es insuficiente para liquidar esta venta al contado.");
                }
            }
            ResponseRegistroPago response = pagoService.RegistroPago(request);
            if (response.isExito()) {
                //set movimviiemto
                RequestRegistroMovimientoCaja requestMovimiento = new RequestRegistroMovimientoCaja();

                // get id turnocaja
                requestMovimiento.setIdTurnoCaja(responsBDventa.getVenta().getIdTurnoCaja());

               //sett
                requestMovimiento.setMovimiento(Movimiento.INGRESO);
                requestMovimiento.setMonto(request.getMonto());

                // concepto armado
                String nombreTipoPago = responsBDTipoPago.getTipoPagos().getDescripcion();
                requestMovimiento.setConcepto("Ingreso por Venta ID: " + request.getIdVenta() + " - Medio: " + nombreTipoPago);

                var responseMovimiento = registroMovimientoCajaUseCase.registroMovimientoCaja(requestMovimiento);

                if (!responseMovimiento.isExito()) {
                    throw new RuntimeException("No se pudo registrar el movimiento de dinero en el turno de caja: " + responseMovimiento.getMessage());
                }
            }

            return response;

        } catch (IllegalArgumentException | SecurityException e) {
            ResponseRegistroPago response = new ResponseRegistroPago();
            response.setExito(false);
            response.setMessage(e.getMessage());
            return response;
        } catch (Exception e) {
            String mensajeError = "Error inesperado al registrar el pago: " + e.getMessage();
            System.err.println("[ERROR] " + mensajeError);
            ResponseRegistroPago response = new ResponseRegistroPago();
            response.setExito(false);
            response.setMessage(mensajeError);
            return response;
        }
    }
}