package com.SeyaCloudGestion.GestionSistema.feacture.detalleCompra.application.useCase;

import com.SeyaCloudGestion.GestionSistema.feacture.detalleCompra.application.dto.request.RequestListaDetalleCompra;
import com.SeyaCloudGestion.GestionSistema.feacture.detalleCompra.application.dto.response.ResponseListaDetalleCompra;
import com.SeyaCloudGestion.GestionSistema.feacture.detalleCompra.domain.services.DetalleCompraService;
import com.SeyaCloudGestion.GestionSistema.feacture.venta.application.dto.request.RequestDetalleVenta;
import org.springframework.stereotype.Component;

@Component
public class ListaDetalleCompraUseCase {
    private final DetalleCompraService detalleVentaService;

    public ListaDetalleCompraUseCase(
            DetalleCompraService detalleVentaService
    ) {
        this.detalleVentaService = detalleVentaService;
    }

    public ResponseListaDetalleCompra listarDetalleCompra(long idCompra) {
        try {
            RequestListaDetalleCompra request = new RequestListaDetalleCompra();
            request.setIdCompra(idCompra);
            ResponseListaDetalleCompra response = detalleVentaService.listarDetalleCompra(request);
            if (response.isExito()) {

            }

            return response;

        } catch (IllegalArgumentException | SecurityException e) {
            ResponseListaDetalleCompra response = new ResponseListaDetalleCompra();
            response.setExito(false);
            response.setMessage(e.getMessage());
            response.setDetalles(java.util.List.of());
            return response;
        } catch (Exception e) {
            String mensajeError = "Error inesperado al listar los detalles de la venta: " + e.getMessage();
            System.err.println("[ERROR] " + mensajeError);
            ResponseListaDetalleCompra response = new ResponseListaDetalleCompra();
            response.setExito(false);
            response.setMessage(mensajeError);
            response.setDetalles(java.util.List.of());
            return response;
        }
    }
}