package com.SeyaCloudGestion.GestionSistema.feacture.comprobantes.domain.services;

import com.SeyaCloudGestion.GestionSistema.feacture.comprobantes.application.dto.request.RequestRegistroComprobante;
import com.SeyaCloudGestion.GestionSistema.feacture.comprobantes.application.dto.response.ResponseRegistroComprobante;
import com.SeyaCloudGestion.GestionSistema.feacture.comprobantes.domain.interfaces.IComprobanteRegistro;
import com.SeyaCloudGestion.GestionSistema.feacture.comprobantes.infraestructure.persistence.repository.crud.ComprobanteRegistroRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ComprobanteService implements IComprobanteRegistro {

    private final ComprobanteRegistroRepository comprobanteRegistroRepository;

    public ComprobanteService(ComprobanteRegistroRepository comprobanteRegistroRepository) {
        this.comprobanteRegistroRepository = comprobanteRegistroRepository;
    }

    @Override
    @CacheEvict(value = {"comprobantes_lista"}, allEntries = true)
    public ResponseRegistroComprobante RegistroComprobante(RequestRegistroComprobante request,String urlXml,String urlPdf,String numero) {
        return comprobanteRegistroRepository.RegistroComprobante(request,urlXml,urlPdf,numero);
    }
}