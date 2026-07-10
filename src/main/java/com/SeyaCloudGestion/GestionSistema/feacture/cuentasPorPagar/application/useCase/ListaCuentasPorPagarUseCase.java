package com.SeyaCloudGestion.GestionSistema.feacture.cuentasPorPagar.application.useCase;

import com.SeyaCloudGestion.GestionSistema.feacture.cuentasPorPagar.application.dto.request.RequestListaCuentasPorPagar;
import com.SeyaCloudGestion.GestionSistema.feacture.cuentasPorPagar.application.dto.response.ResponseListaCuentasPorPagar;
import com.SeyaCloudGestion.GestionSistema.feacture.cuentasPorPagar.domain.services.CuentasPorPagarService;
import org.springframework.stereotype.Component;

@Component
public class ListaCuentasPorPagarUseCase {

    private final CuentasPorPagarService cuentasPorPagarService;

    public ListaCuentasPorPagarUseCase(
            CuentasPorPagarService cuentasPorPagarService
    ) {
        this.cuentasPorPagarService = cuentasPorPagarService;
    }

    public ResponseListaCuentasPorPagar listaCuentasPorPagar(RequestListaCuentasPorPagar request) {
        try {
            ResponseListaCuentasPorPagar response = cuentasPorPagarService.listaCuentasPorPagar(request);
            if (response.isExito()) {}

            return response;

        } catch (IllegalArgumentException | SecurityException e) {
            ResponseListaCuentasPorPagar response = new ResponseListaCuentasPorPagar();
            response.setExito(false);
            response.setMessage(e.getMessage());
            response.setCuentasPorPagar(java.util.List.of());
            return response;
        } catch (Exception e) {
            String mensajeError = "Error inesperado al listar las cuentas por pagar: " + e.getMessage();
            System.err.println("[ERROR] " + mensajeError);
            ResponseListaCuentasPorPagar response = new ResponseListaCuentasPorPagar();
            response.setExito(false);
            response.setMessage(mensajeError);
            response.setCuentasPorPagar(java.util.List.of());
            return response;
        }
    }
}