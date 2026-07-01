package com.SeyaCloudGestion.GestionSistema.feacture.proveedores.application.useCase;

import com.SeyaCloudGestion.GestionSistema.feacture.proveedores.application.dto.request.RequestListaProveedor;
import com.SeyaCloudGestion.GestionSistema.feacture.proveedores.application.dto.response.ResponseListaProveedor;
import com.SeyaCloudGestion.GestionSistema.feacture.proveedores.domain.services.ProveedoresService;
import org.springframework.stereotype.Component;

@Component
public class ListaProveedorUseCase {
    private final ProveedoresService proveedoresService;

    public ListaProveedorUseCase(
            ProveedoresService proveedoresService
    ){
        this.proveedoresService = proveedoresService;
    }

    public ResponseListaProveedor ListaProveedores(RequestListaProveedor request) {
        try {
            ResponseListaProveedor response = proveedoresService.listaProveedores(request);
            if(response.isExito()){}

            return response;

        } catch (IllegalArgumentException | SecurityException e){
            ResponseListaProveedor response = new ResponseListaProveedor();
            response.setExito(false);
            response.setMessage(e.getMessage());
            response.setProveedores(java.util.List.of());
            return response;
        } catch (Exception e){
            String mensajeError = "Error inesperado al listar los proveedores: " + e.getMessage();
            System.err.println("[ERROR] " + mensajeError);
            ResponseListaProveedor response = new ResponseListaProveedor();
            response.setExito(false);
            response.setMessage(mensajeError);
            response.setProveedores(java.util.List.of());
            return response;
        }
    }
}