package com.SeyaCloudGestion.GestionSistema.feacture.venta.domain.services;

import com.SeyaCloudGestion.GestionSistema.feacture.venta.application.dto.request.*;
import com.SeyaCloudGestion.GestionSistema.feacture.venta.application.dto.response.*;
import com.SeyaCloudGestion.GestionSistema.feacture.venta.domain.interfaces.*;
import com.SeyaCloudGestion.GestionSistema.feacture.venta.infraestructure.persistence.repository.crud.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class VentaService implements IVentaListado, IVentaRegistro, IVentaEdicion, IVentaDetalle {

    private final VentaListadoRepository ventaListadoRepository;
    private final VentaRegistroRepository ventaRegistroRepository;
    private final VentaEdicionRepository ventaEdicionRepository;
    private final VentaDetalleRepository ventaDetalleRepository;

    public VentaService(
            VentaListadoRepository ventaListadoRepository,
            VentaRegistroRepository ventaRegistroRepository,
            VentaEdicionRepository ventaEdicionRepository,
            VentaDetalleRepository ventaDetalleRepository
    ) {
        this.ventaListadoRepository = ventaListadoRepository;
        this.ventaRegistroRepository = ventaRegistroRepository;
        this.ventaEdicionRepository = ventaEdicionRepository;
        this.ventaDetalleRepository = ventaDetalleRepository;
    }

    @Override
    @Cacheable(value = "ventas_lista", key = "#request.estado")
    public ResponseListaVenta listaVenta(RequestListaVenta request) {
        return ventaListadoRepository.listaVenta(request);
    }

    @Override
    @CacheEvict(value = {"ventas_lista", "venta_detalle"}, allEntries = true)
    public ResponseRegistroVenta RegistroVenta(long idCaja,RequestRegistroVenta request, double subTotal, double impuesto, double total) {
        return ventaRegistroRepository.RegistroVenta(idCaja, request, subTotal, impuesto, total);
    }

    @Override
    @CacheEvict(value = {"ventas_lista", "venta_detalle"}, allEntries = true)
    public ResponseEditarEstadoVenta EditarEstadoVenta(RequestEditarEstadoVenta request, int estado) {
        return ventaEdicionRepository.EditarEstadoVenta(request, estado);
    }

    @Override
    @Cacheable(value = "venta_detalle", key = "#request.idVenta")
    public ResponseDetalleVenta DetalleVenta(RequestDetalleVenta request) {
        return ventaDetalleRepository.DetalleVenta(request);
    }
}