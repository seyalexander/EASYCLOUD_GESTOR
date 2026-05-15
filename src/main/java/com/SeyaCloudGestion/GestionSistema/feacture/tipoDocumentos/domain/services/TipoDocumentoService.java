package com.SeyaCloudGestion.GestionSistema.feacture.tipoDocumentos.domain.services;

import com.SeyaCloudGestion.GestionSistema.feacture.moneda.application.dto.request.RequestListaMonedas;
import com.SeyaCloudGestion.GestionSistema.feacture.tipoDocumentos.application.dto.request.*;
import com.SeyaCloudGestion.GestionSistema.feacture.tipoDocumentos.application.dto.response.*;
import com.SeyaCloudGestion.GestionSistema.feacture.tipoDocumentos.domain.interfaces.ITipoDocumentoDetalle;
import com.SeyaCloudGestion.GestionSistema.feacture.tipoDocumentos.domain.interfaces.ITipoDocumentoEdicion;
import com.SeyaCloudGestion.GestionSistema.feacture.tipoDocumentos.domain.interfaces.ITipoDocumentoListado;
import com.SeyaCloudGestion.GestionSistema.feacture.tipoDocumentos.domain.interfaces.ITipoDocumentoRegistro;
import com.SeyaCloudGestion.GestionSistema.feacture.tipoDocumentos.infraestructure.persistence.repository.crud.TipoDocumentoDetalleRepository;
import com.SeyaCloudGestion.GestionSistema.feacture.tipoDocumentos.infraestructure.persistence.repository.crud.TipoDocumentoEdicionRepository;
import com.SeyaCloudGestion.GestionSistema.feacture.tipoDocumentos.infraestructure.persistence.repository.crud.TipoDocumentoListadoRepository;
import com.SeyaCloudGestion.GestionSistema.feacture.tipoDocumentos.infraestructure.persistence.repository.crud.TipoDocumentoRegistroRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class TipoDocumentoService implements ITipoDocumentoListado, ITipoDocumentoRegistro, ITipoDocumentoEdicion, ITipoDocumentoDetalle {

    private final TipoDocumentoListadoRepository tipoDocumentoListadoRepository;
    private final TipoDocumentoRegistroRepository tipoDocumentoRegistroRepository;
    private final TipoDocumentoEdicionRepository tipoDocumentoEdicionRepository;
    private final TipoDocumentoDetalleRepository tipoDocumentoDetalleRepository;

    public TipoDocumentoService(
            TipoDocumentoListadoRepository tipoDocumentoListadoRepository,
            TipoDocumentoRegistroRepository tipoDocumentoRegistroRepository,
            TipoDocumentoEdicionRepository tipoDocumentoEdicionRepository,
            TipoDocumentoDetalleRepository tipoDocumentoDetalleRepository
    ) {
        this.tipoDocumentoListadoRepository = tipoDocumentoListadoRepository;
        this.tipoDocumentoRegistroRepository = tipoDocumentoRegistroRepository;
        this.tipoDocumentoEdicionRepository = tipoDocumentoEdicionRepository;
        this.tipoDocumentoDetalleRepository = tipoDocumentoDetalleRepository;
    }

    @Override
    @Cacheable(value = "tipoDocumentos_lista", key = "#request.estado")
    public ResponseListaTipoDocumento ListaTipoDocumento(RequestListaTipoDocumentos request) {
        return tipoDocumentoListadoRepository.ListaTipoDocumento(request);
    }

    @Override
    @CacheEvict(value = {"tipoDocumentos_lista","tipoDocumentos_detalle"}, allEntries = true)
    public ResponseRegistroTipoDocumento RegistroTipoDocumento(RequestRegistroTipoDocumento request, long userAutenticado) {
        return tipoDocumentoRegistroRepository.RegistroTipoDocumento(request, userAutenticado);
    }

    @Override
    @CacheEvict(value = {"tipoDocumentos_lista","tipoDocumentos_detalle"}, allEntries = true)
    public ResponseEditarAllTipoDocumento EditarTipoDocumento(RequestEditarAllTipoDocumento request, long userAutenticado) {
        return tipoDocumentoEdicionRepository.EditarTipoDocumento(request, userAutenticado);
    }

    @Override
    @CacheEvict(value = {"tipoDocumentos_lista","tipoDocumentos_detalle"}, allEntries = true)
    public ResponseEditarEstadoTipoDocumento EditarEstadoTipoDocumento(RequestEditarEstadoTipoDocumento request, int estado, long userAutenticado) {
        return tipoDocumentoEdicionRepository.EditarEstadoTipoDocumento(request, estado, userAutenticado);
    }

    @Override
    @Cacheable(value = "tipoDocumentos_detalle", key = "#request.idTipoDocumentos")
    public ResponseDetalleTipoDocumento DetalleTipoDocumento(RequestDetalleTipoDocumento request) {
        return tipoDocumentoDetalleRepository.DetalleTipoDocumento(request);
    }
}
