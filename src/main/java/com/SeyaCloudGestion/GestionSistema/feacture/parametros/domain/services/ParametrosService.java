package com.SeyaCloudGestion.GestionSistema.feacture.parametros.domain.services;

import com.SeyaCloudGestion.GestionSistema.feacture.parametros.application.dto.request.*;
import com.SeyaCloudGestion.GestionSistema.feacture.parametros.application.dto.response.*;
import com.SeyaCloudGestion.GestionSistema.feacture.parametros.domain.interfaces.*;
import com.SeyaCloudGestion.GestionSistema.feacture.parametros.infraestructure.persistence.repository.crud.*;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class ParametrosService implements IParametrosListado, IParametrosRegistro, IParametrosEdicion, IParametrosDetalle{
    private final ParametrosListadoRepository parametroListadoRepository;
    private final ParametrosRegistroRepository parametroRegistroRepository;
    private final ParametrosEdicionRepository parametroEdicionRepository;
    private final ParametrosDetalleRepository parametroDetalleRepository;

    public ParametrosService(ParametrosListadoRepository parametroListadoRepository, ParametrosRegistroRepository parametroRegistroRepository, ParametrosEdicionRepository parametroEdicionRepository, ParametrosDetalleRepository parametroDetalleRepository) {
        this.parametroListadoRepository = parametroListadoRepository;
        this.parametroRegistroRepository = parametroRegistroRepository;
        this.parametroEdicionRepository = parametroEdicionRepository;
        this.parametroDetalleRepository = parametroDetalleRepository;
    }


    @Override
    @Cacheable(value = "parametros", key = "#request.estado")
    public ResponseDetalleParametros DetalleParametros(RequestDetalleParametros request) {
        return parametroDetalleRepository.DetalleParametros(request);
    }

    @Override
    @CacheEvict(value = {"parametros", "parametros_detalle"}, allEntries = true)
    public ResponseEditarAllParametros EditarAllParametros(RequestEditarAllParametros request) {
        return parametroEdicionRepository.EditarAllParametros(request);
    }

    @Override
    @CacheEvict(value = {"parametros", "parametros_detalle"}, allEntries = true)
    public ResponseEditarEstadoParametros EditarEstadoParametros(RequestEditarEstadoParametros request, int estado) {
        return parametroEdicionRepository.EditarEstadoParametros(request, estado);
    }

    @Override
    @Cacheable(value = "parametros", key = "#request.estado")
    public ResponseListaParametros ListaParametros(RequestListaParametros request) {
        return parametroListadoRepository.ListaParametros(request);
    }

    @Override
    @CacheEvict(value = {"parametros", "parametros_detalle"}, allEntries = true)
    public ResponseRegistroParametros RegistroParametros(RequestRegistroParametros request) {
        return parametroRegistroRepository.RegistroParametros(request);
    }
}