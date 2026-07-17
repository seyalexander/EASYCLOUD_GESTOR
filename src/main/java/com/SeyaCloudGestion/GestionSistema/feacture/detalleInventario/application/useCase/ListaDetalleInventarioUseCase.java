package com.SeyaCloudGestion.GestionSistema.feacture.detalleInventario.application.useCase;

import com.SeyaCloudGestion.GestionSistema.feacture.detalleInventario.application.dto.request.RequestListaDetalleInventario;
import com.SeyaCloudGestion.GestionSistema.feacture.detalleInventario.application.dto.response.ResponseListaDetalleInventario;
import com.SeyaCloudGestion.GestionSistema.feacture.detalleInventario.domain.services.DetalleInventarioService;
import org.springframework.stereotype.Component;

@Component
public class ListaDetalleInventarioUseCase {
    private final DetalleInventarioService detalleVentaService;

    public ListaDetalleInventarioUseCase(
            DetalleInventarioService detalleVentaService
    ) {
        this.detalleVentaService = detalleVentaService;
    }

    public ResponseListaDetalleInventario listarDetalleInventario(long idCabezeraIventario,long idAlmacen) {
        try {
            RequestListaDetalleInventario request = new RequestListaDetalleInventario();
            request.setIdInventarioCabecera(idCabezeraIventario);
            request.setIdAlmacecn(idAlmacen);
            ResponseListaDetalleInventario response = detalleVentaService.listarDetalleInventario(request);
            if (response.isExito()) {

            }

            return response;

        } catch (IllegalArgumentException | SecurityException e) {
            ResponseListaDetalleInventario response = new ResponseListaDetalleInventario();
            response.setExito(false);
            response.setMessage(e.getMessage());
            response.setDetalles(java.util.List.of());
            return response;
        } catch (Exception e) {
            String mensajeError = "Error inesperado al listar los detalles de la venta: " + e.getMessage();
            System.err.println("[ERROR] " + mensajeError);
            ResponseListaDetalleInventario response = new ResponseListaDetalleInventario();
            response.setExito(false);
            response.setMessage(mensajeError);
            response.setDetalles(java.util.List.of());
            return response;
        }
    }
}