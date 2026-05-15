package com.SeyaCloudGestion.GestionSistema.feacture.rol.domain.services;

import com.SeyaCloudGestion.GestionSistema.feacture.rol.application.dto.request.*;
import com.SeyaCloudGestion.GestionSistema.feacture.rol.application.dto.response.*;
import com.SeyaCloudGestion.GestionSistema.feacture.rol.domain.interfaces.IRolDetalle;
import com.SeyaCloudGestion.GestionSistema.feacture.rol.domain.interfaces.IRolEdicion;
import com.SeyaCloudGestion.GestionSistema.feacture.rol.domain.interfaces.IRolListado;
import com.SeyaCloudGestion.GestionSistema.feacture.rol.domain.interfaces.IRolRegistro;
import com.SeyaCloudGestion.GestionSistema.feacture.rol.infraestructure.persistence.repository.crud.RolDetalleRepository;
import com.SeyaCloudGestion.GestionSistema.feacture.rol.infraestructure.persistence.repository.crud.RolEditarRepository;
import com.SeyaCloudGestion.GestionSistema.feacture.rol.infraestructure.persistence.repository.crud.RolListadoRepository;
import com.SeyaCloudGestion.GestionSistema.feacture.rol.infraestructure.persistence.repository.crud.RolRegistroRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class RolService implements IRolListado, IRolRegistro, IRolEdicion, IRolDetalle {

    private final RolListadoRepository rolListadoRepository;
    private final RolRegistroRepository rolRegistroRepository;
    private final RolEditarRepository rolEditarRepository;
    private final RolDetalleRepository rolDetalleRepository;

    public RolService(
            RolListadoRepository rolListadoRepository,
            RolRegistroRepository rolRegistroRepository,
            RolEditarRepository rolEditarRepository,
            RolDetalleRepository rolDetalleRepository
    ) {
        this.rolListadoRepository = rolListadoRepository;
        this.rolRegistroRepository = rolRegistroRepository;
        this.rolEditarRepository = rolEditarRepository;
        this.rolDetalleRepository = rolDetalleRepository;
    }

    @Override
    @Cacheable(value = "roles", key = "#request.estado")
    public ResponseListaRol ListaRol(RequestListaRol request) {
        return rolListadoRepository.ListaRol(request);
    }

    @Override
    @CacheEvict(value = {"roles", "rol_detalle"}, allEntries = true)
    public ResponseRegistroRol registrarRol(RequestRegistroRol request) {
        return rolRegistroRepository.registrarRol(request);
    }

    @Override
    @CacheEvict(value = {"roles", "rol_detalle"}, allEntries = true)
    public ResponseEditarAllRol EditarRol(RequestEditarAllRol request) {
        return rolEditarRepository.EditarRol(request);
    }

    @Override
    @CacheEvict(value = {"roles", "rol_detalle"}, allEntries = true)
    public ResponseEditarEstadoRol EditarEstadoRol(RequestEditarEstadoRol request, int estado) {
        return rolEditarRepository.EditarEstadoRol(request, estado);
    }

    @Override
    @Cacheable(value = "rol_detalle", key = "#request.idRol")
    public ResponseDetalleRol DetalleRol(RequestDetalleRol request) {
        return rolDetalleRepository.DetalleRol(request);
    }
}
