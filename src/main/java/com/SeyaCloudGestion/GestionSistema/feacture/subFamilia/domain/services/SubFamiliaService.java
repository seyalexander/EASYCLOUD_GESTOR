package com.SeyaCloudGestion.GestionSistema.feacture.subFamilia.domain.services;

import com.SeyaCloudGestion.GestionSistema.feacture.familia.application.dto.request.RequestDetalleFamilia;
import com.SeyaCloudGestion.GestionSistema.feacture.subFamilia.application.dto.request.*;
import com.SeyaCloudGestion.GestionSistema.feacture.subFamilia.application.dto.response.*;
import com.SeyaCloudGestion.GestionSistema.feacture.subFamilia.domain.interfaces.ISubFamiliaDetalle;
import com.SeyaCloudGestion.GestionSistema.feacture.subFamilia.domain.interfaces.ISubFamiliaEdicion;
import com.SeyaCloudGestion.GestionSistema.feacture.subFamilia.domain.interfaces.ISubFamiliaLista;
import com.SeyaCloudGestion.GestionSistema.feacture.subFamilia.domain.interfaces.ISubFamiliaRegistro;
import com.SeyaCloudGestion.GestionSistema.feacture.subFamilia.infraestructure.persistence.repository.crud.SubFamiliaDetalleRepository;
import com.SeyaCloudGestion.GestionSistema.feacture.subFamilia.infraestructure.persistence.repository.crud.SubFamiliaEdicionRepository;
import com.SeyaCloudGestion.GestionSistema.feacture.subFamilia.infraestructure.persistence.repository.crud.SubFamiliaListadoRepository;
import com.SeyaCloudGestion.GestionSistema.feacture.subFamilia.infraestructure.persistence.repository.crud.SubFamiliaRegistroRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SubFamiliaService implements ISubFamiliaLista, ISubFamiliaRegistro, ISubFamiliaEdicion, ISubFamiliaDetalle {

    private final SubFamiliaListadoRepository subFamiliaListadoRepository;
    private final SubFamiliaRegistroRepository subFamiliaRegistroRepository;
    private final SubFamiliaEdicionRepository subFamiliaEdicionRepository;
    private final SubFamiliaDetalleRepository subFamiliaDetalleRepository;

    public SubFamiliaService(
            SubFamiliaListadoRepository subFamiliaListadoRepository,
            SubFamiliaRegistroRepository subFamiliaRegistroRepository,
            SubFamiliaEdicionRepository subFamiliaEdicionRepository,
            SubFamiliaDetalleRepository subFamiliaDetalleRepository
    ) {
        this.subFamiliaListadoRepository = subFamiliaListadoRepository;
        this.subFamiliaRegistroRepository = subFamiliaRegistroRepository;
        this.subFamiliaEdicionRepository = subFamiliaEdicionRepository;
        this.subFamiliaDetalleRepository = subFamiliaDetalleRepository;
    }

    @Override
    @Cacheable(value = "subFamilias", key = "#request.estado")
    public ResponseListaSubFamilia ListaSubFamilia(RequestListaSubFamilia request) {
        return subFamiliaListadoRepository.ListaSubFamilia(request);
    }

    @Override
    @CacheEvict(value = {"subFamilias", "subfamilia_detalle"}, allEntries = true)
    public ResponseRegistroSubFamilia RegistroSubFamilia(RequestRegistrarSubFamilia request) {
        return subFamiliaRegistroRepository.RegistroSubFamilia(request);
    }

    @Override
    @CacheEvict(value = {"subFamilias", "subfamilia_detalle"}, allEntries = true)
    public ResponseEditarAllSubFamilia EdicionAllSubFamilia(RequestEditarAllSubFamilia request) {
        return subFamiliaEdicionRepository.EdicionAllSubFamilia(request);
    }

    @Override
    @CacheEvict(value = {"subFamilias", "subfamilia_detalle"}, allEntries = true)
    public ResponseEditarEstadoSubFamilia EditarEstadoSubFamilia(RequestEditarEstadoSubFamilia request, int estado) {
        return subFamiliaEdicionRepository.EditarEstadoSubFamilia(request, estado);
    }

    @Override
    @Cacheable(value = "subfamilia_detalle", key = "#request.idSubFamilia")
    public ResponseDetalleSubFamilia DetalleSubFamilia(RequestDetalleSubFamilia request) {
        return subFamiliaDetalleRepository.DetalleSubFamilia(request);
    }
}
