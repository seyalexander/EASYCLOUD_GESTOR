package com.SeyaCloudGestion.GestionSistema.feacture.detalleVenta.application.useCase;

import com.SeyaCloudGestion.GestionSistema.feacture.detalleVenta.application.dto.response.ResponseListaDetalleVenta;
import com.SeyaCloudGestion.GestionSistema.feacture.detalleVenta.domain.services.DetalleVentaService;
import com.SeyaCloudGestion.GestionSistema.feacture.venta.application.dto.request.RequestDetalleVenta;
import org.springframework.stereotype.Component;

@Component
public class ListaDetalleVentaUseCase {
    private final DetalleVentaService detalleVentaService;

    public ListaDetalleVentaUseCase(
            DetalleVentaService detalleVentaService
    ) {
        this.detalleVentaService = detalleVentaService;
    }

    public ResponseListaDetalleVenta listarDetalleVenta(long idVenta) {
        try {
            RequestDetalleVenta request = new RequestDetalleVenta();
            request.setIdVenta(idVenta);
            ResponseListaDetalleVenta response = detalleVentaService.listarDetalleVenta(request);
            if (response.isExito()) {}

            return response;

        } catch (IllegalArgumentException | SecurityException e) {
            ResponseListaDetalleVenta response = new ResponseListaDetalleVenta();
            response.setExito(false);
            response.setMessage(e.getMessage());
            response.setDetalles(java.util.List.of());
            return response;
        } catch (Exception e) {
            String mensajeError = "Error inesperado al listar los detalles de la venta: " + e.getMessage();
            System.err.println("[ERROR] " + mensajeError);
            ResponseListaDetalleVenta response = new ResponseListaDetalleVenta();
            response.setExito(false);
            response.setMessage(mensajeError);
            response.setDetalles(java.util.List.of());
            return response;
        }
    }
}