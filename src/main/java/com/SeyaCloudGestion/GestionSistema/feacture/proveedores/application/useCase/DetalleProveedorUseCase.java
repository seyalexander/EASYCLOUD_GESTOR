package com.SeyaCloudGestion.GestionSistema.feacture.proveedores.application.useCase;

import com.SeyaCloudGestion.GestionSistema.feacture.proveedores.application.dto.request.RequestDetalleProveedor;
import com.SeyaCloudGestion.GestionSistema.feacture.proveedores.application.dto.response.ResponseDetalleProveedor;
import com.SeyaCloudGestion.GestionSistema.feacture.proveedores.domain.services.ProveedoresService;
import org.springframework.stereotype.Component;

@Component
public class DetalleProveedorUseCase {

    private final ProveedoresService proveedoresService;

    public DetalleProveedorUseCase(
            ProveedoresService proveedoresService
    ){
        this.proveedoresService = proveedoresService;
    }

    public ResponseDetalleProveedor DetalleProveedores(long idProveedor) {
        try {
            RequestDetalleProveedor request = new RequestDetalleProveedor();
            request.setIdProveedor(idProveedor);

            ResponseDetalleProveedor response = proveedoresService.DetalleProveedores(request);
            if(response.isExito()){}

            return response;

        } catch (IllegalArgumentException | SecurityException e){
            ResponseDetalleProveedor response = new ResponseDetalleProveedor();
            response.setExito(false);
            response.setMessage(e.getMessage());
            return response;
        } catch (Exception e){
            String mensajeError = "Error inesperado al obtener el detalle del proveedor: " + e.getMessage();
            System.err.println("[ERROR] " + mensajeError);
            ResponseDetalleProveedor response = new ResponseDetalleProveedor();
            response.setExito(false);
            response.setMessage(mensajeError);
            return response;
        }
    }
}