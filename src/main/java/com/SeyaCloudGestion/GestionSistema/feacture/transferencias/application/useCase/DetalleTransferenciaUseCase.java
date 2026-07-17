package com.SeyaCloudGestion.GestionSistema.feacture.transferencias.application.useCase;

import com.SeyaCloudGestion.GestionSistema.feacture.transferencias.application.dto.request.RequestDetalleTransferencia;
import com.SeyaCloudGestion.GestionSistema.feacture.transferencias.application.dto.response.ResponseDetalleTransferencia;
import com.SeyaCloudGestion.GestionSistema.feacture.transferencias.domain.services.TransferenciaService;
import org.springframework.stereotype.Component;

@Component
public class DetalleTransferenciaUseCase {

    private final TransferenciaService transferenciaService;

    public DetalleTransferenciaUseCase(
            TransferenciaService transferenciaService
    ) {
        this.transferenciaService = transferenciaService;
    }

    public ResponseDetalleTransferencia DetalleTransferencia(long idTransferencia) {
        try {
            RequestDetalleTransferencia request = new RequestDetalleTransferencia();
            request.setIdTransferencia(idTransferencia);

            ResponseDetalleTransferencia response = transferenciaService.DetalleTransferencia(request);

            if (response.isExito()) {
            }

            return response;

        } catch (IllegalArgumentException | SecurityException e) {
            ResponseDetalleTransferencia response = new ResponseDetalleTransferencia();
            response.setExito(false);
            response.setMessage(e.getMessage());
            return response;
        } catch (Exception e) {
            String mensajeError = "Error inesperado al obtener el detalle de la transferencia: " + e.getMessage();
            System.err.println("[ERROR] " + mensajeError);
            ResponseDetalleTransferencia response = new ResponseDetalleTransferencia();
            response.setExito(false);
            response.setMessage(mensajeError);
            return response;
        }
    }
}