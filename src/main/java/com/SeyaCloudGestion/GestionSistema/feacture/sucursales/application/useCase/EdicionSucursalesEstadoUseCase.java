package com.SeyaCloudGestion.GestionSistema.feacture.sucursales.application.useCase;

import com.SeyaCloudGestion.GestionSistema.feacture.sucursales.application.dto.request.RequestEditarEstadoSucursales;
import com.SeyaCloudGestion.GestionSistema.feacture.sucursales.application.dto.response.ResponseEditarEstadoSucursales;
import com.SeyaCloudGestion.GestionSistema.feacture.sucursales.domain.services.SucursalesService;
import org.springframework.stereotype.Component;

@Component
public class EdicionSucursalesEstadoUseCase {
    private final SucursalesService sucursalesService;

    public EdicionSucursalesEstadoUseCase(SucursalesService sucursalesService) {
        this.sucursalesService = sucursalesService;
    }

    public ResponseEditarEstadoSucursales AnularSucursales(long idSucursales) {
        try {
            RequestEditarEstadoSucursales request = new RequestEditarEstadoSucursales();
            request.setIdSucursales(idSucursales);

            ResponseEditarEstadoSucursales response = sucursalesService.EditarEstadoSucursales(request, 0);

            if (response.isExito()) {
            }

            return response;

        } catch (IllegalArgumentException | SecurityException e) {
            ResponseEditarEstadoSucursales response = new ResponseEditarEstadoSucursales();
            response.setExito(false);
            response.setMessage(e.getMessage());
            return response;

        } catch (Exception e) {
            String mensajeError = "Error inesperado al actualizar la sucursal: " + e.getMessage();
            System.err.println("[ERROR] " + mensajeError);

            ResponseEditarEstadoSucursales response = new ResponseEditarEstadoSucursales();
            response.setExito(false);
            response.setMessage(mensajeError);
            return response;
        }
    }

    public ResponseEditarEstadoSucursales ActivarSucursales(long idSucursales) {
        try {
            RequestEditarEstadoSucursales request = new RequestEditarEstadoSucursales();
            request.setIdSucursales(idSucursales);

            ResponseEditarEstadoSucursales response = sucursalesService.EditarEstadoSucursales(request, 1);

            if (response.isExito()) {
            }

            return response;

        } catch (IllegalArgumentException | SecurityException e) {
            ResponseEditarEstadoSucursales response = new ResponseEditarEstadoSucursales();
            response.setExito(false);
            response.setMessage(e.getMessage());
            return response;

        } catch (Exception e) {
            String mensajeError = "Error inesperado al actualizar la sucursal: " + e.getMessage();
            System.err.println("[ERROR] " + mensajeError);

            ResponseEditarEstadoSucursales response = new ResponseEditarEstadoSucursales();
            response.setExito(false);
            response.setMessage(mensajeError);
            return response;
        }
    }
}