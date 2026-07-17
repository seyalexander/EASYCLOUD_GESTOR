package com.SeyaCloudGestion.GestionSistema.feacture.ajustes.domain.services;

import com.SeyaCloudGestion.GestionSistema.feacture.ajustes.application.dto.request.*;
import com.SeyaCloudGestion.GestionSistema.feacture.ajustes.application.dto.response.*;
import com.SeyaCloudGestion.GestionSistema.feacture.ajustes.domain.interfaces.IAjustesDetalle;
import com.SeyaCloudGestion.GestionSistema.feacture.ajustes.domain.interfaces.IAjustesListado;
import com.SeyaCloudGestion.GestionSistema.feacture.ajustes.domain.interfaces.IAjustesRegistro;
import com.SeyaCloudGestion.GestionSistema.feacture.ajustes.infraestructure.persistence.repository.crud.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AjustesService implements IAjustesListado, IAjustesRegistro, IAjustesDetalle {

    private final AjusteListadoRepository ajustesListadoRepository;
    private final AjusteRegistroRepository ajustesRegistroRepository;
    private final AjusteDetalleRepository ajustesDetalleRepository;

    public AjustesService(
            AjusteListadoRepository ajustesListadoRepository, AjusteRegistroRepository ajustesRegistroRepository, AjusteDetalleRepository ajustesDetalleRepository) {
        this.ajustesListadoRepository = ajustesListadoRepository;
        this.ajustesRegistroRepository = ajustesRegistroRepository;
        this.ajustesDetalleRepository = ajustesDetalleRepository;
    }

    @Override
    @Cacheable(
            value = "ajustes_lista",
            key = "#request.idArticulo + '_' + #request.idAlmacen"
    )
    public ResponseListaAjuste listaAjustes(RequestListaAjuste request) {
        return ajustesListadoRepository.listaAjustes(request);
    }

    @Override
    @CacheEvict(value = {"ajustes_lista", "ajuste_detalle"}, allEntries = true)
    public ResponseRegistroAjuste RegistroAjustes(RequestRegistrarAjuste request) {
        return ajustesRegistroRepository.RegistroAjustes(request);
    }

    @Override
    @Cacheable(value = "ajuste_detalle", key = "#request.idAjuste")
    public ResponseDetalleAjuste DetalleAjustes(RequestDetalleAjuste request) {
        return ajustesDetalleRepository.DetalleAjustes(request);
    }
}