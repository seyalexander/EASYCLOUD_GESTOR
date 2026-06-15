package com.SeyaCloudGestion.GestionSistema.feacture.serieDocumento.domain.services;

import com.SeyaCloudGestion.GestionSistema.feacture.serieDocumento.application.dto.request.*;
import com.SeyaCloudGestion.GestionSistema.feacture.serieDocumento.application.dto.response.*;
import com.SeyaCloudGestion.GestionSistema.feacture.serieDocumento.domain.interfaces.*;
import com.SeyaCloudGestion.GestionSistema.feacture.serieDocumento.infraestructure.persistence.repository.crud.*;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class SerieDocumentoService  implements ISerieDocumentoDetalle, ISerieDocumentoEdicion, ISerieDocumentoListado, ISerieDocumentoRegistro {

    private final SerieDocumentoListadoRepository   serieDocumentoListadoRepository;
    private final SerieDocumentoRegistroRepository  serieDocumentoRegistroRepository;
    private final SerieDocumentoEdicionRepository  serieDocumentoEdicionRepository;
    private final SerieDocumentoDetalleRepository  serieDocumentoDetalleRepository;

    public SerieDocumentoService(SerieDocumentoListadoRepository serieDocumentoListadoRepository, SerieDocumentoRegistroRepository serieDocumentoRegistroRepository, SerieDocumentoEdicionRepository serieDocumentoEdicionRepository, SerieDocumentoDetalleRepository serieDocumentoDetalleRepository) {
        this.serieDocumentoListadoRepository = serieDocumentoListadoRepository;
        this.serieDocumentoRegistroRepository = serieDocumentoRegistroRepository;
        this.serieDocumentoEdicionRepository = serieDocumentoEdicionRepository;
        this.serieDocumentoDetalleRepository = serieDocumentoDetalleRepository;
    }


    @Override
    @Cacheable(value = "serieDocumentos_detalle", key = "#request.idSeries")
    public ResponseDetalleSerieDocumento DetalleSerieDocumento(RequestDetalleSeries request) {
        return serieDocumentoDetalleRepository.DetalleSerieDocumento(request);
    }

    @Override
    @CacheEvict(value = {"serieDocumentos", "serieDocumentos_detalle"}, allEntries = true)
    public ResponseEditarAllSerieDocumento EditarAllSerieDocumento(RequestEditarAllSeries request) {
        return serieDocumentoEdicionRepository.EditarAllSerieDocumento(request);
    }

    @Override
    @CacheEvict(value = {"serieDocumentos", "serieDocumentos_detalle"}, allEntries = true)
    public ResponseEditarEstadoSerieDocumento EditarEstadoSerieDocumento(RequestEditarEstadoSeries request, int estado) {
        return serieDocumentoEdicionRepository.EditarEstadoSerieDocumento(request, estado);
    }

    @Override
    @Cacheable(value = "serieDocumentos", key = "#request.estado")
    public ResponseListaSerieDocumento listaSerieDocumento(RequestListaSeries request) {
        return serieDocumentoListadoRepository.listaSerieDocumento(request);
    }

    @Override
    @CacheEvict(value = {"serieDocumentos", "serieDocumentos_detalle"}, allEntries = true)
    public ResponseRegistroSerieDocumento RegistroSerieDocumento(RequestRegistroSeries request, long correlativo) {
        return serieDocumentoRegistroRepository.RegistroSerieDocumento(request,correlativo);
    }
}