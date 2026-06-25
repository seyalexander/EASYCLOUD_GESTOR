package com.SeyaCloudGestion.GestionSistema.feacture.pagos.domain.services;

import com.SeyaCloudGestion.GestionSistema.feacture.pagos.application.dto.request.RequestListaPago;
import com.SeyaCloudGestion.GestionSistema.feacture.pagos.application.dto.request.RequestRegistroPago;
import com.SeyaCloudGestion.GestionSistema.feacture.pagos.application.dto.response.ResponseListaPago;
import com.SeyaCloudGestion.GestionSistema.feacture.pagos.application.dto.response.ResponseRegistroPago;
import com.SeyaCloudGestion.GestionSistema.feacture.pagos.domain.interfaces.IPagoListado;
import com.SeyaCloudGestion.GestionSistema.feacture.pagos.domain.interfaces.IPagoRegistro;
import com.SeyaCloudGestion.GestionSistema.feacture.pagos.infraestructure.persistence.repository.crud.PagoListadoRepository;
import com.SeyaCloudGestion.GestionSistema.feacture.pagos.infraestructure.persistence.repository.crud.PagoRegistroRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PagoService implements IPagoRegistro,IPagoListado {

    private final PagoListadoRepository pagoListadoRepository;
    private final PagoRegistroRepository pagoRegistroRepository;

    public PagoService(
            PagoListadoRepository pagoListadoRepository,
            PagoRegistroRepository pagoRegistroRepository
    ) {
        this.pagoListadoRepository = pagoListadoRepository;
        this.pagoRegistroRepository = pagoRegistroRepository;
    }
    @Override
    public ResponseListaPago listaPago(RequestListaPago request) {
        return pagoListadoRepository.listaPago(request);
    }

    @Override
    public ResponseRegistroPago RegistroPago(RequestRegistroPago request) {
        return pagoRegistroRepository.RegistroPago(request);
    }
}
