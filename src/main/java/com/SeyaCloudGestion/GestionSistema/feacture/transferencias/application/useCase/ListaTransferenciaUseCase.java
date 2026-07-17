package com.SeyaCloudGestion.GestionSistema.feacture.transferencias.application.useCase;

import com.SeyaCloudGestion.GestionSistema.feacture.transferencias.application.dto.request.RequestListaTransferencia;
import com.SeyaCloudGestion.GestionSistema.feacture.transferencias.application.dto.response.ResponseListaTransferencia;
import com.SeyaCloudGestion.GestionSistema.feacture.transferencias.domain.services.TransferenciaService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ListaTransferenciaUseCase {

    private final TransferenciaService transferenciaService;

    public ListaTransferenciaUseCase(
            TransferenciaService transferenciaService
    ) {
        this.transferenciaService = transferenciaService;
    }

    public ResponseListaTransferencia ListaTransferencia(RequestListaTransferencia request) {
        try {
            ResponseListaTransferencia response = transferenciaService.listaTransferencia(request);

            if (response.isExito()) {
            }

            return response;

        } catch (IllegalArgumentException | SecurityException e) {
            ResponseListaTransferencia response = new ResponseListaTransferencia();
            response.setExito(false);
            response.setMessage(e.getMessage());
            response.setTransferencias(List.of());
            return response;
        } catch (Exception e) {
            String mensajeError = "Error inesperado al listar las transferencias: " + e.getMessage();
            System.err.println("[ERROR] " + mensajeError);
            ResponseListaTransferencia response = new ResponseListaTransferencia();
            response.setExito(false);
            response.setMessage(mensajeError);
            response.setTransferencias(List.of());
            return response;
        }
    }
}