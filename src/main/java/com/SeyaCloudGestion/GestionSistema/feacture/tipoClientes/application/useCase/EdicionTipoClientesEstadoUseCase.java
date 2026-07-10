package com.SeyaCloudGestion.GestionSistema.feacture.tipoClientes.application.useCase;

import com.SeyaCloudGestion.GestionSistema.common.exceptions.ResourceNotFoundException;
import com.SeyaCloudGestion.GestionSistema.feacture.tipoClientes.application.dto.request.RequestDetalleTipoClientes;
import com.SeyaCloudGestion.GestionSistema.feacture.tipoClientes.application.dto.request.RequestEditarEstadoTipoClientes;
import com.SeyaCloudGestion.GestionSistema.feacture.tipoClientes.application.dto.response.ResponseDetalleTipoClientes;
import com.SeyaCloudGestion.GestionSistema.feacture.tipoClientes.application.dto.response.ResponseEditarEstadoTipoClientes;
import com.SeyaCloudGestion.GestionSistema.feacture.tipoClientes.domain.services.TipoClientesService;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class EdicionTipoClientesEstadoUseCase {
    private final TipoClientesService tipoClientesService;

    public EdicionTipoClientesEstadoUseCase(TipoClientesService tipoClientesService) {
        this.tipoClientesService = tipoClientesService;
    }

    public ResponseEditarEstadoTipoClientes AnularTipoCliente(long idTipoCliente) {
        try {
            RequestDetalleTipoClientes requestDetalle = new RequestDetalleTipoClientes();
            requestDetalle.setIdTipoCliente(idTipoCliente);

            ResponseDetalleTipoClientes detalleBD= tipoClientesService.DetalleTipoClientes(requestDetalle);

            if (!detalleBD.isExito() || detalleBD.getTipoClientes() == null) {
                throw new ResourceNotFoundException("El Id no existe.");
            }

            if (Objects.equals(detalleBD.getTipoClientes().getEstado(), 0)) {
                throw new IllegalArgumentException("El Tipo cliente ya se encuentra anulado.");
            }

            RequestEditarEstadoTipoClientes request = new RequestEditarEstadoTipoClientes();
            request.setIdTipoClientes(idTipoCliente);
            ResponseEditarEstadoTipoClientes response = tipoClientesService.EditarEstadoTipoClientes(request,0);
            if(response.isExito()){}

            return response;
        } catch (IllegalArgumentException | SecurityException e) {
            ResponseEditarEstadoTipoClientes response = new ResponseEditarEstadoTipoClientes();
            response.setExito(false);
            response.setMessage(e.getMessage());
            return response;
        } catch (Exception e) {
            String mensajeError = "Error inesperado al editar el estado del tipo cliente: " + e.getMessage();
            System.err.println("[ERROR] " + mensajeError);
            ResponseEditarEstadoTipoClientes response = new ResponseEditarEstadoTipoClientes();
            response.setExito(false);
            response.setMessage("Error inesperado: " + e.getMessage());
            return response;
        }
    }

    public ResponseEditarEstadoTipoClientes ActivarTipoCliente(long idTipoCliente) {
        try {
            RequestDetalleTipoClientes requestDetalle = new RequestDetalleTipoClientes();
            requestDetalle.setIdTipoCliente(idTipoCliente);

            ResponseDetalleTipoClientes detalleBD= tipoClientesService.DetalleTipoClientes(requestDetalle);

            if (!detalleBD.isExito() || detalleBD.getTipoClientes() == null) {
                throw new ResourceNotFoundException("El Id no existe.");
            }

            if (Objects.equals(detalleBD.getTipoClientes().getEstado(), 1)) {
                throw new IllegalArgumentException("El Tipo cliente ya se encuentra activado.");
            }

            RequestEditarEstadoTipoClientes request = new RequestEditarEstadoTipoClientes();
            request.setIdTipoClientes(idTipoCliente);
            ResponseEditarEstadoTipoClientes response = tipoClientesService.EditarEstadoTipoClientes(request,1);
            if(response.isExito()){}

            return response;
        } catch (IllegalArgumentException | SecurityException e) {
            ResponseEditarEstadoTipoClientes response = new ResponseEditarEstadoTipoClientes();
            response.setExito(false);
            response.setMessage(e.getMessage());
            return response;
        } catch (Exception e) {
            String mensajeError = "Error inesperado al editar el estado del tipo cliente: " + e.getMessage();
            System.err.println("[ERROR] " + mensajeError);
            ResponseEditarEstadoTipoClientes response = new ResponseEditarEstadoTipoClientes();
            response.setExito(false);
            response.setMessage("Error inesperado: " + e.getMessage());
            return response;
        }
    }
}