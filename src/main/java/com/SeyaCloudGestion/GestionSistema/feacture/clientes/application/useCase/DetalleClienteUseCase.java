package com.SeyaCloudGestion.GestionSistema.feacture.clientes.application.useCase;

import com.SeyaCloudGestion.GestionSistema.feacture.clientes.application.dto.request.RequestDetalleCliente;
import com.SeyaCloudGestion.GestionSistema.feacture.clientes.application.dto.response.ResponseDetalleCliente;
import com.SeyaCloudGestion.GestionSistema.feacture.clientes.domain.services.ClienteService;
import org.springframework.stereotype.Component;

@Component
public class DetalleClienteUseCase {
    private final ClienteService clienteService;

    public DetalleClienteUseCase(ClienteService clienteService) {
        this.clienteService = clienteService;
    }
    public ResponseDetalleCliente DetalleCliente(long idCliente) {
        try {
            RequestDetalleCliente request = new RequestDetalleCliente();
            request.setIdCliente(idCliente);
            ResponseDetalleCliente response = clienteService.DetalleCliente(request);
            if(response.isExito()){}
            return response;
        }catch (IllegalArgumentException | SecurityException e){
            ResponseDetalleCliente response = new ResponseDetalleCliente();
            response.setExito(false);
            response.setMessage(e.getMessage());
            return response;
        }
        catch (Exception e){
            String mensajeError = "Error inesperado al obtener el detalle del cliente: " + e.getMessage();
            System.err.println("[ERROR] " + mensajeError);
            ResponseDetalleCliente response = new ResponseDetalleCliente();
            response.setExito(false);
            response.setMessage(mensajeError);
            return response;
        }
    }
}
