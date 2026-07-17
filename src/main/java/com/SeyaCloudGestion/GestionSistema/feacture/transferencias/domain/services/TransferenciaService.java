package com.SeyaCloudGestion.GestionSistema.feacture.transferencias.domain.services;

import com.SeyaCloudGestion.GestionSistema.feacture.transferencias.application.dto.request.RequestAceptrarTransferencia;
import com.SeyaCloudGestion.GestionSistema.feacture.transferencias.application.dto.request.RequestDetalleTransferencia;
import com.SeyaCloudGestion.GestionSistema.feacture.transferencias.application.dto.request.RequestListaTransferencia;
import com.SeyaCloudGestion.GestionSistema.feacture.transferencias.application.dto.request.RequestRegistroTransferencia;
import com.SeyaCloudGestion.GestionSistema.feacture.transferencias.application.dto.response.ResponseAceptarTransferencia;
import com.SeyaCloudGestion.GestionSistema.feacture.transferencias.application.dto.response.ResponseDetalleTransferencia;
import com.SeyaCloudGestion.GestionSistema.feacture.transferencias.application.dto.response.ResponseListaTransferencia;
import com.SeyaCloudGestion.GestionSistema.feacture.transferencias.application.dto.response.ResponseRegistroTransferencia;
import com.SeyaCloudGestion.GestionSistema.feacture.transferencias.domain.interfaces.ITransferenciaDetalle;
import com.SeyaCloudGestion.GestionSistema.feacture.transferencias.domain.interfaces.ITransferenciaEdicion;
import com.SeyaCloudGestion.GestionSistema.feacture.transferencias.domain.interfaces.ITransferenciaListado;
import com.SeyaCloudGestion.GestionSistema.feacture.transferencias.domain.interfaces.ITransferenciaRegistro;
import com.SeyaCloudGestion.GestionSistema.feacture.transferencias.infraestructure.persistence.repository.crud.TransferenciaDetalleRepository;
import com.SeyaCloudGestion.GestionSistema.feacture.transferencias.infraestructure.persistence.repository.crud.TransferenciaEdicionRepository;
import com.SeyaCloudGestion.GestionSistema.feacture.transferencias.infraestructure.persistence.repository.crud.TransferenciaListadoRepository;
import com.SeyaCloudGestion.GestionSistema.feacture.transferencias.infraestructure.persistence.repository.crud.TransferenciaRegistroRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class TransferenciaService implements ITransferenciaListado, ITransferenciaRegistro, ITransferenciaEdicion, ITransferenciaDetalle {

    private final TransferenciaListadoRepository transferenciaListadoRepository;
    private final TransferenciaRegistroRepository transferenciaRegistroRepository;
    private final TransferenciaEdicionRepository transferenciaEdicionRepository;
    private final TransferenciaDetalleRepository transferenciaDetalleRepository;

    public TransferenciaService(
            TransferenciaListadoRepository transferenciaListadoRepository,
            TransferenciaRegistroRepository transferenciaRegistroRepository,
            TransferenciaEdicionRepository transferenciaEdicionRepository,
            TransferenciaDetalleRepository transferenciaDetalleRepository
    ) {
        this.transferenciaListadoRepository = transferenciaListadoRepository;
        this.transferenciaRegistroRepository = transferenciaRegistroRepository;
        this.transferenciaEdicionRepository = transferenciaEdicionRepository;
        this.transferenciaDetalleRepository = transferenciaDetalleRepository;
    }

    @Override
    @Cacheable(value = "transferencias_lista", key = "#request.idAlmacenOrigen + '_' + #request.idAlmacenDestino")
    public ResponseListaTransferencia listaTransferencia(RequestListaTransferencia request) {
        return transferenciaListadoRepository.listaTransferencia(request);
    }

    @Override
    @CacheEvict(value = {"transferencias_lista", "transferencia_detalle"}, allEntries = true)
    public ResponseRegistroTransferencia RegistroTransferencia(RequestRegistroTransferencia request) {
        return transferenciaRegistroRepository.RegistroTransferencia(request);
    }

    @Override
    @CacheEvict(value = {"transferencias_lista", "transferencia_detalle"}, allEntries = true)
    public ResponseAceptarTransferencia EditarEstadoTransferencia(RequestAceptrarTransferencia request) {
        return transferenciaEdicionRepository.EditarEstadoTransferencia(request);
    }

    @Override
    @Cacheable(value = "transferencia_detalle", key = "#request.idTransferencia")
    public ResponseDetalleTransferencia DetalleTransferencia(RequestDetalleTransferencia request) {
        return transferenciaDetalleRepository.DetalleTransferencia(request);
    }
}