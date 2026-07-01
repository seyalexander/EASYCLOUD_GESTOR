package com.SeyaCloudGestion.GestionSistema.feacture.proveedores.application.useCase;

import com.SeyaCloudGestion.GestionSistema.feacture.proveedores.application.dto.request.RequestEditarEstadoProveedor;
import com.SeyaCloudGestion.GestionSistema.feacture.proveedores.application.dto.response.ResponseDetalleProveedor;
import com.SeyaCloudGestion.GestionSistema.feacture.proveedores.application.dto.response.ResponseEditarEstadoProveedor;
import com.SeyaCloudGestion.GestionSistema.feacture.proveedores.domain.services.ProveedoresService;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class EdicionProveedorEstadoUseCase {

    private final ProveedoresService proveedoresService;
    private final DetalleProveedorUseCase detalleProveedoresUseCase;

    public EdicionProveedorEstadoUseCase(
            ProveedoresService proveedoresService, DetalleProveedorUseCase detalleProveedoresUseCase
            ) {
        this.proveedoresService = proveedoresService;
        this.detalleProveedoresUseCase = detalleProveedoresUseCase;
    }

    public ResponseEditarEstadoProveedor EdicionAnularProveedor(long idProveedor) {
        try {
            // get id proveedor
            ResponseDetalleProveedor detalleBD = detalleProveedoresUseCase.DetalleProveedores(idProveedor);

            if (!detalleBD.isExito() || detalleBD.getProveedor() == null) {
                throw new IllegalArgumentException("El proveedor no existe.");
            }

            // Verificamos que no tenga el mismo estado (0 = Anulado)
            if (Objects.equals(detalleBD.getProveedor().getEstado(), 0)) {
                throw new IllegalArgumentException("El proveedor ya se encuentra anulado.");
            }

            RequestEditarEstadoProveedor request = new RequestEditarEstadoProveedor();
            request.setIdProveedor(idProveedor);

            ResponseEditarEstadoProveedor response = proveedoresService.EditarEstadoProveedores(request, 0);
            if (response.isExito()) {}

            return response;

        } catch (IllegalArgumentException | SecurityException e) {
            ResponseEditarEstadoProveedor response = new ResponseEditarEstadoProveedor();
            response.setExito(false);
            response.setMessage(e.getMessage());
            return response;
        } catch (Exception e) {
            String mensajeError = "Error inesperado al cambiar el estado del proveedor: " + e.getMessage();
            System.err.println("[ERROR] " + mensajeError);
            ResponseEditarEstadoProveedor response = new ResponseEditarEstadoProveedor();
            response.setExito(false);
            response.setMessage(mensajeError);
            return response;
        }
    }

    public ResponseEditarEstadoProveedor EdicionActivarProveedor(long idProveedor) {
        try {
            // Traemos el proveedor
            ResponseDetalleProveedor detalleBD = detalleProveedoresUseCase.DetalleProveedores(idProveedor);

            if (!detalleBD.isExito() || detalleBD.getProveedor() == null) {
                throw new IllegalArgumentException("El proveedor no existe.");
            }

            // Verificamos que no tenga el mismo estado (1 = Activo)
            if (Objects.equals(detalleBD.getProveedor().getEstado(), 1)) {
                throw new IllegalArgumentException("El proveedor ya se encuentra activado.");
            }

            RequestEditarEstadoProveedor request = new RequestEditarEstadoProveedor();
            request.setIdProveedor(idProveedor);

            ResponseEditarEstadoProveedor response = proveedoresService.EditarEstadoProveedores(request, 1);
            if (response.isExito()) {}

            return response;

        } catch (IllegalArgumentException | SecurityException e) {
            ResponseEditarEstadoProveedor response = new ResponseEditarEstadoProveedor();
            response.setExito(false);
            response.setMessage(e.getMessage());
            return response;
        } catch (Exception e) {
            String mensajeError = "Error inesperado al cambiar el estado del proveedor: " + e.getMessage();
            System.err.println("[ERROR] " + mensajeError);
            ResponseEditarEstadoProveedor response = new ResponseEditarEstadoProveedor();
            response.setExito(false);
            response.setMessage(mensajeError);
            return response;
        }
    }
}