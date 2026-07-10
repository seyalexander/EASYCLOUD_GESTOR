package com.SeyaCloudGestion.GestionSistema.feacture.movimientosStock.application.useCase;

import com.SeyaCloudGestion.GestionSistema.feacture.movimientosStock.application.dto.request.RequestDetalleMovimientoStock;
import com.SeyaCloudGestion.GestionSistema.feacture.movimientosStock.application.dto.response.ResponseDetalleMovimientoStock;
import com.SeyaCloudGestion.GestionSistema.feacture.movimientosStock.domain.services.MovimientoStockService;
import org.springframework.stereotype.Component;

@Component
public class DetalleMovimientoStockUseCase {

    private final MovimientoStockService movimientoStockService;

    public DetalleMovimientoStockUseCase(
            MovimientoStockService movimientoStockService
    ) {
        this.movimientoStockService = movimientoStockService;
    }

    public ResponseDetalleMovimientoStock DetalleMovimientoStock(long idMovimientoStock) {
        try {
            RequestDetalleMovimientoStock request = new RequestDetalleMovimientoStock();
            request.setIdDetalleMovimiento(idMovimientoStock);

            ResponseDetalleMovimientoStock response = movimientoStockService.DetalleMovimientoStock(request);
            if (response.isExito()) {}

            return response;

        } catch (IllegalArgumentException | SecurityException e) {
            ResponseDetalleMovimientoStock response = new ResponseDetalleMovimientoStock();
            response.setExito(false);
            response.setMessage(e.getMessage());
            return response;

        } catch (Exception e) {
            String mensajeError = "Error inesperado al obtener el detalle del movimiento de stock: " + e.getMessage();
            System.err.println("[ERROR] " + mensajeError);

            ResponseDetalleMovimientoStock response = new ResponseDetalleMovimientoStock();
            response.setExito(false);
            response.setMessage(mensajeError);
            return response;
        }
    }
}