package com.SeyaCloudGestion.GestionSistema.feacture.movimientosStock.application.useCase;

import com.SeyaCloudGestion.GestionSistema.feacture.movimientosStock.application.dto.request.RequestListaMovimientoStock;
import com.SeyaCloudGestion.GestionSistema.feacture.movimientosStock.application.dto.response.ResponseListaMovimientoStock;
import com.SeyaCloudGestion.GestionSistema.feacture.movimientosStock.domain.services.MovimientoStockService;
import org.springframework.stereotype.Component;

@Component
public class ListaMovimientoStockUseCase {
    private final MovimientoStockService movimientoStockService;

    public ListaMovimientoStockUseCase(
            MovimientoStockService movimientoStockService
    ) {
        this.movimientoStockService = movimientoStockService;
    }

    public ResponseListaMovimientoStock ListaMovimientoStock(RequestListaMovimientoStock request) {
        try {
            ResponseListaMovimientoStock response = movimientoStockService.listaMovimientoStock(request);
            if (response.isExito()) {}

            return response;

        } catch (IllegalArgumentException | SecurityException e) {
            ResponseListaMovimientoStock response = new ResponseListaMovimientoStock();
            response.setExito(false);
            response.setMessage(e.getMessage());
            response.setMovimientoStocks(java.util.List.of());
            return response;

        } catch (Exception e) {
            String mensajeError = "Error inesperado al listar los movimientos de stock: " + e.getMessage();
            System.err.println("[ERROR] " + mensajeError);

            ResponseListaMovimientoStock response = new ResponseListaMovimientoStock();
            response.setExito(false);
            response.setMessage(mensajeError);
            response.setMovimientoStocks(java.util.List.of());
            return response;
        }
    }
}