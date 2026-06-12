package com.SeyaCloudGestion.GestionSistema.feacture.sucursales.application.useCase;

import com.SeyaCloudGestion.GestionSistema.common.exceptions.ResourceNotFoundException;
import com.SeyaCloudGestion.GestionSistema.feacture.sucursales.application.dto.request.RequestDetalleSucursales;
import com.SeyaCloudGestion.GestionSistema.feacture.sucursales.application.dto.request.RequestEditarEstadoSucursales;
import com.SeyaCloudGestion.GestionSistema.feacture.sucursales.application.dto.response.ResponseDetalleSucursales;
import com.SeyaCloudGestion.GestionSistema.feacture.sucursales.application.dto.response.ResponseEditarEstadoSucursales;
import com.SeyaCloudGestion.GestionSistema.feacture.sucursales.domain.services.SucursalesService;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class EdicionSucursalesEstadoUseCase {
    private final SucursalesService sucursalesService;

    public EdicionSucursalesEstadoUseCase(SucursalesService sucursalesService) {
        this.sucursalesService = sucursalesService;
    }

    public ResponseEditarEstadoSucursales AnularSucursales(long idSucursales) {
        try {
            RequestDetalleSucursales requestDetalle = new RequestDetalleSucursales();
            requestDetalle.setIdSucursales(idSucursales);

            ResponseDetalleSucursales detalleBD= sucursalesService.DetalleSucursales(requestDetalle);

            if (!detalleBD.isExito() || detalleBD.getSucursales() == null) {
                throw new ResourceNotFoundException("El Id no existe.");
            }
            if (Objects.equals(detalleBD.getSucursales().getEstado(), 0)) {
                throw new IllegalArgumentException("La sucursal ya se encuentra anulada.");
            }

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
            RequestDetalleSucursales requestDetalle = new RequestDetalleSucursales();
            requestDetalle.setIdSucursales(idSucursales);

            ResponseDetalleSucursales detalleBD= sucursalesService.DetalleSucursales(requestDetalle);

            if (!detalleBD.isExito() || detalleBD.getSucursales() == null) {
                throw new ResourceNotFoundException("El Id no existe.");
            }
            if (Objects.equals(detalleBD.getSucursales().getEstado(), 1)) {
                throw new IllegalArgumentException("La sucursal ya se encuentra activada.");
            }

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