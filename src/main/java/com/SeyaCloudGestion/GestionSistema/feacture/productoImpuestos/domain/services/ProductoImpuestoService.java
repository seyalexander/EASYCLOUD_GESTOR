package com.SeyaCloudGestion.GestionSistema.feacture.productoImpuestos.domain.services;

import com.SeyaCloudGestion.GestionSistema.feacture.productoImpuestos.application.dto.request.*;
import com.SeyaCloudGestion.GestionSistema.feacture.productoImpuestos.application.dto.response.*;
import com.SeyaCloudGestion.GestionSistema.feacture.productoImpuestos.domain.interfaces.*;
import com.SeyaCloudGestion.GestionSistema.feacture.productoImpuestos.infraestructure.persistence.repository.crud.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ProductoImpuestoService  implements IProductoImpuestoRegistro, IProductoImpuestoListado, IProductoImpuestoEdicion,IProductoImpuestoDetalle {
    private final ProductoImpuestoEdicionRepository productoImpuestoEdicionRepository;
    private final ProductoImpuestoListadoRepository productoImpuestoListadoRepository;
    private final ProductoImpuestoRegistroRepository productoImpuestoRegistroRepository;
    private final ProductoImpuestoDetalleRepository productoImpuestoDetalleRepository;

    public ProductoImpuestoService(ProductoImpuestoEdicionRepository productoImpuestoEdicionRepository, ProductoImpuestoListadoRepository productoImpuestoListadoRepository, ProductoImpuestoRegistroRepository productoImpuestoRegistroRepository, ProductoImpuestoDetalleRepository productoImpuestoDetalleRepository) {
        this.productoImpuestoEdicionRepository = productoImpuestoEdicionRepository;
        this.productoImpuestoListadoRepository = productoImpuestoListadoRepository;
        this.productoImpuestoRegistroRepository = productoImpuestoRegistroRepository;
        this.productoImpuestoDetalleRepository = productoImpuestoDetalleRepository;
    }

    @Override
    @Cacheable(value = "productoImpuestos_lista", key = "#request.estado")
    public ResponseListaProductoImpuesto ListaProductoImpuesto(RequestListaProductoImpuesto request) {
        return productoImpuestoListadoRepository.ListaProductoImpuesto(request);
    }

    @Override
    @CacheEvict(value = {"productoImpuestos_lista"}, allEntries = true)
    public ResponseRegistroProductoImpuesto RegistroProductoImpuesto(RequestRegistroProductoImpuesto request) {
        return productoImpuestoRegistroRepository.RegistroProductoImpuesto(request);
    }

    @Override
    @CacheEvict(value = {"productoImpuestos_lista"}, allEntries = true)
    public ResponseEditarAllProductoImpuesto EditarAllProductoImpuesto(RequestEditarAllProductoImpuesto request) {
        return productoImpuestoEdicionRepository.EditarAllProductoImpuesto(request);
    }

    @Override
    @CacheEvict(value = {"productoImpuestos_lista"}, allEntries = true)
    public ResponseEditarEstadoProductoImpuesto EditarEstadoProductoImpuesto(RequestEditarEstadoProductoImpuesto request, int estado) {
        return productoImpuestoEdicionRepository.EditarEstadoProductoImpuesto(request, estado);
    }

    @Override
    public ResponseDetalleProductoImpuesto DetalleProductoImpuesto(RequestDetalleProductoImpuesto request) {
        return productoImpuestoDetalleRepository.DetalleProductoImpuesto(request);
    }
}
