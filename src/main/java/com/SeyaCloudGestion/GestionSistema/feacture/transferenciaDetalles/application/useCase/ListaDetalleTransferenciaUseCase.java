package com.SeyaCloudGestion.GestionSistema.feacture.transferenciaDetalles.application.useCase;

import com.SeyaCloudGestion.GestionSistema.feacture.transferenciaDetalles.application.dto.request.RequestListaDetalleTransferencia;
import com.SeyaCloudGestion.GestionSistema.feacture.transferenciaDetalles.application.dto.response.ResponseListaTransferenciaDetalle;
import com.SeyaCloudGestion.GestionSistema.feacture.transferenciaDetalles.domain.services.TransferenciaDetalleService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ListaDetalleTransferenciaUseCase {

    private final TransferenciaDetalleService transferenciaDetalleService;

    public ListaDetalleTransferenciaUseCase(TransferenciaDetalleService transferenciaDetalleService) {
        this.transferenciaDetalleService = transferenciaDetalleService;
    }

    public ResponseListaTransferenciaDetalle ListaDetalleTransferencia(long idTransferencia) {
        try {
            RequestListaDetalleTransferencia request = new RequestListaDetalleTransferencia();
            request.setIdTransferencia(idTransferencia);
            ResponseListaTransferenciaDetalle response = transferenciaDetalleService.listaDetalleTransferencia(request);

            if (response.isExito()) {
            }

            return response;

        } catch (IllegalArgumentException | SecurityException e) {
            ResponseListaTransferenciaDetalle response = new ResponseListaTransferenciaDetalle();
            response.setExito(false);
            response.setMessage(e.getMessage());
            response.setDetalles(List.of());
            return response;
        } catch (Exception e) {
            String mensajeError = "Error inesperado al listar los detalles de la transferencia: " + e.getMessage();
            System.err.println("[ERROR] " + mensajeError);
            ResponseListaTransferenciaDetalle response = new ResponseListaTransferenciaDetalle();
            response.setExito(false);
            response.setMessage(mensajeError);
            response.setDetalles(List.of());
            return response;
        }
    }
}