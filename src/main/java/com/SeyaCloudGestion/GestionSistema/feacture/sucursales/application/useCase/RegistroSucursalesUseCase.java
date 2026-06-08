package com.SeyaCloudGestion.GestionSistema.feacture.sucursales.application.useCase;

import com.SeyaCloudGestion.GestionSistema.feacture.sucursales.application.dto.request.RequestRegistroSucursales;
import com.SeyaCloudGestion.GestionSistema.feacture.sucursales.application.dto.response.ResponseRegistroSucursales;
import com.SeyaCloudGestion.GestionSistema.feacture.sucursales.domain.services.SucursalesService;
import org.springframework.stereotype.Component;

@Component
public class RegistroSucursalesUseCase {
    private final SucursalesService sucursalesService;

    public RegistroSucursalesUseCase(SucursalesService sucursalesService) {
        this.sucursalesService = sucursalesService;
    }

    public ResponseRegistroSucursales RegistroSucursales(RequestRegistroSucursales request) {
        try {
            ResponseRegistroSucursales response = sucursalesService.RegistroSucursales(request);

            if (response.isExito()) {
            }

            return response;

        } catch (IllegalArgumentException | SecurityException e) {
            ResponseRegistroSucursales response = new ResponseRegistroSucursales();
            response.setExito(false);
            response.setMessage(e.getMessage());
            return response;

        } catch (Exception e) {
            String mensajeError = "Error inesperado al registrar la sucursal: " + e.getMessage();
            System.err.println("[ERROR] " + mensajeError);

            ResponseRegistroSucursales response = new ResponseRegistroSucursales();
            response.setExito(false);
            response.setMessage(mensajeError);
            return response;
        }
    }
}