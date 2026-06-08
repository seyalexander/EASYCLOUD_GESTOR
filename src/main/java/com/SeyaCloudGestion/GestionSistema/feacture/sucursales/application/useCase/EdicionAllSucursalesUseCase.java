package com.SeyaCloudGestion.GestionSistema.feacture.sucursales.application.useCase;

import com.SeyaCloudGestion.GestionSistema.feacture.sucursales.application.dto.request.RequestEditarAllSucursales;
import com.SeyaCloudGestion.GestionSistema.feacture.sucursales.application.dto.response.ResponseEditarAllSucursales;
import com.SeyaCloudGestion.GestionSistema.feacture.sucursales.domain.services.SucursalesService;
import org.springframework.stereotype.Component;

@Component
public class EdicionAllSucursalesUseCase {
    private final SucursalesService sucursalesService;

    public EdicionAllSucursalesUseCase(SucursalesService sucursalesService) {
        this.sucursalesService = sucursalesService;
    }
    public ResponseEditarAllSucursales EdicionAllSucursales(RequestEditarAllSucursales request) {
        try {
            ResponseEditarAllSucursales response = sucursalesService.EditarAllSucursales(request);

            if (response.isExito()) {
            }

            return response;

        } catch (IllegalArgumentException | SecurityException e) {
            ResponseEditarAllSucursales response = new ResponseEditarAllSucursales();
            response.setExito(false);
            response.setMessage(e.getMessage());
            return response;

        } catch (Exception e) {
            String mensajeError = "Error inesperado al actualizar la sucursal: " + e.getMessage();
            System.err.println("[ERROR] " + mensajeError);

            ResponseEditarAllSucursales response = new ResponseEditarAllSucursales();
            response.setExito(false);
            response.setMessage(mensajeError);
            return response;
        }
    }
}