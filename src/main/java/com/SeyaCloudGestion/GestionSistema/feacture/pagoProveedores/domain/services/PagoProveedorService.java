package com.SeyaCloudGestion.GestionSistema.feacture.pagoProveedores.domain.services;

import com.SeyaCloudGestion.GestionSistema.feacture.pagoProveedores.application.dto.request.RequestListaPagoProveedor;
import com.SeyaCloudGestion.GestionSistema.feacture.pagoProveedores.application.dto.request.RequestRegistroPagoProveedor;
import com.SeyaCloudGestion.GestionSistema.feacture.pagoProveedores.application.dto.response.ResponseListaPagoProveedor;
import com.SeyaCloudGestion.GestionSistema.feacture.pagoProveedores.application.dto.response.ResponseRegistroPagoProveedor;
import com.SeyaCloudGestion.GestionSistema.feacture.pagoProveedores.domain.interfaces.IPagoProveedoresListado;
import com.SeyaCloudGestion.GestionSistema.feacture.pagoProveedores.domain.interfaces.IPagoProveedoresRegistro;
import com.SeyaCloudGestion.GestionSistema.feacture.pagoProveedores.infraestructure.persistence.repository.crud.PagoProveedorListadoRepository;
import com.SeyaCloudGestion.GestionSistema.feacture.pagoProveedores.infraestructure.persistence.repository.crud.PagoProveedorRegistroRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PagoProveedorService implements IPagoProveedoresListado, IPagoProveedoresRegistro {

    private final PagoProveedorListadoRepository pagoProveedorListadoRepository;
    private final PagoProveedorRegistroRepository pagoProveedorRegistroRepository;

    public PagoProveedorService(
            PagoProveedorListadoRepository pagoProveedorListadoRepository,
            PagoProveedorRegistroRepository pagoProveedorRegistroRepository
    ) {
        this.pagoProveedorListadoRepository = pagoProveedorListadoRepository;
        this.pagoProveedorRegistroRepository = pagoProveedorRegistroRepository;
    }

    @Override
    @Cacheable(value = "pagos_proveedores_lista")
    public ResponseListaPagoProveedor listaPagoProveedor(RequestListaPagoProveedor request) {
        return pagoProveedorListadoRepository.listaPagoProveedor(request);
    }

    @Override
    @CacheEvict(value = "pagos_proveedores_lista", allEntries = true)
    public ResponseRegistroPagoProveedor RegistroPagoProveedor(RequestRegistroPagoProveedor request) {
        return pagoProveedorRegistroRepository.RegistroPagoProveedor(request);
    }
}