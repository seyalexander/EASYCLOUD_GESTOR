package com.SeyaCloudGestion.GestionSistema.feacture.marca.domain.services;

import com.SeyaCloudGestion.GestionSistema.feacture.marca.application.dto.request.*;
import com.SeyaCloudGestion.GestionSistema.feacture.marca.application.dto.response.*;
import com.SeyaCloudGestion.GestionSistema.feacture.marca.domain.interfaces.IMarcaDetalle;
import com.SeyaCloudGestion.GestionSistema.feacture.marca.domain.interfaces.IMarcaEdicion;
import com.SeyaCloudGestion.GestionSistema.feacture.marca.domain.interfaces.IMarcaListado;
import com.SeyaCloudGestion.GestionSistema.feacture.marca.domain.interfaces.IMarcaRegistro;
import com.SeyaCloudGestion.GestionSistema.feacture.marca.infraestructure.persistence.repository.crud.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MarcaService implements IMarcaListado, IMarcaDetalle, IMarcaRegistro, IMarcaEdicion {

    private final MarcaListaRepository marcaListaRepository;
    private final MarcaDetalleRepository marcaDetalleRepository;
    private final MarcaEdicionRepository marcaEdicionRepository;
    private final MarcaRegistroRepository marcaRegistroRepository;

    public MarcaService(
            MarcaListaRepository marcaListaRepository,
            MarcaDetalleRepository marcaDetalleRepository,
            MarcaEdicionRepository marcaEdicionRepository,
            MarcaRegistroRepository marcaRegistroRepository
    ) {
        this.marcaListaRepository = marcaListaRepository;
        this.marcaDetalleRepository = marcaDetalleRepository;
        this.marcaEdicionRepository = marcaEdicionRepository;
        this.marcaRegistroRepository = marcaRegistroRepository;
    }

    @Override
    @Cacheable(value = "marca_lista", key = "#request.estado")
    public ResponseListaMarca ListaMarca(RequestListaMarca request) {
        return marcaListaRepository.ListaMarca(request);
    }

    @Override
    @Cacheable(value = "marca_detalle", key = "#request.idMarca")
    public ResponseDetalleMarca DetalleMarca(RequestDetalleMarca marca) {
        return marcaDetalleRepository.DetalleMarca(marca);
    }


    @Override
    @CacheEvict(value = {"marca_lista", "marca_detalle"}, allEntries = true)
    public ResponseRegistroMarca RegistroMarca(RequestRegistroMarca request) {
        return marcaRegistroRepository.RegistroMarca(request);
    }

    @Override
    @CacheEvict(value = {"marca_lista", "marca_detalle"}, allEntries = true)
    public ResponseEditarAllMarca EditarAllMarca(RequestEditarAllMarca request) {
        return marcaEdicionRepository.EditarAllMarca(request);
    }

    @Override
    @CacheEvict(value = {"marca_lista", "marca_detalle"}, allEntries = true)
    public ResponseEditarEstadoMarca EditarEstadoMarca(RequestEditarEstadoMarca request, int estado) {
        return marcaEdicionRepository.EditarEstadoMarca(request, estado);
    }
}
