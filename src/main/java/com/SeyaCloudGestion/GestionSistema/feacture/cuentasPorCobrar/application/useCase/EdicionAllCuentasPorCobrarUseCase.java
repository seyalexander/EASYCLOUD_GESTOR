package com.SeyaCloudGestion.GestionSistema.feacture.cuentasPorCobrar.application.useCase;

import com.SeyaCloudGestion.GestionSistema.feacture.cuentasPorCobrar.application.dto.request.RequestEditarAllCuentasPorCobrar;
import com.SeyaCloudGestion.GestionSistema.feacture.cuentasPorCobrar.application.dto.response.ResponseEditarAllCuentasPorCobrar;
import com.SeyaCloudGestion.GestionSistema.feacture.cuentasPorCobrar.domain.services.CuentasPorCobrarService;
import org.springframework.stereotype.Component;

@Component
public class EdicionAllCuentasPorCobrarUseCase {
    private final CuentasPorCobrarService cuentasPorCobrarService;

    public EdicionAllCuentasPorCobrarUseCase(CuentasPorCobrarService cuentasPorCobrarService) {
        this.cuentasPorCobrarService = cuentasPorCobrarService;
    }
    public ResponseEditarAllCuentasPorCobrar EdicionAllCuentasPorCobrar(RequestEditarAllCuentasPorCobrar request) {
        try {
            ResponseEditarAllCuentasPorCobrar response = cuentasPorCobrarService.EditarAllCuentasPorCobrar(request);

            if (response.isExito()) {
            }

            return response;

        } catch (IllegalArgumentException | SecurityException e) {
            ResponseEditarAllCuentasPorCobrar response = new ResponseEditarAllCuentasPorCobrar();
            response.setExito(false);
            response.setMessage(e.getMessage());
            return response;

        } catch (Exception e) {
            String mensajeError = "Error inesperado al actualizar la cuenta por cobrar: " + e.getMessage();
            System.err.println("[ERROR] " + mensajeError);

            ResponseEditarAllCuentasPorCobrar response = new ResponseEditarAllCuentasPorCobrar();
            response.setExito(false);
            response.setMessage(mensajeError);
            return response;
        }
    }
}