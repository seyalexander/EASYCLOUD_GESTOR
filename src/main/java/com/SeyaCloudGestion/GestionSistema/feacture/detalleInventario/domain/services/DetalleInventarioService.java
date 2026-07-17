package com.SeyaCloudGestion.GestionSistema.feacture.detalleInventario.domain.services;

import com.SeyaCloudGestion.GestionSistema.feacture.detalleCompra.domain.interfaces.IDetalleCompraRegistro;
import com.SeyaCloudGestion.GestionSistema.feacture.detalleInventario.application.dto.request.RequestEditarDetalleInventario;
import com.SeyaCloudGestion.GestionSistema.feacture.detalleInventario.application.dto.request.RequestListaDetalleInventario;
import com.SeyaCloudGestion.GestionSistema.feacture.detalleInventario.application.dto.request.RequestRegistroDetalleInventario;
import com.SeyaCloudGestion.GestionSistema.feacture.detalleInventario.application.dto.response.ResponseEditarDetalleInventario;
import com.SeyaCloudGestion.GestionSistema.feacture.detalleInventario.application.dto.response.ResponseListaDetalleInventario;
import com.SeyaCloudGestion.GestionSistema.feacture.detalleInventario.application.dto.response.ResponseRegistroDetalleInventario;
import com.SeyaCloudGestion.GestionSistema.feacture.detalleInventario.domain.interfaces.IDetalleInventarioEditar;
import com.SeyaCloudGestion.GestionSistema.feacture.detalleInventario.domain.interfaces.IDetalleInventarioListado;
import com.SeyaCloudGestion.GestionSistema.feacture.detalleInventario.domain.interfaces.IDetalleInventarioRegistro;
import com.SeyaCloudGestion.GestionSistema.feacture.detalleInventario.infrastructure.persistence.repository.crud.DetalleInventarioEdicionRepository;
import com.SeyaCloudGestion.GestionSistema.feacture.detalleInventario.infrastructure.persistence.repository.crud.DetalleInventarioListadoRepository;
import com.SeyaCloudGestion.GestionSistema.feacture.detalleInventario.infrastructure.persistence.repository.crud.DetalleInventarioRegistroRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class DetalleInventarioService implements IDetalleInventarioListado, IDetalleInventarioRegistro, IDetalleInventarioEditar {

    private final DetalleInventarioListadoRepository detalleVentaListadoRepository;
    private final DetalleInventarioRegistroRepository detalleVentaRegistroRepository;
    private final DetalleInventarioEdicionRepository detalleInventarioEdicionRepository;

    public DetalleInventarioService(
            DetalleInventarioListadoRepository detalleVentaListadoRepository,
            DetalleInventarioRegistroRepository detalleVentaRegistroRepository, DetalleInventarioEdicionRepository detalleInventarioEdicionRepository
    ) {
        this.detalleVentaListadoRepository = detalleVentaListadoRepository;
        this.detalleVentaRegistroRepository = detalleVentaRegistroRepository;
        this.detalleInventarioEdicionRepository = detalleInventarioEdicionRepository;
    }

    @Override
    @Cacheable(value = "venta_compra", key = "#request.idCompra")
    public ResponseListaDetalleInventario listarDetalleInventario(RequestListaDetalleInventario request) {
        return detalleVentaListadoRepository.listarDetalleInventario(request);
    }

    @Override
    @CacheEvict(value = "venta_compra", key = "#idCompra")
    public ResponseRegistroDetalleInventario registrarDetalleIventario(long idInventarioCabezera, RequestRegistroDetalleInventario request, double stockSistema ) {
        return detalleVentaRegistroRepository.registrarDetalleIventario( idInventarioCabezera,request,stockSistema);
    }

    @Override
    public ResponseEditarDetalleInventario editarDetalleIventario(long idInventarioCabezera, RequestEditarDetalleInventario request) {
        return detalleInventarioEdicionRepository.editarDetalleIventario(idInventarioCabezera, request);
    }
}