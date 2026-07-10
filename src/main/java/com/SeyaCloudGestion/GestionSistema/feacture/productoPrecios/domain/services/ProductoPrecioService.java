package com.SeyaCloudGestion.GestionSistema.feacture.productoPrecios.domain.services;

import com.SeyaCloudGestion.GestionSistema.feacture.productoPrecios.application.dto.request.*;
import com.SeyaCloudGestion.GestionSistema.feacture.productoPrecios.application.dto.response.*;
import com.SeyaCloudGestion.GestionSistema.feacture.productoPrecios.domain.interfaces.*;
import com.SeyaCloudGestion.GestionSistema.feacture.productoPrecios.infraestructure.persistence.repository.crud.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ProductoPrecioService implements IProductoPrecioListado, IProductoPrecioEdicion, IProductoPrecioRegistro ,IProductoPrecioDetalle{
    private final ProductoPrecioEdicionRepository  productoPrecioEdicionRepository;
    private final ProductoPrecioRegistroRepository  productoPrecioRegistroRepository;
    private final ProductoPrecioListadoRepository  productoPrecioListadoRepository;
    private final ProductoPrecioDetalleRepository  productoPrecioDetalleRepository;

    public ProductoPrecioService(ProductoPrecioEdicionRepository productoPrecioEdicionRepository, ProductoPrecioRegistroRepository productoPrecioRegistroRepository, ProductoPrecioListadoRepository productoPrecioListadoRepository, ProductoPrecioDetalleRepository productoPrecioDetalleRepository) {
        this.productoPrecioEdicionRepository = productoPrecioEdicionRepository;
        this.productoPrecioRegistroRepository = productoPrecioRegistroRepository;
        this.productoPrecioListadoRepository = productoPrecioListadoRepository;
        this.productoPrecioDetalleRepository = productoPrecioDetalleRepository;
    }

    @Override
    @Cacheable(value = "productoPrecios_lista", key = "#request.estado")
    public ResponseListaProductoPrecio ListaProductoPrecio(RequestListaProductoPrecio request) {
        return productoPrecioListadoRepository.ListaProductoPrecio(request);
    }

    @Override
    @CacheEvict(value = {"productoPrecios_lista"}, allEntries = true)
    public ResponseRegistroProductoPrecio RegistroProductoPrecio(RequestRegistroProductoPrecio request) {
        return productoPrecioRegistroRepository.RegistroProductoPrecio(request);
    }

    @Override
    @CacheEvict(value = {"productoPrecios_lista"}, allEntries = true)
    public ResponseEditarAllProductoPrecio EditarAllProductoPrecio(RequestEditarAllProductoPrecio request) {
        return productoPrecioEdicionRepository.EditarAllProductoPrecio(request);
    }

    @Override
    @CacheEvict(value = {"productoPrecios_lista"}, allEntries = true)
    public ResponseEditarEstadoProductoPrecio EditarEstadoProductoPrecio(RequestEditarEstadoProductoPrecio request, int estado) {
        return productoPrecioEdicionRepository.EditarEstadoProductoPrecio(request, estado);
    }

    @Override
    public ResponseDetalleProductoPrecio DetalleProductoPrecio(RequestDetalleProductoPrecio request) {
        return productoPrecioDetalleRepository.DetalleProductoPrecio(request);
    }
}
