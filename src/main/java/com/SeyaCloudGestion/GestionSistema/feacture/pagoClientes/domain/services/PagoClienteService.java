package com.SeyaCloudGestion.GestionSistema.feacture.pagoClientes.domain.services;

import com.SeyaCloudGestion.GestionSistema.feacture.pagoClientes.application.dto.request.RequestRegistroDetallePagoCliente;
import com.SeyaCloudGestion.GestionSistema.feacture.pagoClientes.application.dto.response.ResponseRegistroDetallePagoCliente;
import com.SeyaCloudGestion.GestionSistema.feacture.pagoClientes.application.dto.response.ResponseRegistroPagoCliente;
import com.SeyaCloudGestion.GestionSistema.feacture.pagoClientes.domain.interfaces.IPagoClienteRegistro;
import com.SeyaCloudGestion.GestionSistema.feacture.pagoClientes.infraestructure.persistence.repository.crud.PagoClienteRegistroRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PagoClienteService implements IPagoClienteRegistro {

    private final PagoClienteRegistroRepository pagoClienteRegistroRepository;

    public PagoClienteService(PagoClienteRegistroRepository pagoClienteRegistroRepository) {
        this.pagoClienteRegistroRepository = pagoClienteRegistroRepository;
    }

    @Override
    @CacheEvict(value = {"pagos_clientes_lista", "cuenta_cobrar_detalle"}, allEntries = true)
    public ResponseRegistroDetallePagoCliente RegistroDetallePagoCliente(long idCuentaPorCobrar , RequestRegistroDetallePagoCliente request) {
        return pagoClienteRegistroRepository.RegistroDetallePagoCliente(idCuentaPorCobrar, request);
    }
}