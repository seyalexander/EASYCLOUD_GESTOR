package com.SeyaCloudGestion.GestionSistema.feacture.venta.application.useCase;

import com.SeyaCloudGestion.GestionSistema.feacture.venta.application.dto.request.RequestDetalleVenta;
import com.SeyaCloudGestion.GestionSistema.feacture.venta.application.dto.response.ResponseDetalleVenta;
import com.SeyaCloudGestion.GestionSistema.feacture.venta.domain.services.VentaService;
import org.springframework.stereotype.Component;

@Component
public class DetalleVentaUseCase {

    private final VentaService ventaService;

    public DetalleVentaUseCase(
            VentaService ventaService
    ) {
        this.ventaService = ventaService;
    }

    public ResponseDetalleVenta DetalleVenta(long idVenta) {
        try {
            RequestDetalleVenta request = new RequestDetalleVenta();
            request.setIdVenta(idVenta);

            ResponseDetalleVenta response = ventaService.DetalleVenta(request);
            if (response.isExito()) {}

            return response;

        } catch (IllegalArgumentException | SecurityException e) {
            ResponseDetalleVenta response = new ResponseDetalleVenta();
            response.setExito(false);
            response.setMessage(e.getMessage());
            return response;
        } catch (Exception e) {
            String mensajeError = "Error inesperado al obtener el detalle de la venta: " + e.getMessage();
            System.err.println("[ERROR] " + mensajeError);
            ResponseDetalleVenta response = new ResponseDetalleVenta();
            response.setExito(false);
            response.setMessage(mensajeError);
            return response;
        }
    }
}