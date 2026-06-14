package com.SeyaCloudGestion.GestionSistema.feacture.clientes.application.useCase;

import com.SeyaCloudGestion.GestionSistema.common.exceptions.ResourceNotFoundException;
import com.SeyaCloudGestion.GestionSistema.feacture.clientes.application.dto.request.RequestDetalleCliente;
import com.SeyaCloudGestion.GestionSistema.feacture.clientes.application.dto.request.RequestEditarAllCliente;
import com.SeyaCloudGestion.GestionSistema.feacture.clientes.application.dto.request.RequestEditarEstadoCliente;
import com.SeyaCloudGestion.GestionSistema.feacture.clientes.application.dto.response.ResponseDetalleCliente;
import com.SeyaCloudGestion.GestionSistema.feacture.clientes.application.dto.response.ResponseEditarAllCliente;
import com.SeyaCloudGestion.GestionSistema.feacture.clientes.application.dto.response.ResponseEditarEstadoCliente;
import com.SeyaCloudGestion.GestionSistema.feacture.clientes.domain.services.ClienteService;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class EdicionClienteEstadoUseCase {
    private final ClienteService  clienteService;

    public EdicionClienteEstadoUseCase(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    public ResponseEditarEstadoCliente AnularCliente(long id) {
        try {
            //verificar la existencia del cliente
            RequestDetalleCliente requestDetalleCliente = new RequestDetalleCliente();
            requestDetalleCliente.setIdCliente(id);

            ResponseDetalleCliente detalleBDCliente= clienteService.DetalleCliente(requestDetalleCliente);

            if (!detalleBDCliente.isExito() || detalleBDCliente.getCliente() == null) {
                throw new ResourceNotFoundException("El Id del cliente no existe.");
            }
            if (Objects.equals(detalleBDCliente.getCliente().getEstado(), 0)) {
                throw new IllegalArgumentException("El Cliente ya se encuentra anulado.");
            }

            RequestEditarEstadoCliente request = new RequestEditarEstadoCliente();
            request.setIdCliente(id);
            ResponseEditarEstadoCliente response = clienteService.EditarEstadoCliente(request,0);
            if(response.isExito()){}
            return response;

        }catch (IllegalArgumentException | SecurityException e){
            ResponseEditarEstadoCliente response = new ResponseEditarEstadoCliente();
            response.setExito(false);
            response.setMessage(e.getMessage());
            return response;
        }
        catch (Exception e){
            String mensajeError = "Error inesperado al anular el cliente: " + e.getMessage();
            System.err.println("[ERROR] " + mensajeError);
            ResponseEditarEstadoCliente response = new ResponseEditarEstadoCliente();
            response.setExito(false);
            response.setMessage(mensajeError);
            return response;
        }
    }

    public ResponseEditarEstadoCliente ActivarCliente(long id) {
        try {
            //verificar la existencia del cliente
            RequestDetalleCliente requestDetalleCliente = new RequestDetalleCliente();
            requestDetalleCliente.setIdCliente(id);

            ResponseDetalleCliente detalleBDCliente= clienteService.DetalleCliente(requestDetalleCliente);

            if (!detalleBDCliente.isExito() || detalleBDCliente.getCliente() == null) {
                throw new ResourceNotFoundException("El Id del cliente no existe.");
            }
            if (Objects.equals(detalleBDCliente.getCliente().getEstado(), 1)) {
                throw new IllegalArgumentException("El Cliente ya se encuentra activado.");
            }


            RequestEditarEstadoCliente request = new RequestEditarEstadoCliente();
            request.setIdCliente(id);
            ResponseEditarEstadoCliente response = clienteService.EditarEstadoCliente(request,1);
            if(response.isExito()){}
            return response;

        }catch (IllegalArgumentException | SecurityException e){
            ResponseEditarEstadoCliente response = new ResponseEditarEstadoCliente();
            response.setExito(false);
            response.setMessage(e.getMessage());
            return response;
        }
        catch (Exception e){
            String mensajeError = "Error inesperado al anular el cliente: " + e.getMessage();
            System.err.println("[ERROR] " + mensajeError);
            ResponseEditarEstadoCliente response = new ResponseEditarEstadoCliente();
            response.setExito(false);
            response.setMessage(mensajeError);
            return response;
        }
    }
}
