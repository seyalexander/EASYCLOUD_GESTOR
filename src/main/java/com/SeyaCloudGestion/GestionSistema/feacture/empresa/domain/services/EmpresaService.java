package com.SeyaCloudGestion.GestionSistema.feacture.empresa.domain.services;

import com.SeyaCloudGestion.GestionSistema.feacture.empresa.application.dto.request.*;
import com.SeyaCloudGestion.GestionSistema.feacture.empresa.application.dto.response.*;
import com.SeyaCloudGestion.GestionSistema.feacture.empresa.domain.interfaces.IEmpresaDetalle;
import com.SeyaCloudGestion.GestionSistema.feacture.empresa.domain.interfaces.IEmpresaEdicion;
import com.SeyaCloudGestion.GestionSistema.feacture.empresa.domain.interfaces.IEmpresaListado;
import com.SeyaCloudGestion.GestionSistema.feacture.empresa.domain.interfaces.IEmpresaRegistro;
import com.SeyaCloudGestion.GestionSistema.feacture.empresa.infraestructure.persistence.repository.crud.EmpresaDetalleRepository;
import com.SeyaCloudGestion.GestionSistema.feacture.empresa.infraestructure.persistence.repository.crud.EmpresaEdicionRepository;
import com.SeyaCloudGestion.GestionSistema.feacture.empresa.infraestructure.persistence.repository.crud.EmpresaListadoRepository;
import com.SeyaCloudGestion.GestionSistema.feacture.empresa.infraestructure.persistence.repository.crud.EmpresaRegistroRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EmpresaService implements IEmpresaListado, IEmpresaRegistro, IEmpresaEdicion, IEmpresaDetalle {

    private final EmpresaListadoRepository empresaListadoRepository;
    private final EmpresaRegistroRepository empresaRegistroRepository;
    private final EmpresaEdicionRepository empresaEdicionRepository;
    private final EmpresaDetalleRepository empresaDetalleRepository;

    public EmpresaService(
            EmpresaListadoRepository empresaListadoRepository,
            EmpresaRegistroRepository empresaRegistroRepository,
            EmpresaEdicionRepository empresaEdicionRepository,
            EmpresaDetalleRepository empresaDetalleRepository
    ) {
        this.empresaListadoRepository = empresaListadoRepository;
        this.empresaRegistroRepository = empresaRegistroRepository;
        this.empresaEdicionRepository = empresaEdicionRepository;
        this.empresaDetalleRepository = empresaDetalleRepository;
    }

    @Override
    @Cacheable(value = "empresa_lista", key = "#request.estado")
    public ResponseListaEmpresa listaEmpresa(RequestListaEmpresa request) {
        return empresaListadoRepository.listaEmpresa(request);
    }

    @Override
    @CacheEvict(value = {"empresa_lista", "empresa_detalle"}, allEntries = true)
    public ResponseRegistroEmpresa registrarEmpresa(RequestRegistroEmpresa request, long userAutenticado) {
        return empresaRegistroRepository.registrarEmpresa(request, userAutenticado);
    }

    @Override
    @CacheEvict(value = {"empresa_lista", "empresa_detalle"}, allEntries = true)
    public ResponseEditarEstadoEmpresa editarEstadoEmpresa(RequestEditarEstadoEmpresa request, int estado, long userAutenticado) {
        return empresaEdicionRepository.editarEstadoEmpresa(request, estado, userAutenticado);
    }


    @Override
    @CacheEvict(value = {"empresa_lista", "empresa_detalle"}, allEntries = true)
    public ResponseEditarAllEmpresa editarAllEmpresa(RequestEditarAllEmpresa request, long userAutenticado) {
        return empresaEdicionRepository.editarAllEmpresa(request, userAutenticado);
    }

    @Override
    @Cacheable(value = "empresa_detalle", key = "#request.idEmpresa")
    public ResponseDetalleEmpresa DetalleEmpresa(RequestDetalleEmpresa request) {
        return empresaDetalleRepository.DetalleEmpresa(request);
    }
}
