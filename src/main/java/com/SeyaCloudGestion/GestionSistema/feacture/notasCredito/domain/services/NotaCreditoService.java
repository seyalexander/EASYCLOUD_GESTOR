package com.SeyaCloudGestion.GestionSistema.feacture.notasCredito.domain.services;

import com.SeyaCloudGestion.GestionSistema.feacture.notasCredito.application.dto.request.RequestRegistroNotaCredito;
import com.SeyaCloudGestion.GestionSistema.feacture.notasCredito.application.dto.response.ResponseRegistroNotaCredito;
import com.SeyaCloudGestion.GestionSistema.feacture.notasCredito.domain.interfaces.INotaCreditoRegistro;
import com.SeyaCloudGestion.GestionSistema.feacture.notasCredito.infraestructure.persistence.repository.crud.NotaCreditoRegistroRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class NotaCreditoService implements INotaCreditoRegistro {

    private final NotaCreditoRegistroRepository notaCreditoRegistroRepository;

    public NotaCreditoService(NotaCreditoRegistroRepository notaCreditoRegistroRepository) {
        this.notaCreditoRegistroRepository = notaCreditoRegistroRepository;
    }

    @Override
    @CacheEvict(value = {"cuentas_lista", "ventas_reportes", "ventas_detalle"}, allEntries = true)
    public ResponseRegistroNotaCredito RegistroNotaCredito(RequestRegistroNotaCredito request) {
        return notaCreditoRegistroRepository.RegistroNotaCredito(request);
    }
}
