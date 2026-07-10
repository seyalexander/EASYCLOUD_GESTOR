package com.SeyaCloudGestion.GestionSistema.feacture.sucursales.application.useCase;

import com.SeyaCloudGestion.GestionSistema.common.exceptions.ResourceNotFoundException;
import com.SeyaCloudGestion.GestionSistema.feacture.sucursales.application.dto.request.RequestDetalleSucursales;
import com.SeyaCloudGestion.GestionSistema.feacture.sucursales.application.dto.request.RequestEditarAllSucursales;
import com.SeyaCloudGestion.GestionSistema.feacture.sucursales.application.dto.response.ResponseDetalleSucursales;
import com.SeyaCloudGestion.GestionSistema.feacture.sucursales.application.dto.response.ResponseEditarAllSucursales;
import com.SeyaCloudGestion.GestionSistema.feacture.sucursales.domain.services.SucursalesService;
import com.SeyaCloudGestion.GestionSistema.feacture.sucursales.domain.validations.VerificarCambiosSucursal;
import org.springframework.stereotype.Component;

@Component
public class EdicionAllSucursalesUseCase {
    private final SucursalesService sucursalesService;
    private final VerificarCambiosSucursal verificarCambiosSucursal;

    public EdicionAllSucursalesUseCase(SucursalesService sucursalesService, VerificarCambiosSucursal verificarCambiosSucursal) {
        this.sucursalesService = sucursalesService;
        this.verificarCambiosSucursal = verificarCambiosSucursal;
    }
    public ResponseEditarAllSucursales EdicionAllSucursales(RequestEditarAllSucursales request) {
        try {

            RequestDetalleSucursales requestDetalle = new RequestDetalleSucursales();
            requestDetalle.setIdSucursales(request.getIdSucursales());

            ResponseDetalleSucursales detalleBD= sucursalesService.DetalleSucursales(requestDetalle);

            if (!detalleBD.isExito() || detalleBD.getSucursales() == null) {
                throw new ResourceNotFoundException("El Id no existe.");
            }

            if (!verificarCambiosSucursal.verificarCambios(detalleBD.getSucursales(), request)) {
                throw new ResourceNotFoundException("No se detectaron cambios para actualizar.");
            }


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