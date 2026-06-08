package com.SeyaCloudGestion.GestionSistema.feacture.sucursales.application.useCase;

import com.SeyaCloudGestion.GestionSistema.feacture.sucursales.application.dto.request.RequestListaSucursales;
import com.SeyaCloudGestion.GestionSistema.feacture.sucursales.application.dto.response.ResponseListaSucursales;
import com.SeyaCloudGestion.GestionSistema.feacture.sucursales.domain.services.SucursalesService;
import org.springframework.stereotype.Component;

@Component
public class ListaSucursalesUseCase {
    private final SucursalesService sucursalesService;

    public ListaSucursalesUseCase(SucursalesService sucursalesService) {
        this.sucursalesService = sucursalesService;
    }
    public ResponseListaSucursales ListaSucursales(RequestListaSucursales request) {
        try {
            ResponseListaSucursales response = sucursalesService.ListaSucursales(request);

            if (response.isExito()) {
            }

            return response;

        } catch (IllegalArgumentException | SecurityException e) {
            ResponseListaSucursales response = new ResponseListaSucursales();
            response.setExito(false);
            response.setMessage(e.getMessage());
            response.setSucursales(java.util.List.of());
            return response;

        } catch (Exception e) {
            String mensajeError = "Error inesperado al listar las sucursales: " + e.getMessage();
            System.err.println("[ERROR] " + mensajeError);

            ResponseListaSucursales response = new ResponseListaSucursales();
            response.setExito(false);
            response.setMessage(mensajeError);
            response.setSucursales(java.util.List.of());
            return response;
        }
    }
}