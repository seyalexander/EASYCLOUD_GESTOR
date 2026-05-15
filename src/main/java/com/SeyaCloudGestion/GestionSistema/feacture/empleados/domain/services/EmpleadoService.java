package com.SeyaCloudGestion.GestionSistema.feacture.empleados.domain.services;

import com.SeyaCloudGestion.GestionSistema.feacture.empleados.application.dto.request.*;
import com.SeyaCloudGestion.GestionSistema.feacture.empleados.application.dto.response.*;
import com.SeyaCloudGestion.GestionSistema.feacture.empleados.domain.interfaces.IEmpleadoDetalle;
import com.SeyaCloudGestion.GestionSistema.feacture.empleados.domain.interfaces.IEmpleadoEdicion;
import com.SeyaCloudGestion.GestionSistema.feacture.empleados.domain.interfaces.IEmpleadoListado;
import com.SeyaCloudGestion.GestionSistema.feacture.empleados.domain.interfaces.IEmpleadoRegistro;
import com.SeyaCloudGestion.GestionSistema.feacture.empleados.infraestructure.persistence.repository.crud.EmpleadoDetalleRepository;
import com.SeyaCloudGestion.GestionSistema.feacture.empleados.infraestructure.persistence.repository.crud.EmpleadoEdicionRepository;
import com.SeyaCloudGestion.GestionSistema.feacture.empleados.infraestructure.persistence.repository.crud.EmpleadoListadoRepository;
import com.SeyaCloudGestion.GestionSistema.feacture.empleados.infraestructure.persistence.repository.crud.EmpleadoRegistroRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EmpleadoService implements IEmpleadoListado, IEmpleadoRegistro, IEmpleadoEdicion, IEmpleadoDetalle {

    private final EmpleadoListadoRepository empleadoListadoRepository;
    private final EmpleadoRegistroRepository empleadoRegistroRepository;
    private final EmpleadoEdicionRepository empleadoEdicionRepository;
    private final EmpleadoDetalleRepository empleadoDetalleRepository;

    public EmpleadoService(
            EmpleadoListadoRepository empleadoListadoRepository,
            EmpleadoRegistroRepository empleadoRegistroRepository,
            EmpleadoEdicionRepository empleadoEdicionRepository,
            EmpleadoDetalleRepository empleadoDetalleRepository
    ){
        this.empleadoListadoRepository = empleadoListadoRepository;
        this.empleadoRegistroRepository = empleadoRegistroRepository;
        this.empleadoEdicionRepository = empleadoEdicionRepository;
        this.empleadoDetalleRepository = empleadoDetalleRepository;
    }

    @Override
    @Cacheable(value = "empleados_lista", key = "#request.estado")
    public ResponseListaEmpleado ListaEmpleado(RequestListaEmpleado request) {
        return empleadoListadoRepository.ListaEmpleado(request);
    }

    @Override
    @CacheEvict(value = {"empleados_lista", "empleados_detalle"}, allEntries = true)
    public ResponseRegistroEmpleado RegistroEmpleado(RequestRegistroEmpleado request, long userAutenticado, long idEmpresa) {
        return empleadoRegistroRepository.RegistroEmpleado(request, userAutenticado, idEmpresa);
    }

    @Override
    @CacheEvict(value = {"empleados_lista", "empleados_detalle"}, allEntries = true)
    public ResponseEditarAllEmpleado EditarAllEmpleado(RequestEditarAllEmpleado request, long userAutenticado) {
        return empleadoEdicionRepository.EditarAllEmpleado(request, userAutenticado);
    }

    @Override
    @CacheEvict(value = {"empleados_lista", "empleados_detalle"}, allEntries = true)
    public ResponseEditarEstadoEmpleado EditarEstadoEmpleado(RequestEditarEstadoEmpleado request, int estado, long userAutenticado) {
        return empleadoEdicionRepository.EditarEstadoEmpleado(request, estado, userAutenticado);
    }

    @Override
    @Cacheable(value = "empleados_detalle", key = "#request.idEmpleado")
    public ResponseDetalleEmpleado DetalleEmpleado(RequestDetalleEmpleado request) {
        return empleadoDetalleRepository.DetalleEmpleado(request);
    }
}
