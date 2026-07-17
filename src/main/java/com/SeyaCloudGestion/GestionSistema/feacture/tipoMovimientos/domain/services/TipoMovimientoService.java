package com.SeyaCloudGestion.GestionSistema.feacture.tipoMovimientos.domain.services;

import com.SeyaCloudGestion.GestionSistema.feacture.tipoMovimientos.application.dto.request.*;
import com.SeyaCloudGestion.GestionSistema.feacture.tipoMovimientos.application.dto.response.*;
import com.SeyaCloudGestion.GestionSistema.feacture.tipoMovimientos.domain.interfaces.*;
import com.SeyaCloudGestion.GestionSistema.feacture.tipoMovimientos.infraestructure.persistence.repository.crud.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
@Slf4j
@Service
public class TipoMovimientoService implements ITipoMovimientoListado, ITipoMovimientoRegistro, ITipoMovimientoEdicion, ITipoMovimientoDetalle{

    private final TipoMovimientoListadoRepository tipoMovimientoListadoRepository;
    private final TipoMovimientoRegistroRepository tipoMovimientoRegistroRepository;
    private final TipoMovimientoEdicionRepository tipoMovimientoEdicionRepository;
    private final TipoMovimientoDetalleRepository tipoMovimientoDetalleRepository;

    public TipoMovimientoService(
            TipoMovimientoListadoRepository tipoMovimientoListadoRepository,
            TipoMovimientoRegistroRepository tipoMovimientoRegistroRepository,
            TipoMovimientoEdicionRepository tipoMovimientoEdicionRepository,
            TipoMovimientoDetalleRepository tipoMovimientoDetalleRepository
    ) {
        this.tipoMovimientoListadoRepository = tipoMovimientoListadoRepository;
        this.tipoMovimientoRegistroRepository = tipoMovimientoRegistroRepository;
        this.tipoMovimientoEdicionRepository = tipoMovimientoEdicionRepository;
        this.tipoMovimientoDetalleRepository = tipoMovimientoDetalleRepository;
    }

    @Override
    @Cacheable(value = "tipoMovimientos", key = "#request.estado")
    public ResponseListaTipoMovimiento ListaTipoMovimiento(RequestListaTipoMovimiento request) {
        return tipoMovimientoListadoRepository.ListaTipoMovimiento(request);
    }

    @Override
    @CacheEvict(value = {"tipoMovimientos", "tipoMovimiento_detalle"}, allEntries = true)
    public ResponseRegistroTipoMovimiento RegistroTipoMovimiento(RequestRegistroTipoMovimiento request) {
        return tipoMovimientoRegistroRepository.RegistroTipoMovimiento(request);
    }

    @Override
    @CacheEvict(value = {"tipoMovimientos", "tipoMovimiento_detalle"}, allEntries = true)
    public ResponseEditarAllTipoMovimiento EditarAllTipoMovimiento(RequestEditarAllTipoMovimiento request) {
        return tipoMovimientoEdicionRepository.EditarAllTipoMovimiento(request);
    }

    @Override
    @CacheEvict(value = {"tipoMovimientos", "tipoMovimiento_detalle"}, allEntries = true)
    public ResponseEditarEstadoTipoMovimiento EditarEstadoTipoMovimiento(RequestEditarEstadoTipoMovimiento request, int estado) {
        return tipoMovimientoEdicionRepository.EditarEstadoTipoMovimiento(request, estado);
    }

    @Override
    @Cacheable(value = "tipoMovimiento_detalle", key = "#request.idTipoMovimiento")
    public ResponseDetalleTipoMovimiento DetalleTipoMovimiento(RequestDetalleTipoMovimiento request) {
        return tipoMovimientoDetalleRepository.DetalleTipoMovimiento(request);
    }

    @Override
    @Cacheable(value = "tipoMovimiento_detalle", key = "#request.codigo")
    public ResponseDetalleTipoMovimiento DetalleTipoMovimiento(RequestDetallePorCodigoTipoMovimiento request) {
        return tipoMovimientoDetalleRepository.DetalleTipoMovimiento(request);
    }
}
