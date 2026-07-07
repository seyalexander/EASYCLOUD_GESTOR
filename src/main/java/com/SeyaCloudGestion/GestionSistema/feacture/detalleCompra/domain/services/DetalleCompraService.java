package com.SeyaCloudGestion.GestionSistema.feacture.detalleCompra.domain.services;

import com.SeyaCloudGestion.GestionSistema.feacture.detalleCompra.application.dto.request.RequestListaDetalleCompra;
import com.SeyaCloudGestion.GestionSistema.feacture.detalleCompra.application.dto.request.RequestRegistroDetalleCompra;
import com.SeyaCloudGestion.GestionSistema.feacture.detalleCompra.application.dto.response.ResponseListaDetalleCompra;
import com.SeyaCloudGestion.GestionSistema.feacture.detalleCompra.application.dto.response.ResponseRegistroDetalleCompra;
import com.SeyaCloudGestion.GestionSistema.feacture.detalleCompra.domain.interfaces.IDetalleCompraListado;
import com.SeyaCloudGestion.GestionSistema.feacture.detalleCompra.domain.interfaces.IDetalleCompraRegistro;
import com.SeyaCloudGestion.GestionSistema.feacture.detalleCompra.infrastructure.persistence.repository.crud.DetalleCompraListadoRepository;
import com.SeyaCloudGestion.GestionSistema.feacture.detalleCompra.infrastructure.persistence.repository.crud.DetalleCompraRegistroRepository;
import com.SeyaCloudGestion.GestionSistema.feacture.detalleVenta.domain.interfaces.IDetalleVentaRegistro;
import com.SeyaCloudGestion.GestionSistema.feacture.venta.application.dto.request.RequestDetalleVenta;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class DetalleCompraService implements IDetalleCompraListado, IDetalleCompraRegistro {

    private final DetalleCompraListadoRepository detalleVentaListadoRepository;
    private final DetalleCompraRegistroRepository detalleVentaRegistroRepository;

    public DetalleCompraService(
            DetalleCompraListadoRepository detalleVentaListadoRepository,
            DetalleCompraRegistroRepository detalleVentaRegistroRepository
    ) {
        this.detalleVentaListadoRepository = detalleVentaListadoRepository;
        this.detalleVentaRegistroRepository = detalleVentaRegistroRepository;
    }

    @Override
    @Cacheable(value = "venta_compra", key = "#request.idCompra")
    public ResponseListaDetalleCompra listarDetalleCompra(RequestListaDetalleCompra request) {
        return detalleVentaListadoRepository.listarDetalleCompra(request);
    }

    @Override
    @CacheEvict(value = "venta_compra", key = "#idCompra")
    public ResponseRegistroDetalleCompra registrarDetalleCompra(long idCompra,RequestRegistroDetalleCompra request, double total) {
        return detalleVentaRegistroRepository.registrarDetalleCompra( idCompra,request,total);
    }
}