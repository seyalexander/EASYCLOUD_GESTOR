package com.SeyaCloudGestion.GestionSistema.feacture.transferencias.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.feacture.transferencias.application.dto.request.RequestRegistroTransferencia;
import com.SeyaCloudGestion.GestionSistema.feacture.transferencias.application.dto.response.ResponseRegistroTransferencia;

public interface ITransferenciaRegistro {
    ResponseRegistroTransferencia RegistroTransferencia(RequestRegistroTransferencia request);
}