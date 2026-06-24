package com.SeyaCloudGestion.GestionSistema.feacture.devoluciones.domain.services;

import com.SeyaCloudGestion.GestionSistema.feacture.devoluciones.application.dto.request.RequestRegistroDevolucion;
import com.SeyaCloudGestion.GestionSistema.feacture.devoluciones.application.dto.response.ResponseRegistroDevolucion;
import com.SeyaCloudGestion.GestionSistema.feacture.devoluciones.domain.interfaces.IDevolucionRegistro;
import com.SeyaCloudGestion.GestionSistema.feacture.devoluciones.infraestructure.persistence.repository.crud.DevolucionRegistroRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class DevolucionService implements IDevolucionRegistro {

    private final DevolucionRegistroRepository devolucionRegistroRepository;

    public DevolucionService(DevolucionRegistroRepository devolucionRegistroRepository) {
        this.devolucionRegistroRepository = devolucionRegistroRepository;
    }

    @Override
    @CacheEvict(value = {"productos_stock", "cuentas_lista"}, allEntries = true)
    public ResponseRegistroDevolucion RegistroDevolucion(RequestRegistroDevolucion request) {
        return devolucionRegistroRepository.RegistroDevolucion(request);
    }
}