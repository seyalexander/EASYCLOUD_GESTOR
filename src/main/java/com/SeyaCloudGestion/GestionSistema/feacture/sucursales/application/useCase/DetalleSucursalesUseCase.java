package com.SeyaCloudGestion.GestionSistema.feacture.sucursales.application.useCase;

import com.SeyaCloudGestion.GestionSistema.feacture.sucursales.application.dto.request.RequestDetalleSucursales;
import com.SeyaCloudGestion.GestionSistema.feacture.sucursales.application.dto.response.ResponseDetalleSucursales;
import com.SeyaCloudGestion.GestionSistema.feacture.sucursales.domain.services.SucursalesService;
import org.springframework.stereotype.Component;

@Component
public class DetalleSucursalesUseCase {
    private final SucursalesService sucursalesService;

    public DetalleSucursalesUseCase(SucursalesService sucursalesService) {
        this.sucursalesService = sucursalesService;
    }
    public ResponseDetalleSucursales DetalleSucursales(long idSucursales) {
        try {
            RequestDetalleSucursales request = new RequestDetalleSucursales();
            request.setIdSucursales(idSucursales);

            ResponseDetalleSucursales response = sucursalesService.DetalleSucursales(request);

            if (response.isExito()) {
            }

            return response;

        } catch (IllegalArgumentException | SecurityException e) {
            ResponseDetalleSucursales response = new ResponseDetalleSucursales();
            response.setExito(false);
            response.setMessage(e.getMessage());
            return response;

        } catch (Exception e) {
            String mensajeError = "Error inesperado al ver el detalle de la sucursal: " + e.getMessage();
            System.err.println("[ERROR] " + mensajeError);

            ResponseDetalleSucursales response = new ResponseDetalleSucursales();
            response.setExito(false);
            response.setMessage(mensajeError);
            return response;
        }
    }

}