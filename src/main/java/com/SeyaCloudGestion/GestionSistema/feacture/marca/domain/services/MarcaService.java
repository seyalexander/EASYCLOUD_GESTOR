package com.SeyaCloudGestion.GestionSistema.feacture.marca.domain.services;

import com.SeyaCloudGestion.GestionSistema.feacture.marca.application.dto.request.RequestDetalleMarca;
import com.SeyaCloudGestion.GestionSistema.feacture.marca.application.dto.request.RequestListaMarca;
import com.SeyaCloudGestion.GestionSistema.feacture.marca.application.dto.response.ResponseDetalleMarca;
import com.SeyaCloudGestion.GestionSistema.feacture.marca.application.dto.response.ResponseListaMarca;
import com.SeyaCloudGestion.GestionSistema.feacture.marca.domain.interfaces.IMarcaDetalle;
import com.SeyaCloudGestion.GestionSistema.feacture.marca.domain.interfaces.IMarcaListado;
import com.SeyaCloudGestion.GestionSistema.feacture.marca.infraestructure.persistence.repository.crud.MarcaDetalleRepository;
import com.SeyaCloudGestion.GestionSistema.feacture.marca.infraestructure.persistence.repository.crud.MarcaListaRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MarcaService implements IMarcaListado, IMarcaDetalle {

    private final MarcaListaRepository marcaListaRepository;
    private final MarcaDetalleRepository marcaDetalleRepository;

    public MarcaService(
            MarcaListaRepository marcaListaRepository,
            MarcaDetalleRepository marcaDetalleRepository
    ) {
        this.marcaListaRepository = marcaListaRepository;
        this.marcaDetalleRepository = marcaDetalleRepository;
    }

    @Override
    @Cacheable(value = "marca_lista", key = "#request.estado")
    public ResponseListaMarca ListaMarca(RequestListaMarca request) {
        return marcaListaRepository.ListaMarca(request);
    }

    @Override
    @Cacheable(value = "marca_detalle", key = "#request.idMarca")
    public ResponseDetalleMarca DetalleMarca(RequestDetalleMarca marca) {
        return marcaDetalleRepository.DetalleMarca(marca);
    }
}
