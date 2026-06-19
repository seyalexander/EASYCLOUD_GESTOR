package com.SeyaCloudGestion.GestionSistema.feacture.kardex.domain.services;

import com.SeyaCloudGestion.GestionSistema.feacture.kardex.application.dto.request.*;
import com.SeyaCloudGestion.GestionSistema.feacture.kardex.application.dto.response.*;
import com.SeyaCloudGestion.GestionSistema.feacture.kardex.domain.interfaces.IKardexDetalle;
import com.SeyaCloudGestion.GestionSistema.feacture.kardex.domain.interfaces.IKardexListado;
import com.SeyaCloudGestion.GestionSistema.feacture.kardex.domain.interfaces.IKardexRegistro;
import com.SeyaCloudGestion.GestionSistema.feacture.kardex.infraestructure.persistence.repository.crud.KardexDetalleRepository;
import com.SeyaCloudGestion.GestionSistema.feacture.kardex.infraestructure.persistence.repository.crud.KardexListadoRepository;
import com.SeyaCloudGestion.GestionSistema.feacture.kardex.infraestructure.persistence.repository.crud.KardexRegistroRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class KardexService implements IKardexListado, IKardexRegistro, IKardexDetalle {

    private final KardexListadoRepository kardexListadoRepository;
    private final KardexRegistroRepository kardexRegistroRepository;
    private final KardexDetalleRepository kardexDetalleRepository;

    public KardexService(
            KardexListadoRepository kardexListadoRepository,
            KardexRegistroRepository kardexRegistroRepository,
            KardexDetalleRepository kardexDetalleRepository
    ) {
        this.kardexListadoRepository = kardexListadoRepository;
        this.kardexRegistroRepository = kardexRegistroRepository;
        this.kardexDetalleRepository = kardexDetalleRepository;
    }

    @Override
    @Cacheable(value = "kardex_lista", key = "#request.idArticulo + '_' + #request.fechaInicio")
    public ResponseListaKardex listaKardex(RequestListaKardex request) {
        return kardexListadoRepository.listaKardex(request);
    }

    @Override
    @CacheEvict(value = {"kardex_lista", "kardex_detalle"}, allEntries = true)
    public ResponseRegistroKardex RegistroKardex(RequestRegistroKardex request) {
        return kardexRegistroRepository.RegistroKardex(request);
    }

    @Override
    @Cacheable(value = "kardex_detalle", key = "#request.idArticulo")
    public ResponseDetalleKardex DetalleKardex(RequestDetalleKardex request) {
        return kardexDetalleRepository.DetalleKardex(request);
    }
}