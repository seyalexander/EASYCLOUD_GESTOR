package com.SeyaCloudGestion.GestionSistema.feacture.movimientosStock.application.useCase;

import com.SeyaCloudGestion.GestionSistema.feacture.movimientosStock.application.dto.request.RequestRegistroMovimientoStock;
import com.SeyaCloudGestion.GestionSistema.feacture.movimientosStock.application.dto.response.ResponseRegistroMovimientoStock;
import com.SeyaCloudGestion.GestionSistema.feacture.movimientosStock.domain.services.MovimientoStockService;
import org.springframework.stereotype.Component;

@Component
public class RegistroMovimientoStockUseCase {
    private final MovimientoStockService movimientoStockService;

    public RegistroMovimientoStockUseCase(
            MovimientoStockService movimientoStockService
    ) {
        this.movimientoStockService = movimientoStockService;
    }

    public ResponseRegistroMovimientoStock RegistroMovimientoStock(RequestRegistroMovimientoStock request) {
        try {
            ResponseRegistroMovimientoStock response = movimientoStockService.RegistroMovimientoStock(request);

            if (response.isExito()) {

            }

            return response;

        } catch (IllegalArgumentException | SecurityException e) {
            ResponseRegistroMovimientoStock response = new ResponseRegistroMovimientoStock();
            response.setExito(false);
            response.setMessage(e.getMessage());
            return response;

        } catch (Exception e) {
            String mensajeError = "Error inesperado al registrar el movimiento de stock: " + e.getMessage();
            System.err.println("[ERROR] " + mensajeError);

            ResponseRegistroMovimientoStock response = new ResponseRegistroMovimientoStock();
            response.setExito(false);
            response.setMessage(mensajeError);
            return response;
        }
    }
}