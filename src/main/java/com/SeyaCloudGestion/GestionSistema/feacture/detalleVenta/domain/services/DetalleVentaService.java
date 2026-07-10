package com.SeyaCloudGestion.GestionSistema.feacture.detalleVenta.domain.services;

import com.SeyaCloudGestion.GestionSistema.feacture.detalleVenta.application.dto.request.RequestRegistroDetalleVenta;
import com.SeyaCloudGestion.GestionSistema.feacture.detalleVenta.application.dto.response.ResponseListaDetalleVenta;
import com.SeyaCloudGestion.GestionSistema.feacture.detalleVenta.application.dto.response.ResponseRegistroDetalleVenta;
import com.SeyaCloudGestion.GestionSistema.feacture.detalleVenta.domain.interfaces.IDetalleVentaListado;
import com.SeyaCloudGestion.GestionSistema.feacture.detalleVenta.domain.interfaces.IDetalleVentaRegistro;
import com.SeyaCloudGestion.GestionSistema.feacture.detalleVenta.infrastructure.persistence.repository.crud.DetalleVentaListadoRepository;
import com.SeyaCloudGestion.GestionSistema.feacture.detalleVenta.infrastructure.persistence.repository.crud.DetalleVentaRegistroRepository;
import com.SeyaCloudGestion.GestionSistema.feacture.venta.application.dto.request.RequestDetalleVenta;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class DetalleVentaService implements IDetalleVentaListado, IDetalleVentaRegistro {

    private final DetalleVentaListadoRepository detalleVentaListadoRepository;
    private final DetalleVentaRegistroRepository detalleVentaRegistroRepository;

    public DetalleVentaService(
            DetalleVentaListadoRepository detalleVentaListadoRepository,
            DetalleVentaRegistroRepository detalleVentaRegistroRepository
    ) {
        this.detalleVentaListadoRepository = detalleVentaListadoRepository;
        this.detalleVentaRegistroRepository = detalleVentaRegistroRepository;
    }

    @Override
    @Cacheable(value = "venta_detalle", key = "#request.idVenta")
    public ResponseListaDetalleVenta listarDetalleVenta(RequestDetalleVenta request) {
        return detalleVentaListadoRepository.listarDetalleVenta(request);
    }

    @Override
    @CacheEvict(value = "venta_detalle", key = "#idVenta")
    public ResponseRegistroDetalleVenta registrarDetalleVenta(long idVenta,RequestRegistroDetalleVenta request, double total,double costoUnitario) {
        return detalleVentaRegistroRepository.registrarDetalleVenta(idVenta,request,total, costoUnitario);
    }
}