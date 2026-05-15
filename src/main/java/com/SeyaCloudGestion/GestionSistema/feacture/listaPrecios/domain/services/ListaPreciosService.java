package com.SeyaCloudGestion.GestionSistema.feacture.listaPrecios.domain.services;

import com.SeyaCloudGestion.GestionSistema.feacture.listaPrecios.application.dto.request.RequestListarListaPrecios;
import com.SeyaCloudGestion.GestionSistema.feacture.listaPrecios.application.dto.response.ResponseListaListaPrecios;
import com.SeyaCloudGestion.GestionSistema.feacture.listaPrecios.application.dto.response.ResponseRegistroListaPrecios;
import com.SeyaCloudGestion.GestionSistema.feacture.listaPrecios.domain.interfaces.IListaPreciosListado;
import com.SeyaCloudGestion.GestionSistema.feacture.listaPrecios.infraestructure.persistence.repository.crud.ListaPreciosListaRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ListaPreciosService implements IListaPreciosListado {

    private final ListaPreciosListaRepository listaPreciosListaRepository;

    public ListaPreciosService(ListaPreciosListaRepository listaPreciosListaRepository) {
        this.listaPreciosListaRepository = listaPreciosListaRepository;
    }

    @Override
    @Cacheable(value = "listaPrecios_lista", key = "#request.estado")
    public ResponseListaListaPrecios ListarListaPrecios(RequestListarListaPrecios request) {
        return listaPreciosListaRepository.ListarListaPrecios(request);
    }
}
