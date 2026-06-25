package com.SeyaCloudGestion.GestionSistema.feacture.pagoClientes.application.useCase;

import com.SeyaCloudGestion.GestionSistema.feacture.cuentasPorCobrar.application.dto.response.ResponseDetalleCuentasPorCobrar;
import com.SeyaCloudGestion.GestionSistema.feacture.cuentasPorCobrar.application.useCase.DetalleCuentasPorCobrarUseCase;
import com.SeyaCloudGestion.GestionSistema.feacture.cuentasPorCobrar.infraestructure.persistence.model.EstadoCuenta;
import com.SeyaCloudGestion.GestionSistema.feacture.pagoClientes.application.dto.request.RequestRegistroDetallePagoCliente;
import com.SeyaCloudGestion.GestionSistema.feacture.pagoClientes.application.dto.request.RequestRegistroPagoCliente;
import com.SeyaCloudGestion.GestionSistema.feacture.pagoClientes.application.dto.response.ResponseRegistroDetallePagoCliente;
import com.SeyaCloudGestion.GestionSistema.feacture.pagoClientes.application.dto.response.ResponseRegistroPagoCliente;
import com.SeyaCloudGestion.GestionSistema.feacture.pagoClientes.domain.services.PagoClienteService;
import com.SeyaCloudGestion.GestionSistema.feacture.tipoPagos.application.dto.response.ResponseDetalleTipoPagos;
import com.SeyaCloudGestion.GestionSistema.feacture.tipoPagos.application.useCase.DetalleTipoPagosUseCase;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

public class RegistroPagoClienteUseCase {
    private final PagoClienteService pagoClienteService;
    private final DetalleCuentasPorCobrarUseCase detalleCuentasPorCobrarUseCase;
    private final RegistroDetallePagoClienteUseCase registroDetallePagoClienteUseCase;
    public RegistroPagoClienteUseCase(PagoClienteService pagoClienteService, DetalleCuentasPorCobrarUseCase detalleCuentasPorCobrarUseCase, RegistroDetallePagoClienteUseCase registroDetallePagoClienteUseCase) {
        this.pagoClienteService = pagoClienteService;
        this.detalleCuentasPorCobrarUseCase = detalleCuentasPorCobrarUseCase;
        this.registroDetallePagoClienteUseCase = registroDetallePagoClienteUseCase;
    }

    @Transactional("sqlServerTransactionManager")
    public ResponseRegistroPagoCliente registrarPagoCliente(RequestRegistroPagoCliente request) {
        try {
            //idcuenta
            ResponseDetalleCuentasPorCobrar responsBDcuenta = detalleCuentasPorCobrarUseCase.DetalleCuentasPorCobrar(request.getIdCuentaPorCobrar());
            if (!responsBDcuenta.isExito() || responsBDcuenta.getCuentasPorCobrar() == null) {
                throw new IllegalArgumentException("La cuenta por cobrar no existe.");
            }

            //verificar si la cuenta ya esta saldada
            if (responsBDcuenta.getCuentasPorCobrar().getEstado().equals(EstadoCuenta.PAGADO)) {
                throw new IllegalArgumentException("La cuenta ya esta saldada");
            }
            if (responsBDcuenta.getCuentasPorCobrar().getEstado().equals(EstadoCuenta.ANULADO)) {
                throw new IllegalArgumentException("La cuenta esta anulada y no admite abonos.");
            }
            //
            Set<Long> idsRevisados = new HashSet<>();

            for (RequestRegistroDetallePagoCliente item : request.getPagos()) {
                if (!idsRevisados.add(item.getIdTipoPago())) {
                    throw new IllegalArgumentException("Se detectaron métodos de pago duplicados Los montos deben venir agrupados por tipo de pago.");
                }
            }

            double totalAbonado = 0.0;
            //cantidad abonada
            for (RequestRegistroDetallePagoCliente item : request.getPagos()){
                totalAbonado += item.getMontoPagado();
            }
            //si es mas
            if (totalAbonado > responsBDcuenta.getCuentasPorCobrar().getMontoPendiente()) {
                throw new IllegalArgumentException("El total de abonos ingresados (" + totalAbonado + ") supera el monto pendiente de la cuenta.");
            }
            for (RequestRegistroDetallePagoCliente item : request.getPagos()) {

                RequestRegistroDetallePagoCliente IndividualRequest = new RequestRegistroDetallePagoCliente();
                IndividualRequest.setIdTipoPago(item.getIdTipoPago());
                IndividualRequest.setMontoPagado(item.getMontoPagado());

                ResponseRegistroDetallePagoCliente responseDetalle = registroDetallePagoClienteUseCase.registrarDetallePagoCliente(request.getIdCuentaPorCobrar(),IndividualRequest);
                if (!responseDetalle.isExito()) {
                    throw new RuntimeException("Error en el componente de detalle: " + responseDetalle.getMessage());
                }

            }

            ResponseRegistroPagoCliente response = new ResponseRegistroPagoCliente();
            response.setExito(true);
            response.setMessage("El pago se registró de manera exitosa.");
            if (response.isExito()) {
                
            }

            return response;

        } catch (IllegalArgumentException | SecurityException e) {
            ResponseRegistroPagoCliente response = new ResponseRegistroPagoCliente();
            response.setExito(false);
            response.setMessage(e.getMessage());
            return response;
        } catch (Exception e) {
            String mensajeError = "Error inesperado al registrar el pago: " + e.getMessage();
            System.err.println("[ERROR] " + mensajeError);
            ResponseRegistroPagoCliente response = new ResponseRegistroPagoCliente();
            response.setExito(false);
            response.setMessage(mensajeError);
            return response;
        }
    }
}
