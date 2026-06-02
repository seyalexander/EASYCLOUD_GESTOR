package com.SeyaCloudGestion.GestionSistema.feacture.cuentasPorCobrar.application.useCase;

import com.SeyaCloudGestion.GestionSistema.feacture.cuentasPorCobrar.application.dto.request.RequestRegistroCuentasPorCobrar;
import com.SeyaCloudGestion.GestionSistema.feacture.cuentasPorCobrar.application.dto.response.ResponseRegistroCuentasPorCobrar;
import com.SeyaCloudGestion.GestionSistema.feacture.cuentasPorCobrar.domain.services.CuentasPorCobrarService;
import org.springframework.stereotype.Component;

@Component
public class RegistroCuentasPorCobrarUseCase {
    private final CuentasPorCobrarService cuentasPorCobrarService;

    public RegistroCuentasPorCobrarUseCase(CuentasPorCobrarService cuentasPorCobrarService) {
        this.cuentasPorCobrarService = cuentasPorCobrarService;
    }

    public ResponseRegistroCuentasPorCobrar RegistroCuentasPorCobrar(RequestRegistroCuentasPorCobrar request) {
        try {
            ResponseRegistroCuentasPorCobrar response = cuentasPorCobrarService.RegistroCuentasPorCobrar(request);

            if (response.isExito()) {
            }

            return response;

        } catch (IllegalArgumentException | SecurityException e) {
            ResponseRegistroCuentasPorCobrar response = new ResponseRegistroCuentasPorCobrar();
            response.setExito(false);
            response.setMessage(e.getMessage());
            return response;

        } catch (Exception e) {
            String mensajeError = "Error inesperado al registrar la cuenta por cobrar: " + e.getMessage();
            System.err.println("[ERROR] " + mensajeError);

            ResponseRegistroCuentasPorCobrar response = new ResponseRegistroCuentasPorCobrar();
            response.setExito(false);
            response.setMessage(mensajeError);
            return response;
        }
    }
}