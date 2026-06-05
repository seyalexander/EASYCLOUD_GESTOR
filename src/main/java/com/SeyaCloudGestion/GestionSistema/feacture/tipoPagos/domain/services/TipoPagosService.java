package com.SeyaCloudGestion.GestionSistema.feacture.tipoPagos.domain.services;

import com.SeyaCloudGestion.GestionSistema.feacture.tipoPagos.application.dto.request.*;
import com.SeyaCloudGestion.GestionSistema.feacture.tipoPagos.application.dto.response.*;
import com.SeyaCloudGestion.GestionSistema.feacture.tipoPagos.domain.interfaces.*;
import com.SeyaCloudGestion.GestionSistema.feacture.tipoPagos.infraestructure.persistence.repository.crud.*;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

public class TipoPagosService  implements ITipoPagosListado, ITipoPagosRegistro, ITipoPagosEdicion, ITipoPagosDetalle  {
    private final TipoPagosListadoRepository tipoPagosListadoRepository;
    private final TipoPagosRegistroRepository tipoPagosRegistroRepository;
    private final TipoPagosEdicionRepository tipoPagosEdicionRepository;
    private final TipoPagosDetalleRepository tipoPagosDetalleRepository;

    public TipoPagosService(TipoPagosListadoRepository tipoPagosListadoRepository, TipoPagosRegistroRepository tipoPagosRegistroRepository, TipoPagosEdicionRepository tipoPagosEdicionRepository, TipoPagosDetalleRepository tipoPagosDetalleRepository) {
        this.tipoPagosListadoRepository = tipoPagosListadoRepository;
        this.tipoPagosRegistroRepository = tipoPagosRegistroRepository;
        this.tipoPagosEdicionRepository = tipoPagosEdicionRepository;
        this.tipoPagosDetalleRepository = tipoPagosDetalleRepository;
    }
    @Override
    @Cacheable(value = "tipoPagos", key = "#request.estado")
    public ResponseListaTipoPagos ListaTipoPagos(RequestListaTipoPagos request) {
        return tipoPagosListadoRepository.ListaTipoPagos(request);
    }

    @Override
    @CacheEvict(value = {"tipoPagos", "tipoPagos_detalle"}, allEntries = true)
    public ResponseRegistroTipoPagos RegistroTipoPagos(RequestRegistroTipoPagos request) {
        return tipoPagosRegistroRepository.RegistroTipoPagos(request);
    }

    @Override
    @CacheEvict(value = {"tipoPagos", "tipoPagos_detalle"}, allEntries = true)
    public ResponseEditarAllTipoPagos EditarAllTipoPagos(RequestEditarAllTipoPagos request) {
        return tipoPagosEdicionRepository.EditarAllTipoPagos(request);
    }

    @Override
    @CacheEvict(value = {"tipoPagos", "tipoPagos_detalle"}, allEntries = true)
    public ResponseEditarEstadoTipoPagos EditarEstadoTipoPagos(RequestEditarEstadoTipoPagos request, int estado) {
        return tipoPagosEdicionRepository.EditarEstadoTipoPagos(request, estado);
    }

    @Override
    @Cacheable(value = "tipoPagos_detalle", key = "#request.idTipoPago")
    public ResponseDetalleTipoPagos DetalleTipoPagos(RequestDetalleTipoPagos request) {
        return tipoPagosDetalleRepository.DetalleTipoPagos(request);
    }
}