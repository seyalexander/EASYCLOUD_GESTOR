package com.SeyaCloudGestion.GestionSistema.feacture.caja.application.useCase;

import com.SeyaCloudGestion.GestionSistema.feacture.caja.application.dto.request.RequestRegistroCaja;
import com.SeyaCloudGestion.GestionSistema.feacture.caja.application.dto.response.ResponseRegistroCaja;
import com.SeyaCloudGestion.GestionSistema.feacture.caja.domain.services.CajaService;
import org.springframework.stereotype.Component;

@Component
public class RegistroCajaUseCase {

    private final CajaService cajaService;

    public RegistroCajaUseCase(CajaService cajaService) {
        this.cajaService = cajaService;
    }

    public ResponseRegistroCaja RegistroCaja(RequestRegistroCaja request) {
        try {

            ResponseRegistroCaja response = cajaService.RegistroCaja(request);

            if (response.isExito()) {
            }

            return response;

        } catch (IllegalArgumentException | SecurityException e) {
            ResponseRegistroCaja response = new ResponseRegistroCaja();
            response.setExito(false);
            response.setMessage(e.getMessage());
            return response;
        } catch (Exception e) {
            String mensajeError = "Error inesperado al registrar la caja: " + e.getMessage();
            System.err.println("[ERROR] " + mensajeError);

            ResponseRegistroCaja response = new ResponseRegistroCaja();
            response.setExito(false);
            response.setMessage(mensajeError);
            return response;
        }
    }
}