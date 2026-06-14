package com.SeyaCloudGestion.GestionSistema.feacture.tipoClientes.application.useCase;

import com.SeyaCloudGestion.GestionSistema.common.exceptions.ResourceNotFoundException;
import com.SeyaCloudGestion.GestionSistema.feacture.tipoClientes.application.dto.request.RequestDetalleTipoClientes;
import com.SeyaCloudGestion.GestionSistema.feacture.tipoClientes.application.dto.request.RequestEditarAllTipoClientes;
import com.SeyaCloudGestion.GestionSistema.feacture.tipoClientes.application.dto.response.ResponseDetalleTipoClientes;
import com.SeyaCloudGestion.GestionSistema.feacture.tipoClientes.application.dto.response.ResponseEditarAllTipoClientes;
import com.SeyaCloudGestion.GestionSistema.feacture.tipoClientes.domain.services.TipoClientesService;
import com.SeyaCloudGestion.GestionSistema.feacture.tipoClientes.domain.validations.VerificarCambiosTipocliente;
import org.springframework.stereotype.Component;

@Component
public class EdicionTipoClientesUseCase {
    private final TipoClientesService tipoClientesService;
    private final VerificarCambiosTipocliente verificarCambiosTipocliente;

    public EdicionTipoClientesUseCase(TipoClientesService tipoClientesService, VerificarCambiosTipocliente verificarCambiosTipocliente) {
        this.tipoClientesService = tipoClientesService;
        this.verificarCambiosTipocliente = verificarCambiosTipocliente;
    }

    public ResponseEditarAllTipoClientes EdicionAllTipoClientes(RequestEditarAllTipoClientes request) {
        try {
            RequestDetalleTipoClientes requestDetalle = new RequestDetalleTipoClientes();
            requestDetalle.setIdTipoCliente(request.getIdTipoCliente());

            ResponseDetalleTipoClientes detalleBD= tipoClientesService.DetalleTipoClientes(requestDetalle);

            if (!detalleBD.isExito() || detalleBD.getTipoClientes() == null) {
                throw new ResourceNotFoundException("El Id no existe.");
            }

            //verificar cambios
            if (!verificarCambiosTipocliente.verificarCambios(detalleBD.getTipoClientes(), request)) {
                throw new ResourceNotFoundException("No se detectaron cambios para actualizar.");
            }

            ResponseEditarAllTipoClientes response = tipoClientesService.EditarAllTipoClientes(request);
            if (response.isExito()) {
            }
            return response;
        } catch (IllegalArgumentException | SecurityException e) {
            ResponseEditarAllTipoClientes response = new ResponseEditarAllTipoClientes();
            response.setExito(false);
            response.setMessage(e.getMessage());
            return response;
        } catch (Exception e) {
            String mensajeError = "Error inesperado al obtener los datos para editar el tipo cliente: " + e.getMessage();
            System.err.println("[ERROR] " + mensajeError);
            ResponseEditarAllTipoClientes response = new ResponseEditarAllTipoClientes();
            response.setExito(false);
            response.setMessage("Error inesperado: " + e.getMessage());
            return response;
        }
    }
}