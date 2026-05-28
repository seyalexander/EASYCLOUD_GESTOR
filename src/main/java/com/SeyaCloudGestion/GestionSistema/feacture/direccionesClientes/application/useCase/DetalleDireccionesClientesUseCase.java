package com.SeyaCloudGestion.GestionSistema.feacture.direccionesClientes.application.useCase;

import com.SeyaCloudGestion.GestionSistema.feacture.direccionesClientes.application.dto.request.RequestDetalleDireccionesClientes;
import com.SeyaCloudGestion.GestionSistema.feacture.direccionesClientes.application.dto.response.ResponseDetalleDireccionesClientes;
import com.SeyaCloudGestion.GestionSistema.feacture.direccionesClientes.domain.services.DireccionesClientesService;
import org.springframework.stereotype.Component;

@Component
public class DetalleDireccionesClientesUseCase {
    private final DireccionesClientesService direccionesClientesService;

    public DetalleDireccionesClientesUseCase(DireccionesClientesService direccionesClientesService) {
        this.direccionesClientesService = direccionesClientesService;
    }
    public ResponseDetalleDireccionesClientes DetalleDireccionesClientes(Long idDireccionCliente) {
        try {
            RequestDetalleDireccionesClientes request = new RequestDetalleDireccionesClientes();
            request.setIdDireccionesClientes(idDireccionCliente);
            ResponseDetalleDireccionesClientes response = direccionesClientesService.DetalleDireccionesClientes(request);
            if(response.isExito()){}
            return response;
        } catch (IllegalArgumentException | SecurityException e) {
            ResponseDetalleDireccionesClientes response = new ResponseDetalleDireccionesClientes();
            response.setExito(false);
            response.setMessage(e.getMessage());
            return response;
        } catch (Exception e) {
            String mensajeError = "Error inesperado al obtener el detalle de la dirección del cliente: " + e.getMessage();
            System.err.println("[ERROR] " + mensajeError);
            ResponseDetalleDireccionesClientes response = new ResponseDetalleDireccionesClientes();
            response.setExito(false);
            response.setMessage(mensajeError);
            return response;
        }
    }
}