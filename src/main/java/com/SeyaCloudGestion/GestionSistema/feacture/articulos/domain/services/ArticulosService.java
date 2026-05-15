package com.SeyaCloudGestion.GestionSistema.feacture.articulos.domain.services;

import com.SeyaCloudGestion.GestionSistema.feacture.articulos.application.dto.request.RequestRegistroArticulo;
import com.SeyaCloudGestion.GestionSistema.feacture.articulos.application.dto.response.ResponseRegistroArticulo;
import com.SeyaCloudGestion.GestionSistema.feacture.articulos.domain.interfaces.IArticulosRegistro;
import com.SeyaCloudGestion.GestionSistema.feacture.articulos.infraestructure.persistence.repository.crud.ArticulosRegistroRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ArticulosService implements IArticulosRegistro {

    private final ArticulosRegistroRepository articulosRegistroRepository;

    public ArticulosService (ArticulosRegistroRepository articulosRegistroRepository) {
        this.articulosRegistroRepository = articulosRegistroRepository;
    }

    @Override
    @CacheEvict(value = {"articulos_lista","articulo_detalle"}, allEntries = true)
    public ResponseRegistroArticulo registrarArticulo(RequestRegistroArticulo request) {
        return articulosRegistroRepository.registrarArticulo(request);
    }
}
