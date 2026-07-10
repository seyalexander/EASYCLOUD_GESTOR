package com.SeyaCloudGestion.GestionSistema.feacture.venta.application.useCase;

import com.SeyaCloudGestion.GestionSistema.feacture.venta.application.dto.request.RequestListaVenta;
import com.SeyaCloudGestion.GestionSistema.feacture.venta.application.dto.response.ResponseListaVenta;
import com.SeyaCloudGestion.GestionSistema.feacture.venta.domain.services.VentaService;
import org.springframework.stereotype.Component;

@Component
public class ListaVentaUseCase {
    private final VentaService ventaService;

    public ListaVentaUseCase(
            VentaService ventaService
    ) {
        this.ventaService = ventaService;
    }

    public ResponseListaVenta listaVenta(RequestListaVenta request) {
        try {
            ResponseListaVenta response = ventaService.listaVenta(request);
            if (response.isExito()) {}

            return response;

        } catch (IllegalArgumentException | SecurityException e) {
            ResponseListaVenta response = new ResponseListaVenta();
            response.setExito(false);
            response.setMessage(e.getMessage());
            response.setVentas(java.util.List.of()); // Asegura una lista vacía en lugar de un null peligroso
            return response;
        } catch (Exception e) {
            String mensajeError = "Error inesperado al listar las ventas: " + e.getMessage();
            System.err.println("[ERROR] " + mensajeError);
            ResponseListaVenta response = new ResponseListaVenta();
            response.setExito(false);
            response.setMessage(mensajeError);
            response.setVentas(java.util.List.of());
            return response;
        }
    }
}