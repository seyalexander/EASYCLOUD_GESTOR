package com.SeyaCloudGestion.GestionSistema.feacture.familia.domain.services;

import com.SeyaCloudGestion.GestionSistema.feacture.familia.application.dto.request.*;
import com.SeyaCloudGestion.GestionSistema.feacture.familia.application.dto.response.*;
import com.SeyaCloudGestion.GestionSistema.feacture.familia.domain.interfaces.IFamiliaDetalle;
import com.SeyaCloudGestion.GestionSistema.feacture.familia.domain.interfaces.IFamiliaEdicion;
import com.SeyaCloudGestion.GestionSistema.feacture.familia.domain.interfaces.IFamiliaListado;
import com.SeyaCloudGestion.GestionSistema.feacture.familia.domain.interfaces.IFamiliaRegistro;
import com.SeyaCloudGestion.GestionSistema.feacture.familia.infraestructure.persistence.repository.crud.FamiliaDetalleRepository;
import com.SeyaCloudGestion.GestionSistema.feacture.familia.infraestructure.persistence.repository.crud.FamiliaEdicionRepository;
import com.SeyaCloudGestion.GestionSistema.feacture.familia.infraestructure.persistence.repository.crud.FamiliaListadoRepository;
import com.SeyaCloudGestion.GestionSistema.feacture.familia.infraestructure.persistence.repository.crud.FamiliaRegistroRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class FamiliaService implements IFamiliaListado, IFamiliaRegistro, IFamiliaEdicion, IFamiliaDetalle {

    private final FamiliaListadoRepository familiaListadoRepository;
    private final FamiliaRegistroRepository familiaRegistroRepository;
    private final FamiliaEdicionRepository familiaEdicionRepository;
    private final FamiliaDetalleRepository familiaDetalleRepository;

    public FamiliaService(
            FamiliaListadoRepository familiaListadoRepository,
            FamiliaRegistroRepository familiaRegistroRepository,
            FamiliaEdicionRepository familiaEdicionRepository,
            FamiliaDetalleRepository familiaDetalleRepository
    ) {
        this.familiaListadoRepository = familiaListadoRepository;
        this.familiaRegistroRepository = familiaRegistroRepository;
        this.familiaEdicionRepository = familiaEdicionRepository;
        this.familiaDetalleRepository = familiaDetalleRepository;
    }

    @Override
    @Cacheable(value = "familias_lista", key = "#request.estado")
    public ResponseListaFamilia listaFamilia(RequestListaFamilia request) {
        return familiaListadoRepository.listaFamilia(request);
    }

    @Override
    @CacheEvict(value = {"familias_lista","familia_detalle"}, allEntries = true)
    public ResponseRegistroFamilia RegistroFamilia(RequestRegistroFamilia request) {
        return familiaRegistroRepository.RegistroFamilia(request);
    }

    @Override
    @CacheEvict(value = {"familias_lista","familia_detalle"}, allEntries = true)
    public ResponseEditarAllFamilia EditarAllFamilia(RequestEditarAllFamilia request) {
        return familiaEdicionRepository.EditarAllFamilia(request);
    }

    @Override
    @CacheEvict(value = {"familias_lista","familia_detalle"}, allEntries = true)
    public ResponseEditarEstadoFamilia EditarEstadoFamilia(RequestEditarEstadoFamilia request, int estado) {
        return familiaEdicionRepository.EditarEstadoFamilia(request,estado);
    }

    @Override
    @Cacheable(value = "familia_detalle", key = "#request.idFamilia")
    public ResponseDetalleFamilia DetalleFamilia(RequestDetalleFamilia request) {
        return familiaDetalleRepository.DetalleFamilia(request);
    }
}
