package com.SeyaCloudGestion.GestionSistema.feacture.transferenciaDetalles.domain.services;
import com.SeyaCloudGestion.GestionSistema.feacture.transferenciaDetalles.application.dto.request.RequestListaDetalleTransferencia;
import com.SeyaCloudGestion.GestionSistema.feacture.transferenciaDetalles.application.dto.request.RequestRegistroDetalleTransferencia;
import com.SeyaCloudGestion.GestionSistema.feacture.transferenciaDetalles.application.dto.response.ResponseListaTransferenciaDetalle;
import com.SeyaCloudGestion.GestionSistema.feacture.transferenciaDetalles.application.dto.response.ResponseRegistroTransferenciaDetalle;
import com.SeyaCloudGestion.GestionSistema.feacture.transferenciaDetalles.domain.interfaces.ITransferenciaDetalleListado;
import com.SeyaCloudGestion.GestionSistema.feacture.transferenciaDetalles.domain.interfaces.ITransferenciaDetalleRegistro;
import com.SeyaCloudGestion.GestionSistema.feacture.transferenciaDetalles.infraestructure.persistence.repository.crud.ListadoDetalleTransferenciaRepository;
import com.SeyaCloudGestion.GestionSistema.feacture.transferenciaDetalles.infraestructure.persistence.repository.crud.RegistroDetalleTransferenciaRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class TransferenciaDetalleService implements ITransferenciaDetalleListado, ITransferenciaDetalleRegistro {

    private final ListadoDetalleTransferenciaRepository listadoRepository;
    private final RegistroDetalleTransferenciaRepository registroRepository;

    public TransferenciaDetalleService(
            ListadoDetalleTransferenciaRepository listadoRepository, RegistroDetalleTransferenciaRepository registroRepository) {
        this.listadoRepository = listadoRepository;
        this.registroRepository = registroRepository;
    }

    @Override
    @Cacheable(value = "transferencia_detalles_lista", key = "#request.idTransferencia")
    public ResponseListaTransferenciaDetalle listaDetalleTransferencia(RequestListaDetalleTransferencia request) {
        return listadoRepository.listaDetalleTransferencia(request);
    }

    @Override
    @CacheEvict(value = {"transferencia_detalles_lista"}, allEntries = true)
    public ResponseRegistroTransferenciaDetalle RegistroDetalleTransferencia(long idTransferencia, RequestRegistroDetalleTransferencia request,double costoUnitario) {
        return registroRepository.RegistroDetalleTransferencia(idTransferencia, request,costoUnitario);
    }
}