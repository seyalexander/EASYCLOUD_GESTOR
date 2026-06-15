package com.SeyaCloudGestion.GestionSistema.feacture.serieCaja.domain.services;

import com.SeyaCloudGestion.GestionSistema.feacture.serieCaja.application.dto.request.RequestDetalleSerieCaja;
import com.SeyaCloudGestion.GestionSistema.feacture.serieCaja.application.dto.request.RequestListaSerieCaja;
import com.SeyaCloudGestion.GestionSistema.feacture.serieCaja.application.dto.response.ResponseDetalleSerieCaja;
import com.SeyaCloudGestion.GestionSistema.feacture.serieCaja.application.dto.response.ResponseListaSerieCaja;
import com.SeyaCloudGestion.GestionSistema.feacture.serieCaja.domain.interfaces.ISerieCajaDetalle;
import com.SeyaCloudGestion.GestionSistema.feacture.serieCaja.domain.interfaces.ISerieCajaListado;
import com.SeyaCloudGestion.GestionSistema.feacture.serieCaja.infraestructure.persistence.repository.crud.SerieCajaDetalleRepository;
import com.SeyaCloudGestion.GestionSistema.feacture.serieCaja.infraestructure.persistence.repository.crud.SerieCajaListadoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SerieCajaService implements ISerieCajaListado, ISerieCajaDetalle {

    private final SerieCajaListadoRepository serieCajaListadoRepository;
    private final SerieCajaDetalleRepository serieCajaDetalleRepository;

    public SerieCajaService(SerieCajaListadoRepository serieCajaListadoRepository, SerieCajaDetalleRepository serieCajaDetalleRepository) {
        this.serieCajaListadoRepository = serieCajaListadoRepository;
        this.serieCajaDetalleRepository = serieCajaDetalleRepository;
    }


    @Override
    @Cacheable(value = "cierres_caja_lista", key = "#request.idCaja")
    public ResponseListaSerieCaja listaSerieCaja(RequestListaSerieCaja request) {
        return serieCajaListadoRepository.listaSerieCaja(request);
    }

    @Override
    @Cacheable(value = "cierre_caja_detalle", key = "#request.idSerieCaja")
    public ResponseDetalleSerieCaja DetalleSerieCaja(RequestDetalleSerieCaja request) {
        return serieCajaDetalleRepository.DetalleSerieCaja(request);
    }
}