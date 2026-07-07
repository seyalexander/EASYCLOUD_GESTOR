package com.SeyaCloudGestion.GestionSistema.feacture.pagoClientes.application.useCase;

import com.SeyaCloudGestion.GestionSistema.feacture.cuentasPorCobrar.application.dto.response.ResponseDetalleCuentasPorCobrar;
import com.SeyaCloudGestion.GestionSistema.feacture.cuentasPorCobrar.application.useCase.DetalleCuentasPorCobrarUseCase;
import com.SeyaCloudGestion.GestionSistema.feacture.cuentasPorCobrar.infraestructure.persistence.model.EstadoCuenta;
import com.SeyaCloudGestion.GestionSistema.feacture.movimientosCaja.application.dto.request.RequestRegistroMovimientoCaja;
import com.SeyaCloudGestion.GestionSistema.feacture.movimientosCaja.application.dto.response.ResponseRegistroMovimientoCaja;
import com.SeyaCloudGestion.GestionSistema.feacture.movimientosCaja.application.useCase.RegistroMovimientoCajaUseCase;
import com.SeyaCloudGestion.GestionSistema.feacture.movimientosCaja.infraestructure.persistence.model.Movimiento;
import com.SeyaCloudGestion.GestionSistema.feacture.pagoClientes.application.dto.request.RequestRegistroDetallePagoCliente;
import com.SeyaCloudGestion.GestionSistema.feacture.pagoClientes.application.dto.request.RequestRegistroPagoCliente;
import com.SeyaCloudGestion.GestionSistema.feacture.pagoClientes.application.dto.response.ResponseRegistroDetallePagoCliente;
import com.SeyaCloudGestion.GestionSistema.feacture.pagoClientes.application.dto.response.ResponseRegistroPagoCliente;
import com.SeyaCloudGestion.GestionSistema.feacture.turnoCaja.application.dto.response.ResponseDetalleTurnoCaja;
import com.SeyaCloudGestion.GestionSistema.feacture.turnoCaja.application.useCase.DetalleTurnoCajaUseCase;
import com.SeyaCloudGestion.GestionSistema.feacture.turnoCaja.infraestructure.persistence.model.EstadoCaja;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.HashSet;
import java.util.Set;

public class RegistroPagoClienteUseCase {
    private final DetalleCuentasPorCobrarUseCase detalleCuentasPorCobrarUseCase;
    private final RegistroDetallePagoClienteUseCase registroDetallePagoClienteUseCase;
    private final RegistroMovimientoCajaUseCase registroMovimientoCajaUseCase;
    private final DetalleTurnoCajaUseCase detalleTurnoCajaUseCase;

    public RegistroPagoClienteUseCase(DetalleCuentasPorCobrarUseCase detalleCuentasPorCobrarUseCase, RegistroDetallePagoClienteUseCase registroDetallePagoClienteUseCase, RegistroMovimientoCajaUseCase registroMovimientoCajaUseCase, DetalleTurnoCajaUseCase detalleTurnoCajaUseCase) {
        this.detalleCuentasPorCobrarUseCase = detalleCuentasPorCobrarUseCase;
        this.registroDetallePagoClienteUseCase = registroDetallePagoClienteUseCase;
        this.registroMovimientoCajaUseCase = registroMovimientoCajaUseCase;
        this.detalleTurnoCajaUseCase = detalleTurnoCajaUseCase;
    }

    @Transactional("sqlServerTransactionManager")
    public ResponseRegistroPagoCliente registrarPagoCliente(RequestRegistroPagoCliente request) {
        try {
            //get turno abierto
            ResponseDetalleTurnoCaja responseBDCaja = detalleTurnoCajaUseCase.DetalleTurnoCaja(request.getIdCaja(), EstadoCaja.ABIERTO);
            if (!responseBDCaja.isExito() || responseBDCaja.getTurnoCaja() == null) {
                throw new IllegalArgumentException("La caja no se encuentra abierta.");
            }
            long idTurnoCaja = responseBDCaja.getTurnoCaja().getIdTurnoCaja();
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
            //metodos de pago duplicados
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
                // 2. AQUI MISMO: Registrar el movimiento en caja para ESTE método de pago en específico
                RequestRegistroMovimientoCaja movCaja = new RequestRegistroMovimientoCaja();
                movCaja.setIdTurnoCaja(idTurnoCaja);
                movCaja.setMonto(item.getMontoPagado());
                movCaja.setMovimiento(Movimiento.INGRESO);
                movCaja.setIdTipoPago(item.getIdTipoPago());
                movCaja.setConcepto("Abono a cuenta por cobrar Nro: " + request.getIdCuentaPorCobrar());
                ResponseRegistroMovimientoCaja responseMov = registroMovimientoCajaUseCase.registroMovimientoCaja(movCaja);
                if (!responseMov.isExito()) {
                    throw new RuntimeException("No se pudo registrar el ingreso en la caja: " + responseMov.getMessage());
                }
            }

            ResponseRegistroPagoCliente response = new ResponseRegistroPagoCliente();
            response.setExito(true);
            response.setMessage("El pago se registró de manera exitosa.");
            if (response.isExito()) {

            }

            return response;

        } catch (IllegalArgumentException | SecurityException e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            ResponseRegistroPagoCliente response = new ResponseRegistroPagoCliente();
            response.setExito(false);
            response.setMessage(e.getMessage());
            return response;
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            String mensajeError = "Error inesperado al registrar el pago: " + e.getMessage();
            System.err.println("[ERROR] " + mensajeError);
            ResponseRegistroPagoCliente response = new ResponseRegistroPagoCliente();
            response.setExito(false);
            response.setMessage(mensajeError);
            return response;
        }
    }
}
