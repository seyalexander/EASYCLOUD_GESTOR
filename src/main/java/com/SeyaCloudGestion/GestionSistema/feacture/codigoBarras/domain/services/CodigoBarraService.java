package com.SeyaCloudGestion.GestionSistema.feacture.codigoBarras.domain.services;

import com.SeyaCloudGestion.GestionSistema.feacture.codigoBarras.application.dto.request.RequestEditarAllCodigoBarra;
import com.SeyaCloudGestion.GestionSistema.feacture.codigoBarras.application.dto.request.RequestRegistroCodigoBarra;
import com.SeyaCloudGestion.GestionSistema.feacture.codigoBarras.application.dto.response.ResponseEditarAllCodigoBarra;
import com.SeyaCloudGestion.GestionSistema.feacture.codigoBarras.application.dto.response.ResponseListaCodigoBarra;
import com.SeyaCloudGestion.GestionSistema.feacture.codigoBarras.application.dto.response.ResponseRegistroCodigoBarra;
import com.SeyaCloudGestion.GestionSistema.feacture.codigoBarras.domain.interfaces.ICodigoBarraEdicion;
import com.SeyaCloudGestion.GestionSistema.feacture.codigoBarras.domain.interfaces.ICodigoBarraListado;
import com.SeyaCloudGestion.GestionSistema.feacture.codigoBarras.domain.interfaces.ICodigoBarraRegistro;
import com.SeyaCloudGestion.GestionSistema.feacture.codigoBarras.infraestructure.persistence.repository.crud.CodigoBarraEdicionAllRepository;
import com.SeyaCloudGestion.GestionSistema.feacture.codigoBarras.infraestructure.persistence.repository.crud.CodigoBarraListadoRepository;
import com.SeyaCloudGestion.GestionSistema.feacture.codigoBarras.infraestructure.persistence.repository.crud.CodigoBarraRegistroRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CodigoBarraService implements ICodigoBarraRegistro, ICodigoBarraListado, ICodigoBarraEdicion {
    private final CodigoBarraRegistroRepository codigoBarraRegistroRepository;
    private final CodigoBarraListadoRepository codigoBarraListadoRepository;
    private final CodigoBarraEdicionAllRepository  codigoBarraEdicionAllRepository;

    public CodigoBarraService(CodigoBarraRegistroRepository codigoBarraRegistroRepository, CodigoBarraListadoRepository codigoBarraListadoRepository, CodigoBarraEdicionAllRepository codigoBarraEdicionAllRepository) {
        this.codigoBarraRegistroRepository = codigoBarraRegistroRepository;
        this.codigoBarraListadoRepository = codigoBarraListadoRepository;
        this.codigoBarraEdicionAllRepository = codigoBarraEdicionAllRepository;
    }

    @Override
    @Cacheable(value = "codigoBarras_lista", key = "#request.idArticulo")
    public ResponseListaCodigoBarra ListaCodigoBarra() {
        return codigoBarraListadoRepository.ListaCodigoBarra();
    }

    @Override
    @CacheEvict(value = {"codigoBarras_lista"}, allEntries = true)
    public ResponseRegistroCodigoBarra RegistroCodigoBarra(RequestRegistroCodigoBarra request) {
        return codigoBarraRegistroRepository.RegistroCodigoBarra(request);
    }

    @Override
    @CacheEvict(value = {"codigoBarras_lista"}, allEntries = true)
    public ResponseEditarAllCodigoBarra EditarAllCodigoBarra(RequestEditarAllCodigoBarra request) {
        return codigoBarraEdicionAllRepository.EditarAllCodigoBarra(request);
    }
}
