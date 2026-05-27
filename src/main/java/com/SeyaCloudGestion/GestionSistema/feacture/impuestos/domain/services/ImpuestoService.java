package com.SeyaCloudGestion.GestionSistema.feacture.impuestos.domain.services;

import com.SeyaCloudGestion.GestionSistema.feacture.impuestos.application.dto.request.*;
import com.SeyaCloudGestion.GestionSistema.feacture.impuestos.application.dto.response.*;
import com.SeyaCloudGestion.GestionSistema.feacture.impuestos.domain.interfaces.*;
import com.SeyaCloudGestion.GestionSistema.feacture.impuestos.infraestructure.persistence.repository.crud.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ImpuestoService  implements IImpuestoEdicion, IImpuestoLista, IImpuestoRegistro,IImpuestoDetalle {

    private final ImpuestoDetalleRepository impuestoDetalleRepository;
    private final ImpuestoEdicionRepository impuestoEdicionRepository;
    private final ImpuestoListadoRepository impuestoListaRepository;
    private final ImpuestoRegistroRepository impuestoRegistroRepository;

    public ImpuestoService(ImpuestoDetalleRepository impuestoDetalleRepository, ImpuestoEdicionRepository impuestoEdicionRepository, ImpuestoListadoRepository impuestoListaRepository, ImpuestoRegistroRepository impuestoRegistroRepository) {
        this.impuestoDetalleRepository = impuestoDetalleRepository;
        this.impuestoEdicionRepository = impuestoEdicionRepository;
        this.impuestoListaRepository = impuestoListaRepository;
        this.impuestoRegistroRepository = impuestoRegistroRepository;
    }

    @Override
    @CacheEvict(value = {"impuestos","#request.estado"},allEntries = true)
    public ResponseEditarAllImpuesto EditarAllImpuesto(RequestEditarAllImpuesto request) {
        return impuestoEdicionRepository.EditarAllImpuesto(request);
    }

    @Override
    @CacheEvict(value = {"impuestos",  "impuesto_detalle"} ,allEntries = true)
    public ResponseEditarEstadoImpuesto EditarEstadoImpuesto(RequestEditarEstadoImpuesto request, int estado) {
        return impuestoEdicionRepository.EditarEstadoImpuesto(request, estado);
    }

    @Override
    @Cacheable(value = "impuestos", key = "#request.estado")
    public ResponseListaImpuesto listaImpuesto(RequestListaImpuesto request) {
        return impuestoListaRepository.listaImpuesto(request);
    }

    @Override
    @CacheEvict(value = {"impuestos", "impuesto_detalle"}, allEntries = true)
    public ResponseRegistroImpuesto RegistroImpuesto(RequestRegistroImpuesto request) {
        return impuestoRegistroRepository.RegistroImpuesto(request);
    }

    @Override
    @Cacheable(value = "impuesto_detalle", key = "#request.idImpuesto")
    public ResponseDetalleImpuesto DetalleImpuesto(RequestDetalleImpuesto request) {
        return impuestoDetalleRepository.DetalleImpuesto(request);
    }
}
