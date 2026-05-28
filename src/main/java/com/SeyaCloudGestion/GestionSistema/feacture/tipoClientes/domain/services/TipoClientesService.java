package com.SeyaCloudGestion.GestionSistema.feacture.tipoClientes.domain.services;

import com.SeyaCloudGestion.GestionSistema.feacture.tipoClientes.application.dto.request.*;
import com.SeyaCloudGestion.GestionSistema.feacture.tipoClientes.application.dto.response.*;
import com.SeyaCloudGestion.GestionSistema.feacture.tipoClientes.domain.interfaces.*;
import com.SeyaCloudGestion.GestionSistema.feacture.tipoClientes.infraestructure.persistence.repository.crud.*;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

@Component
public class TipoClientesService  implements ITipoClientesDetalle, ITipoClientesEdicion, ITipoClientesListado, ITipoClientesRegistro {
    private final TipoClientesDetalleRepository tipoClientesDetalleRepository;
    private final TipoClientesEdicionRepository tipoClientesEdicionRepository;
    private final TipoClientesListadoRepository tipoClientesListadoRepository;
    private final TipoClientesRegistroRepository tipoClientesRegistroRepository;

    public TipoClientesService(
            TipoClientesDetalleRepository tipoClientesDetalleRepository,
            TipoClientesEdicionRepository tipoClientesEdicionRepository,
            TipoClientesListadoRepository tipoClientesListadoRepository,
            TipoClientesRegistroRepository tipoClientesRegistroRepository
    ) {
        this.tipoClientesDetalleRepository = tipoClientesDetalleRepository;
        this.tipoClientesEdicionRepository = tipoClientesEdicionRepository;
        this.tipoClientesListadoRepository = tipoClientesListadoRepository;
        this.tipoClientesRegistroRepository = tipoClientesRegistroRepository;
    }

    @Override
    @Cacheable(value = "tipoClientes", key = "#request.estado")
    public ResponseListaTipoClientes ListaTipoClientes(RequestListaTipoClientes request) {
        return tipoClientesListadoRepository.ListaTipoClientes(request);
    }

    @Override
    @CacheEvict(value = {"tipoClientes", "tipoClientes_detalle"}, allEntries = true)
    public ResponseRegistroTipoClientes RegistroTipoClientes(RequestRegistroTipoClientes request) {
        return tipoClientesRegistroRepository.RegistroTipoClientes(request);
    }
    @Override
    @CacheEvict(value = {"tipoClientes", "tipoClientes_detalle"}, allEntries = true)
    public ResponseEditarAllTipoClientes EditarAllTipoClientes(RequestEditarAllTipoClientes request) {
        return tipoClientesEdicionRepository.EditarAllTipoClientes(request);
    }
    @Override
    @CacheEvict(value = {"tipoClientes", "tipoClientes_detalle"}, allEntries = true)
    public ResponseEditarEstadoTipoClientes EditarEstadoTipoClientes(RequestEditarEstadoTipoClientes request, int estado) {
        return tipoClientesEdicionRepository.EditarEstadoTipoClientes(request, estado);
    }
    @Override
    @Cacheable(value = "tipoClientes", key = "#request.idTipoCliente")
    public ResponseDetalleTipoClientes DetalleTipoClientes(RequestDetalleTipoClientes request) {
        return tipoClientesDetalleRepository.DetalleTipoClientes(request);
    }
}