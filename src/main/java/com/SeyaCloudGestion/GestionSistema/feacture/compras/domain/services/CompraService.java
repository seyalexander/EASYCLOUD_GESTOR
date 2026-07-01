package com.SeyaCloudGestion.GestionSistema.feacture.compras.domain.services;

import com.SeyaCloudGestion.GestionSistema.feacture.compras.application.dto.request.*;
import com.SeyaCloudGestion.GestionSistema.feacture.compras.application.dto.response.*;
import com.SeyaCloudGestion.GestionSistema.feacture.compras.domain.interfaces.*;
import com.SeyaCloudGestion.GestionSistema.feacture.compras.infraestructure.persistence.repository.crud.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CompraService implements ICompraListado, ICompraRegistro, ICompraEdicion, ICompraDetalle {

    private final CompraListadoRepository compraListadoRepository;
    private final CompraRegistroRepository compraRegistroRepository;
    private final CompraEdicionRepository compraEdicionRepository;
    private final CompraDetalleRepository compraDetalleRepository;

    public CompraService(
            CompraListadoRepository compraListadoRepository,
            CompraRegistroRepository compraRegistroRepository,
            CompraEdicionRepository compraEdicionRepository,
            CompraDetalleRepository compraDetalleRepository
    ) {
        this.compraListadoRepository = compraListadoRepository;
        this.compraRegistroRepository = compraRegistroRepository;
        this.compraEdicionRepository = compraEdicionRepository;
        this.compraDetalleRepository = compraDetalleRepository;
    }

    @Override
    @Cacheable(value = "compras_lista", key = "#request.estado")
    public ResponseListaCompra listaCompra(RequestListaCompra request) {
        return compraListadoRepository.listaCompra(request);
    }

    @Override
    @CacheEvict(value = {"compras_lista", "compra_detalle"}, allEntries = true)
    public ResponseRegistroCompra RegistroCompra(RequestRegistroCompra request,double subTotal, double igv, double total) {
        return compraRegistroRepository.RegistroCompra(request, subTotal,  igv,  total);
    }

    @Override
    @CacheEvict(value = {"compras_lista", "compra_detalle"}, allEntries = true)
    public ResponseEditarAllCompra EditarAllCompra(RequestEditarAllCompra request) {
        return compraEdicionRepository.EditarAllCompra(request);
    }

    @Override
    @Cacheable(value = "compra_detalle", key = "#request.idCompra")
    public ResponseDetalleCompra DetalleCompra(RequestDetalleCompra request) {
        return compraDetalleRepository.DetalleCompra(request);
    }

    @Override
    @CacheEvict(value = {"compras_lista", "compra_detalle"}, allEntries = true)
    public ResponseAnularCompra AnularCompra(RequestAnularCompra request,int estado) {
        return compraEdicionRepository.AnularCompra(request,estado);
    }
}