package com.SeyaCloudGestion.GestionSistema.feacture.pagos.application.useCase;

import com.SeyaCloudGestion.GestionSistema.feacture.movimientosCaja.application.dto.request.RequestRegistroMovimientoCaja;
import com.SeyaCloudGestion.GestionSistema.feacture.movimientosCaja.application.useCase.RegistroMovimientoCajaUseCase;
import com.SeyaCloudGestion.GestionSistema.feacture.movimientosCaja.infraestructure.persistence.model.Movimiento;
import com.SeyaCloudGestion.GestionSistema.feacture.pagos.application.dto.request.RequestRegistroDetallePago;
import com.SeyaCloudGestion.GestionSistema.feacture.pagos.application.dto.request.RequestRegistroPago;
import com.SeyaCloudGestion.GestionSistema.feacture.pagos.application.dto.response.ResponseRegistroDetallePago;
import com.SeyaCloudGestion.GestionSistema.feacture.pagos.application.dto.response.ResponseRegistroPago;
import com.SeyaCloudGestion.GestionSistema.feacture.pagos.domain.services.PagoService;
import com.SeyaCloudGestion.GestionSistema.feacture.tipoPagos.application.dto.response.ResponseDetalleTipoPagos;
import com.SeyaCloudGestion.GestionSistema.feacture.tipoPagos.application.useCase.DetalleTipoPagosUseCase;
import com.SeyaCloudGestion.GestionSistema.feacture.venta.application.dto.response.ResponseDetalleVenta;
import com.SeyaCloudGestion.GestionSistema.feacture.venta.application.useCase.DetalleVentaUseCase;
import com.SeyaCloudGestion.GestionSistema.feacture.venta.infraestructure.persistence.model.CondicionPago;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

@Component
public class RegistroDetallePagoUseCase {

    private final PagoService pagoService;
    private final DetalleVentaUseCase detalleVentaUseCase;
    private final DetalleTipoPagosUseCase detalleTipoPagosUseCase;
    private final RegistroMovimientoCajaUseCase registroMovimientoCajaUseCase;
    public RegistroDetallePagoUseCase(PagoService pagoService, DetalleVentaUseCase detalleVentaUseCase, DetalleTipoPagosUseCase detalleTipoPagosUseCase, RegistroMovimientoCajaUseCase registroMovimientoCajaUseCase) {
        this.pagoService = pagoService;
        this.detalleVentaUseCase = detalleVentaUseCase;
        this.detalleTipoPagosUseCase = detalleTipoPagosUseCase;
        this.registroMovimientoCajaUseCase = registroMovimientoCajaUseCase;
    }

    @Transactional("sqlServerTransactionManager")
    public ResponseRegistroDetallePago registrarPago(long idVenta,RequestRegistroDetallePago request) {
        try {
            //idventa
            ResponseDetalleVenta responsBDventa = detalleVentaUseCase.DetalleVenta(idVenta);
            if (!responsBDventa.isExito() || responsBDventa.getVenta() == null) {
                throw new IllegalArgumentException("La venta no existe.");
            }
            //idTipoPago
            ResponseDetalleTipoPagos responsBDTipoPago =detalleTipoPagosUseCase.DetalleTipoPagos(request.getIdTipoPago());
            if (!responsBDTipoPago.isExito() || responsBDTipoPago.getTipoPagos() == null) {
                throw new IllegalArgumentException("El tipo de pago no existe.");
            }
            //verificar el monto a pagar
            if (responsBDventa.getVenta().getTotal()<request.getMonto()) {
                throw new IllegalArgumentException("El monto ingresado supera el total requerido para esta venta al contado.");
            }

            ResponseRegistroDetallePago response = pagoService.RegistroPago(idVenta,request);
            if (response.isExito()) {

            }

            return response;

        } catch (IllegalArgumentException | SecurityException e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            ResponseRegistroDetallePago response = new ResponseRegistroDetallePago();
            response.setExito(false);
            response.setMessage(e.getMessage());
            return response;
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            String mensajeError = "Error inesperado al registrar el pago: " + e.getMessage();
            System.err.println("[ERROR] " + mensajeError);
            ResponseRegistroDetallePago response = new ResponseRegistroDetallePago();
            response.setExito(false);
            response.setMessage(mensajeError);
            return response;
        }
    }
}