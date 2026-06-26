package com.SeyaCloudGestion.GestionSistema.feacture.pagos.application.useCase;

import com.SeyaCloudGestion.GestionSistema.feacture.movimientosCaja.application.dto.request.RequestRegistroMovimientoCaja;
import com.SeyaCloudGestion.GestionSistema.feacture.movimientosCaja.application.dto.response.ResponseRegistroMovimientoCaja;
import com.SeyaCloudGestion.GestionSistema.feacture.movimientosCaja.application.useCase.RegistroMovimientoCajaUseCase;
import com.SeyaCloudGestion.GestionSistema.feacture.movimientosCaja.infraestructure.persistence.model.Movimiento;
import com.SeyaCloudGestion.GestionSistema.feacture.pagos.application.dto.request.RequestRegistroDetallePago;
import com.SeyaCloudGestion.GestionSistema.feacture.pagos.application.dto.request.RequestRegistroPago;
import com.SeyaCloudGestion.GestionSistema.feacture.pagos.application.dto.response.ResponseRegistroDetallePago;
import com.SeyaCloudGestion.GestionSistema.feacture.pagos.application.dto.response.ResponseRegistroPago;
import com.SeyaCloudGestion.GestionSistema.feacture.turnoCaja.application.dto.response.ResponseDetalleTurnoCaja;
import com.SeyaCloudGestion.GestionSistema.feacture.turnoCaja.application.useCase.DetalleTurnoCajaUseCase;
import com.SeyaCloudGestion.GestionSistema.feacture.turnoCaja.infraestructure.persistence.model.EstadoCaja;
import com.SeyaCloudGestion.GestionSistema.feacture.venta.application.dto.response.ResponseDetalleVenta;
import com.SeyaCloudGestion.GestionSistema.feacture.venta.application.useCase.DetalleVentaUseCase;
import com.SeyaCloudGestion.GestionSistema.feacture.venta.infraestructure.persistence.model.CondicionPago;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Component
public class RegistroPagoUseCase {

    private final DetalleVentaUseCase detalleVentaUseCase;
    private final RegistroMovimientoCajaUseCase registroMovimientoCajaUseCase;
    private final DetalleTurnoCajaUseCase detalleTurnoCajaUseCase;
    private final RegistroDetallePagoUseCase registroDetallePagoUseCase;

    public RegistroPagoUseCase( DetalleVentaUseCase detalleVentaUseCase,RegistroMovimientoCajaUseCase registroMovimientoCajaUseCase, DetalleTurnoCajaUseCase detalleTurnoCajaUseCase, RegistroDetallePagoUseCase registroDetallePagoUseCase) {
        this.detalleVentaUseCase = detalleVentaUseCase;
        this.registroMovimientoCajaUseCase = registroMovimientoCajaUseCase;
        this.detalleTurnoCajaUseCase = detalleTurnoCajaUseCase;
        this.registroDetallePagoUseCase = registroDetallePagoUseCase;
    }

    @Transactional("sqlServerTransactionManager")
    public ResponseRegistroPago registrarPago(RequestRegistroPago request) {
        try {
            //idVenta
            ResponseDetalleVenta responsBDVenta =detalleVentaUseCase.DetalleVenta(request.getIdVenta());
            if (!responsBDVenta.isExito() || responsBDVenta.getVenta() == null) {
                throw new IllegalArgumentException("La venta no existe.");
            }
            //get turno abierto
            ResponseDetalleTurnoCaja responseBDCaja = detalleTurnoCajaUseCase.DetalleTurnoCaja(request.getIdCaja(), EstadoCaja.ABIERTO);
            if (!responseBDCaja.isExito() || responseBDCaja.getTurnoCaja() == null) {
                throw new IllegalArgumentException("La caja no se encuentra abierta.");
            }
            long idTurnoCaja = responseBDCaja.getTurnoCaja().getIdTurnoCaja();

            //metodos de pago duplicados
            Set<Long> idsRevisados = new HashSet<>();
            double totalAbonado = 0.0;
            for (RequestRegistroDetallePago item : request.getPagos()) {
                totalAbonado += item.getMonto();
                if (!idsRevisados.add(item.getIdTipoPago())) {
                    throw new IllegalArgumentException("Se detectaron métodos de pago duplicados Los montos deben venir agrupados por tipo de pago.");
                }
            }
            //si es al contado tendra que pagar todo (targeta and efectivo o ambos)
            if (!responsBDVenta.getVenta().getCondicionPago().equals(CondicionPago.CONTADO)) {
                throw new IllegalArgumentException("Esta venta es a CRÉDITO. Los pagos de deudas deben registrarse a través del módulo de cobranzas.");
            }
            //verificar el monto a pagar
            if (totalAbonado != responsBDVenta.getVenta().getTotal()) {
                throw new IllegalArgumentException("El monto total ingresado no coincide exactamente con el total de la venta .");
            }

            for (RequestRegistroDetallePago item : request.getPagos()) {

                RequestRegistroDetallePago IndividualRequest = new RequestRegistroDetallePago();
                IndividualRequest.setIdTipoPago(item.getIdTipoPago());
                IndividualRequest.setMonto(item.getMonto());

                ResponseRegistroDetallePago responseDetalle = registroDetallePagoUseCase.registrarPago(request.getIdVenta(),IndividualRequest);
                if (!responseDetalle.isExito()) {
                    throw new RuntimeException("No se logro registar en el componente de detalle: " + responseDetalle.getMessage());
                }

            }

                //set movimviiemto
            RequestRegistroMovimientoCaja movCaja = new RequestRegistroMovimientoCaja();
            movCaja.setIdTurnoCaja(idTurnoCaja);
            movCaja.setMonto(totalAbonado);
            movCaja.setMovimiento(Movimiento.INGRESO);
            movCaja.setConcepto("Pago de venta");

            ResponseRegistroMovimientoCaja responseMov = registroMovimientoCajaUseCase.registroMovimientoCaja(movCaja);
            if (!responseMov.isExito()) {
                throw new RuntimeException("No se pudo registrar el ingreso en la caja: " + responseMov.getMessage());
            }

            ResponseRegistroPago response = new ResponseRegistroPago();
            response.setExito(true);
            response.setMessage("El pago se registró de manera exitosa.");

            if (response.isExito()) {
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