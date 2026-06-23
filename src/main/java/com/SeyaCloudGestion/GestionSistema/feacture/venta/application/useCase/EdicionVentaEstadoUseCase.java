package com.SeyaCloudGestion.GestionSistema.feacture.venta.application.useCase;

import com.SeyaCloudGestion.GestionSistema.feacture.venta.application.dto.request.RequestEditarEstadoVenta;
import com.SeyaCloudGestion.GestionSistema.feacture.venta.application.dto.response.ResponseDetalleVenta;
import com.SeyaCloudGestion.GestionSistema.feacture.venta.application.dto.response.ResponseEditarEstadoVenta;
import com.SeyaCloudGestion.GestionSistema.feacture.venta.domain.services.VentaService;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class EdicionVentaEstadoUseCase {

    private final VentaService ventaService;
    private final DetalleVentaUseCase detalleVentaUseCase;

    public EdicionVentaEstadoUseCase(
            VentaService ventaService,
            DetalleVentaUseCase detalleVentaUseCase
    ) {
        this.ventaService = ventaService;
        this.detalleVentaUseCase = detalleVentaUseCase;
    }

    public ResponseEditarEstadoVenta EdicionAnularVenta(long idVenta) {
        try {
            ResponseDetalleVenta detalleBD = detalleVentaUseCase.DetalleVenta(idVenta);

            if (!detalleBD.isExito() || detalleBD.getDetalles() == null) {
                throw new IllegalArgumentException("La venta no existe.");
            }

            if (Objects.equals(detalleBD.getDetalles().getEstado(), 0)) {
                throw new IllegalArgumentException("La venta ya se encuentra anulada.");
            }

            RequestEditarEstadoVenta request = new RequestEditarEstadoVenta();
            request.setIdVenta(idVenta);

            ResponseEditarEstadoVenta response = ventaService.EditarEstadoVenta(request, 0);
            if (response.isExito()) {}

            return response;

        } catch (IllegalArgumentException | SecurityException e) {
            ResponseEditarEstadoVenta response = new ResponseEditarEstadoVenta();
            response.setExito(false);
            response.setMessage(e.getMessage());
            return response;
        } catch (Exception e) {
            String mensajeError = "Error inesperado al anular la venta: " + e.getMessage();
            System.err.println("[ERROR] " + mensajeError);
            ResponseEditarEstadoVenta response = new ResponseEditarEstadoVenta();
            response.setExito(false);
            response.setMessage(mensajeError);
            return response;
        }
    }

}