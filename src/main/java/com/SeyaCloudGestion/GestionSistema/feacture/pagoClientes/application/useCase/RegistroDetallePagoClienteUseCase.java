package com.SeyaCloudGestion.GestionSistema.feacture.pagoClientes.application.useCase;

import com.SeyaCloudGestion.GestionSistema.feacture.cuentasPorCobrar.application.dto.response.ResponseDetalleCuentasPorCobrar;
import com.SeyaCloudGestion.GestionSistema.feacture.cuentasPorCobrar.application.useCase.DetalleCuentasPorCobrarUseCase;
import com.SeyaCloudGestion.GestionSistema.feacture.cuentasPorCobrar.infraestructure.persistence.model.EstadoCuenta;
import com.SeyaCloudGestion.GestionSistema.feacture.pagoClientes.application.dto.request.RequestRegistroDetallePagoCliente;
import com.SeyaCloudGestion.GestionSistema.feacture.pagoClientes.application.dto.response.ResponseRegistroDetallePagoCliente;
import com.SeyaCloudGestion.GestionSistema.feacture.pagoClientes.application.dto.response.ResponseRegistroPagoCliente;
import com.SeyaCloudGestion.GestionSistema.feacture.pagoClientes.domain.services.PagoClienteService;
import com.SeyaCloudGestion.GestionSistema.feacture.tipoPagos.application.dto.response.ResponseDetalleTipoPagos;
import com.SeyaCloudGestion.GestionSistema.feacture.tipoPagos.application.useCase.DetalleTipoPagosUseCase;

public class RegistroDetallePagoClienteUseCase {
    private final PagoClienteService pagoClienteService;
    private final DetalleCuentasPorCobrarUseCase detalleCuentasPorCobrarUseCase;
    private final DetalleTipoPagosUseCase detalleTipoPagosUseCase;

    public RegistroDetallePagoClienteUseCase(PagoClienteService pagoClienteService, DetalleCuentasPorCobrarUseCase detalleCuentasPorCobrarUseCase, DetalleTipoPagosUseCase detalleTipoPagosUseCase) {
        this.pagoClienteService = pagoClienteService;
        this.detalleCuentasPorCobrarUseCase = detalleCuentasPorCobrarUseCase;
        this.detalleTipoPagosUseCase = detalleTipoPagosUseCase;
    }


    public ResponseRegistroDetallePagoCliente registrarDetallePagoCliente(long idCuentaPorCobrar , RequestRegistroDetallePagoCliente request) {
        try {
            //idcuenta
            ResponseDetalleCuentasPorCobrar responsBDcuenta = detalleCuentasPorCobrarUseCase.DetalleCuentasPorCobrar(idCuentaPorCobrar);
            if (!responsBDcuenta.isExito() || responsBDcuenta.getCuentasPorCobrar() == null) {
                throw new IllegalArgumentException("La cuenta por cobrar no existe.");
            }
            ResponseDetalleTipoPagos responsBDTipoPago =detalleTipoPagosUseCase.DetalleTipoPagos(request.getIdTipoPago());
            if (!responsBDTipoPago.isExito() || responsBDTipoPago.getTipoPagos() == null) {
                throw new IllegalArgumentException("El tipo de pago no existe.");
            }

            if (request.getMontoPagado()>responsBDcuenta.getCuentasPorCobrar().getMontoPendiente()){
                throw new IllegalArgumentException("No se puede abonar mas de lo que debe.");
            }

            ResponseRegistroDetallePagoCliente response = pagoClienteService.RegistroDetallePagoCliente(idCuentaPorCobrar,request);

            if (response.isExito()) {

            }

            return response;

        } catch (IllegalArgumentException | SecurityException e) {
            ResponseRegistroDetallePagoCliente response = new ResponseRegistroDetallePagoCliente();
            response.setExito(false);
            response.setMessage(e.getMessage());
            return response;
        } catch (Exception e) {
            String mensajeError = "Error inesperado al registrar el detalle pago: " + e.getMessage();
            System.err.println("[ERROR] " + mensajeError);
            ResponseRegistroDetallePagoCliente response = new ResponseRegistroDetallePagoCliente();
            response.setExito(false);
            response.setMessage(mensajeError);
            return response;
        }
    }
}
