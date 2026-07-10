package com.SeyaCloudGestion.GestionSistema.feacture.notasCredito.application.useCase;

import com.SeyaCloudGestion.GestionSistema.feacture.notasCredito.application.dto.request.RequestRegistroNotaCredito;
import com.SeyaCloudGestion.GestionSistema.feacture.notasCredito.application.dto.response.ResponseRegistroNotaCredito;
import com.SeyaCloudGestion.GestionSistema.feacture.notasCredito.domain.services.NotaCreditoService;
import com.SeyaCloudGestion.GestionSistema.feacture.venta.application.dto.response.ResponseDetalleVenta;
import com.SeyaCloudGestion.GestionSistema.feacture.venta.application.useCase.DetalleVentaUseCase;
import org.springframework.stereotype.Component;

@Component
public class RegistroNotaCreditoUseCase {
    private final NotaCreditoService notaCreditoService;
    private final DetalleVentaUseCase detalleVentaUseCase;
    public RegistroNotaCreditoUseCase(NotaCreditoService notaCreditoService, DetalleVentaUseCase detalleVentaUseCase) {
        this.notaCreditoService = notaCreditoService;
        this.detalleVentaUseCase = detalleVentaUseCase;
    }

    public ResponseRegistroNotaCredito registroNotaCredito(RequestRegistroNotaCredito request) {
        try {
            //get id venta
            ResponseDetalleVenta responseBDventa =detalleVentaUseCase.DetalleVenta(request.getIdVenta());
            if (!responseBDventa.isExito() || responseBDventa.getVenta() == null) {
                throw new IllegalArgumentException("La venta no existe.");
            }
            //el monto a devolver no supera el total pagado
            double montoTotalVenta = responseBDventa.getVenta().getTotal();
            if (request.getMontoADevolver()> montoTotalVenta) {
                throw new IllegalArgumentException("No se puede devolver mas del monto de la venta ");
            }
            //falta transferencia caja

            ResponseRegistroNotaCredito response = notaCreditoService.RegistroNotaCredito(request);
            return response;

        } catch (IllegalArgumentException | SecurityException e) {
            ResponseRegistroNotaCredito response = new ResponseRegistroNotaCredito();
            response.setExito(false);
            response.setMessage(e.getMessage());
            return response;
        } catch (Exception e) {
            String mensajeError = "Error inesperado al registrar la nota de crédito: " + e.getMessage();
            System.err.println("[ERROR] " + mensajeError);
            ResponseRegistroNotaCredito response = new ResponseRegistroNotaCredito();
            response.setExito(false);
            response.setMessage(mensajeError);
            return response;
        }
    }
}