package com.SeyaCloudGestion.GestionSistema.feacture.pagos.application.useCase;

import com.SeyaCloudGestion.GestionSistema.feacture.cuentasPorCobrar.application.dto.response.ResponseDetalleCuentasPorCobrar;
import com.SeyaCloudGestion.GestionSistema.feacture.cuentasPorCobrar.application.useCase.DetalleCuentasPorCobrarUseCase;
import com.SeyaCloudGestion.GestionSistema.feacture.cuentasPorCobrar.infraestructure.persistence.model.EstadoCuenta;
import com.SeyaCloudGestion.GestionSistema.feacture.pagos.application.dto.request.RequestRegistroPago;
import com.SeyaCloudGestion.GestionSistema.feacture.pagos.application.dto.response.ResponseRegistroPago;
import com.SeyaCloudGestion.GestionSistema.feacture.pagos.domain.services.PagoService;
import com.SeyaCloudGestion.GestionSistema.feacture.tipoPagos.application.dto.response.ResponseDetalleTipoPagos;
import com.SeyaCloudGestion.GestionSistema.feacture.tipoPagos.application.useCase.DetalleTipoPagosUseCase;
import org.springframework.stereotype.Component;

@Component
public class RegistroPagoUseCase {

    private final PagoService pagoService;
    private final DetalleCuentasPorCobrarUseCase detalleCuentasPorCobrarUseCase;
    private final DetalleTipoPagosUseCase detalleTipoPagosUseCase;
    public RegistroPagoUseCase(PagoService pagoService, DetalleCuentasPorCobrarUseCase detalleCuentasPorCobrarUseCase, DetalleTipoPagosUseCase detalleTipoPagosUseCase) {
        this.pagoService = pagoService;
        this.detalleCuentasPorCobrarUseCase = detalleCuentasPorCobrarUseCase;
        this.detalleTipoPagosUseCase = detalleTipoPagosUseCase;
    }

    public ResponseRegistroPago registrarPago(RequestRegistroPago request) {
        try {
            //idcuenta
            ResponseDetalleCuentasPorCobrar responsBDcuenta = detalleCuentasPorCobrarUseCase.DetalleCuentasPorCobrar(request.getIdCuentaPorCobrar());
            if (!responsBDcuenta.isExito() || responsBDcuenta.getCuentasPorCobrar() == null) {
                throw new IllegalArgumentException("La cuent a pagar no existe.");
            }

            //idTipoPago
            ResponseDetalleTipoPagos responsBDTipoPago =detalleTipoPagosUseCase.DetalleTipoPagos(request.getIdTipoPago());
            if (!responsBDTipoPago.isExito() || responsBDTipoPago.getTipoPagos() == null) {
                throw new IllegalArgumentException("El tipo de pago no existe.");
            }
            //verificar si la cuenta ya esta saldada
            if (responsBDcuenta.getCuentasPorCobrar().getEstado().equals(EstadoCuenta.PAGADO)) {
                throw new IllegalArgumentException("La cuenta ya esta saldada no puedes volver a pagarla.");
            }
            if (responsBDcuenta.getCuentasPorCobrar().getEstado().equals(EstadoCuenta.ANULADO)) {
                throw new IllegalArgumentException("La cuenta esta anulada no se puede pagar.");
            }
            ResponseRegistroPago response = pagoService.RegistroPago(request);
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