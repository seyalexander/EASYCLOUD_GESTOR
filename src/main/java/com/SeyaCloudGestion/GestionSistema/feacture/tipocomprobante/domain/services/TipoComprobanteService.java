package com.SeyaCloudGestion.GestionSistema.feacture.tipocomprobante.domain.services;// Generado a partir de la arquitectura de subFamilia.
import com.SeyaCloudGestion.GestionSistema.feacture.tipocomprobante.application.dto.request.*;
import com.SeyaCloudGestion.GestionSistema.feacture.tipocomprobante.application.dto.response.*;
import com.SeyaCloudGestion.GestionSistema.feacture.tipocomprobante.domain.interfaces.ITipoComprobanteDetalle;
import com.SeyaCloudGestion.GestionSistema.feacture.tipocomprobante.domain.interfaces.ITipoComprobanteEdicion;
import com.SeyaCloudGestion.GestionSistema.feacture.tipocomprobante.domain.interfaces.ITipoComprobanteListado;
import com.SeyaCloudGestion.GestionSistema.feacture.tipocomprobante.domain.interfaces.ITipoComprobanteRegistro;
import com.SeyaCloudGestion.GestionSistema.feacture.tipocomprobante.infraestructure.persistence.repository.crud.TipoComprobanteDetalleRepository;
import com.SeyaCloudGestion.GestionSistema.feacture.tipocomprobante.infraestructure.persistence.repository.crud.TipoComprobanteEdicionRepository;
import com.SeyaCloudGestion.GestionSistema.feacture.tipocomprobante.infraestructure.persistence.repository.crud.TipoComprobanteListadoRepository;
import com.SeyaCloudGestion.GestionSistema.feacture.tipocomprobante.infraestructure.persistence.repository.crud.TipoComprobanteRegistroRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class TipoComprobanteService implements ITipoComprobanteListado, ITipoComprobanteRegistro, ITipoComprobanteEdicion, ITipoComprobanteDetalle {

    private final TipoComprobanteListadoRepository tipoComprobanteListadoRepository;
    private final TipoComprobanteRegistroRepository tipoComprobanteRegistroRepository;
    private final TipoComprobanteEdicionRepository tipoComprobanteEdicionRepository;
    private final TipoComprobanteDetalleRepository tipoComprobanteDetalleRepository;

    public TipoComprobanteService(
            TipoComprobanteListadoRepository tipoComprobanteListadoRepository,
            TipoComprobanteRegistroRepository tipoComprobanteRegistroRepository,
            TipoComprobanteEdicionRepository tipoComprobanteEdicionRepository,
            TipoComprobanteDetalleRepository tipoComprobanteDetalleRepository
    ) {
        this.tipoComprobanteListadoRepository = tipoComprobanteListadoRepository;
        this.tipoComprobanteRegistroRepository = tipoComprobanteRegistroRepository;
        this.tipoComprobanteEdicionRepository = tipoComprobanteEdicionRepository;
        this.tipoComprobanteDetalleRepository = tipoComprobanteDetalleRepository;
    }

    @Override
    @Cacheable(value = "tipo_comprobantes_lista", key = "#request.estado")
    public ResponseListaTipoComprobante listaTipoComprobante(RequestListaTipoComprobante request) {
        return tipoComprobanteListadoRepository.listaTipoComprobante(request);
    }

    @Override
    @CacheEvict(value = {"tipo_comprobantes_lista", "tipo_comprobante_detalle"}, allEntries = true)
    public ResponseRegistroTipoComprobante RegistroTipoComprobante(RequestRegistroTipoComprobante request) {
        return tipoComprobanteRegistroRepository.RegistroTipoComprobante(request);
    }

    @Override
    @CacheEvict(value = {"tipo_comprobantes_lista", "tipo_comprobante_detalle"}, allEntries = true)
    public ResponseEditarAllTipoComprobante EditarAllTipoComprobante(RequestEditarAllTipoComprobante request) {
        return tipoComprobanteEdicionRepository.EditarAllTipoComprobante(request);
    }

    @Override
    @CacheEvict(value = {"tipo_comprobantes_lista", "tipo_comprobante_detalle"}, allEntries = true)
    public ResponseEditarEstadoTipoComprobante EditarEstadoTipoComprobante(RequestEditarEstadoTipoComprobante request, int estado) {
        return tipoComprobanteEdicionRepository.EditarEstadoTipoComprobante(request, estado);
    }

    @Override
    @Cacheable(value = "tipo_comprobante_detalle", key = "#request.idTipoComprobante")
    public ResponseDetalleTipoComprobante DetalleTipoComprobante(RequestDetalleTipoComprobante request) {
        return tipoComprobanteDetalleRepository.DetalleTipoComprobante(request);
    }
}