package com.SeyaCloudGestion.GestionSistema.feacture.articulos.domain.services;

import com.SeyaCloudGestion.GestionSistema.feacture.articulos.application.dto.request.*;
import com.SeyaCloudGestion.GestionSistema.feacture.articulos.application.dto.response.*;
import com.SeyaCloudGestion.GestionSistema.feacture.articulos.domain.interfaces.*;
import com.SeyaCloudGestion.GestionSistema.feacture.articulos.infraestructure.persistence.repository.crud.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ArticulosService implements IArticulosRegistro, IArticulosListado, IArticulosEdicion, IArticulosDetalle {

    private final ArticulosRegistroRepository articulosRegistroRepository;
    private final ArticuloListadoRepository articuloListadoRepository;
    private final ArticulosEdicionRepository articulosEdicionRepository;
    private final ArticuloDetalleRepository articuloDetalleRepository;

    public ArticulosService (ArticulosRegistroRepository articulosRegistroRepository, ArticuloListadoRepository articuloListadoRepository, ArticulosEdicionRepository articulosEdicionRepository, ArticuloDetalleRepository articuloDetalleRepository) {
        this.articulosRegistroRepository = articulosRegistroRepository;
        this.articuloListadoRepository = articuloListadoRepository;
        this.articulosEdicionRepository = articulosEdicionRepository;
        this.articuloDetalleRepository = articuloDetalleRepository;
    }
    @Override
    @Cacheable(value = "articulos_lista", key = "#request.estado")
    public ResponseListaArticulo ListaArticulos(RequestListaArticulo request) {
        return articuloListadoRepository.ListaArticulos(request);
    }
    @Override
    @CacheEvict(value = {"articulos_lista","articulo_detalle"}, allEntries = true)
    public ResponseRegistroArticulo registrarArticulo(RequestRegistroArticulo request) {
        return articulosRegistroRepository.registrarArticulo(request);
    }

    @Override
    @CacheEvict(value = {"articulos_lista", "articulo_detalle"}, allEntries = true)
    public ResponseEditarAllArticulo EditarAllArticulos(RequestEditarAllArticulo request) {
        return articulosEdicionRepository.EditarAllArticulos(request);
    }

    @Override
    @CacheEvict(value = {"articulos_lista", "articulo_detalle"}, allEntries = true)
    public ResponseEditarEstadoArticulo EditarEstadoArticulos(RequestEditarEstadoArticulo request, int estado) {
        return articulosEdicionRepository.EditarEstadoArticulos(request, estado);
    }

    @Override
    @Cacheable(value = "articulo_detalle", key = "#request.idArticulo")
    public ResponseDetalleArticulo DetalleArticulos(RequestDetalleArticulo request) {
        return articuloDetalleRepository.DetalleArticulos(request);
    }
}
