package com.SeyaCloudGestion.GestionSistema.feacture.movimientosStock.application.useCase;

import com.SeyaCloudGestion.GestionSistema.feacture.kardex.application.useCase.RegistroKardexUseCase;
import com.SeyaCloudGestion.GestionSistema.feacture.movimientosStock.application.dto.request.RequestProcesarFullStock;
import com.SeyaCloudGestion.GestionSistema.feacture.movimientosStock.application.dto.response.ResponseProcesarFullStock;
import com.SeyaCloudGestion.GestionSistema.feacture.stocks.application.useCase.EdicionSotckUseCase;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class ProcesarFullMovimientoStockUseCase {
    private final RegistroMovimientoStockUseCase registroMovimientoUseCase;
    private final EdicionSotckUseCase actualizarStockUseCase;
    private final RegistroKardexUseCase registroKardexUseCase;

    public ProcesarFullMovimientoStockUseCase(RegistroMovimientoStockUseCase registroMovimientoUseCase, EdicionSotckUseCase actualizarStockUseCase, RegistroKardexUseCase registroKardexUseCase) {
        this.registroMovimientoUseCase = registroMovimientoUseCase;
        this.actualizarStockUseCase = actualizarStockUseCase;
        this.registroKardexUseCase = registroKardexUseCase;
    }

    @Transactional("sqlServerTransactionManager")
    public ResponseProcesarFullStock procesar(RequestProcesarFullStock request) {

        // 1. Ejecutas el primer UseCase fragmentado (Movimiento de Stock)
        var resMov = registroMovimientoUseCase.registroMovimiento(request.toMovimientoRequest());
        if (!resMov.isExito()) throw new RuntimeException("Error al registrar movimiento: " + resMov.getMessage());

        // 2. Ejecutas el segundo UseCase fragmentado (Actualizar saldos de stock)
        var resStock = actualizarStockUseCase.actualizarStock(request.toStockRequest());
        if (!resStock.isExito()) throw new RuntimeException("Error al actualizar stock físico: " + resStock.getMessage());

        // kardex
        var resKardex = registroKardexUseCase.registroKardex(request.toKardexRequest(resStock.getNuevoSaldo()));
        if (!resKardex.isExito()) throw new RuntimeException("Error al registrar en Kardex: " + resKardex.getMessage());

        return new ResponseProcesarStock(true, "Inventario, Stock y Kardex procesados correctamente.");
    }
}
