package com.SeyaCloudGestion.GestionSistema.feacture.movimientosStock.domain.services;

import com.SeyaCloudGestion.GestionSistema.feacture.movimientosStock.application.dto.request.RequestDetalleMovimientoStock;
import com.SeyaCloudGestion.GestionSistema.feacture.movimientosStock.application.dto.request.RequestListaMovimientoStock;
import com.SeyaCloudGestion.GestionSistema.feacture.movimientosStock.application.dto.request.RequestRegistroMovimientoStock;
import com.SeyaCloudGestion.GestionSistema.feacture.movimientosStock.application.dto.response.ResponseDetalleMovimientoStock;
import com.SeyaCloudGestion.GestionSistema.feacture.movimientosStock.application.dto.response.ResponseListaMovimientoStock;
import com.SeyaCloudGestion.GestionSistema.feacture.movimientosStock.application.dto.response.ResponseRegistroMovimientoStock;
import com.SeyaCloudGestion.GestionSistema.feacture.movimientosStock.domain.interfaces.IMovimientoStockDetalle;
import com.SeyaCloudGestion.GestionSistema.feacture.movimientosStock.domain.interfaces.IMovimientoStockListado;
import com.SeyaCloudGestion.GestionSistema.feacture.movimientosStock.domain.interfaces.IMovimientoStockRegistro;
import com.SeyaCloudGestion.GestionSistema.feacture.movimientosStock.infraestructure.persistence.repository.crud.MovimientoStockDetalleRepository;
import com.SeyaCloudGestion.GestionSistema.feacture.movimientosStock.infraestructure.persistence.repository.crud.MovimientoStockListadoRepository;
import com.SeyaCloudGestion.GestionSistema.feacture.movimientosStock.infraestructure.persistence.repository.crud.MovimientoStockRegistroRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MovimientoStockService implements IMovimientoStockListado, IMovimientoStockRegistro, IMovimientoStockDetalle {

    private final MovimientoStockListadoRepository movimientoStockListadoRepository;
    private final MovimientoStockRegistroRepository movimientoStockRegistroRepository;
    private final MovimientoStockDetalleRepository movimientoStockDetalleRepository;

    public MovimientoStockService(
            MovimientoStockListadoRepository movimientoStockListadoRepository,
            MovimientoStockRegistroRepository movimientoStockRegistroRepository,
            MovimientoStockDetalleRepository movimientoStockDetalleRepository
    ) {
        this.movimientoStockListadoRepository = movimientoStockListadoRepository;
        this.movimientoStockRegistroRepository = movimientoStockRegistroRepository;
        this.movimientoStockDetalleRepository = movimientoStockDetalleRepository;
    }

    @Override
    @Cacheable(value = "movimientos_lista", key = "{#request.idArticulo, #request.idAlmacen}")
    public ResponseListaMovimientoStock listaMovimientoStock(RequestListaMovimientoStock request) {
        return movimientoStockListadoRepository.listaMovimientoStock(request);
    }

    @Override
    @CacheEvict(value = {"movimientos_lista", "movimiento_detalle"}, allEntries = true)
    public ResponseRegistroMovimientoStock RegistroMovimientoStock(RequestRegistroMovimientoStock request) {
        return movimientoStockRegistroRepository.RegistroMovimientoStock(request);
    }

    @Override
    @Cacheable(value = "movimiento_detalle", key = "#request.idMovimientoStock")
    public ResponseDetalleMovimientoStock DetalleMovimientoStock(RequestDetalleMovimientoStock request) {
        return movimientoStockDetalleRepository.DetalleMovimientoStock(request);
    }
}