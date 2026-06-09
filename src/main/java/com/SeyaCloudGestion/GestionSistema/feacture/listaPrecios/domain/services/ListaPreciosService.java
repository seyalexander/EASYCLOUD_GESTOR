package com.SeyaCloudGestion.GestionSistema.feacture.listaPrecios.domain.services;

import com.SeyaCloudGestion.GestionSistema.feacture.listaPrecios.application.dto.request.*;
import com.SeyaCloudGestion.GestionSistema.feacture.listaPrecios.application.dto.response.*;
import com.SeyaCloudGestion.GestionSistema.feacture.listaPrecios.domain.interfaces.IListaPreciosDetalle;
import com.SeyaCloudGestion.GestionSistema.feacture.listaPrecios.domain.interfaces.IListaPreciosEdicion;
import com.SeyaCloudGestion.GestionSistema.feacture.listaPrecios.domain.interfaces.IListaPreciosListado;
import com.SeyaCloudGestion.GestionSistema.feacture.listaPrecios.domain.interfaces.IListaPreciosRegistro;
import com.SeyaCloudGestion.GestionSistema.feacture.listaPrecios.infraestructure.persistence.repository.crud.ListaPreciosDetalleRepository;
import com.SeyaCloudGestion.GestionSistema.feacture.listaPrecios.infraestructure.persistence.repository.crud.ListaPreciosEdicionRepository;
import com.SeyaCloudGestion.GestionSistema.feacture.listaPrecios.infraestructure.persistence.repository.crud.ListaPreciosListaRepository;
import com.SeyaCloudGestion.GestionSistema.feacture.listaPrecios.infraestructure.persistence.repository.crud.ListaPreciosRegistroRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ListaPreciosService implements IListaPreciosListado, IListaPreciosDetalle, IListaPreciosEdicion, IListaPreciosRegistro {

    private final ListaPreciosListaRepository listaPreciosListaRepository;
    private final ListaPreciosDetalleRepository listaPreciosDetalleRepository;
    private final ListaPreciosEdicionRepository listaPreciosEdicionRepository;
    private final ListaPreciosRegistroRepository listaPreciosRegistroRepository;

    public ListaPreciosService(ListaPreciosListaRepository listaPreciosListaRepository, ListaPreciosDetalleRepository listaPreciosDetalleRepository, ListaPreciosEdicionRepository listaPreciosEdicionRepository, ListaPreciosRegistroRepository listaPreciosRegistroRepository) {
        this.listaPreciosListaRepository = listaPreciosListaRepository;
        this.listaPreciosDetalleRepository = listaPreciosDetalleRepository;
        this.listaPreciosEdicionRepository = listaPreciosEdicionRepository;
        this.listaPreciosRegistroRepository = listaPreciosRegistroRepository;
    }

    @Override
    @Cacheable(value = "listaPrecios_lista", key = "#request.estado")
    public ResponseListaListaPrecios ListarListaPrecios(RequestListarListaPrecios request) {
        return listaPreciosListaRepository.ListarListaPrecios(request);
    }

    @Override
    @Cacheable(value = "listaPrecios_detalle", key = "#request.idListaPrecios")
    public ResponseDetalleListaPrecios DetalleListaPrecios(RequestDetalleListaPrecios request) {
        return listaPreciosDetalleRepository.DetalleListaPrecios(request);
    }

    @Override
    @CacheEvict(value = {"listaPrecios_lista", "listaPrecios_detalle"}, allEntries = true)
    public ResponseEditarAllListaPrecios EditarAllListaPrecios(RequestEditarAllListaPrecios request) {
        return listaPreciosEdicionRepository.EditarAllListaPrecios(request);
    }

    @Override
    @CacheEvict(value = {"listaPrecios_lista", "listaPrecios_detalle"}, allEntries = true)
    public ResponseEditarEstadoListaPrecios EditarEstadoListaPrecios(RequestEditarEstadoListaPrecios request, int estado) {
        return listaPreciosEdicionRepository.EditarEstadoListaPrecios(request ,estado);
    }

    @Override
    @CacheEvict(value = {"listaPrecios_lista", "listaPrecios_detalle"}, allEntries = true)
    public ResponseRegistroListaPrecios RegistroListaPrecios(RequestRegistroListaPrecios request) {
        return listaPreciosRegistroRepository.RegistroListaPrecios(request);
    }
}
