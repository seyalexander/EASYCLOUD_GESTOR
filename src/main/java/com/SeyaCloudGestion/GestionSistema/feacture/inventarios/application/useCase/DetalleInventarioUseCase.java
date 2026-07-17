package com.SeyaCloudGestion.GestionSistema.feacture.inventarios.application.useCase;

import com.SeyaCloudGestion.GestionSistema.feacture.inventarios.application.dto.request.RequestDetalleInventario;
import com.SeyaCloudGestion.GestionSistema.feacture.inventarios.application.dto.response.ResponseDetalleInventario;
import com.SeyaCloudGestion.GestionSistema.feacture.inventarios.domain.services.InventarioService;
import org.springframework.stereotype.Component;

@Component
public class DetalleInventarioUseCase {

    private final InventarioService inventarioService;

    public DetalleInventarioUseCase(
            InventarioService inventarioService
    ) {
        this.inventarioService = inventarioService;
    }

    public ResponseDetalleInventario DetalleInventario(long idInventario) {
        try {
            RequestDetalleInventario request = new RequestDetalleInventario();
            request.setIdInventario(idInventario);

            ResponseDetalleInventario response = inventarioService.DetalleInventario(request);
            if (response.isExito()) {
            }

            return response;

        } catch (IllegalArgumentException | SecurityException e) {
            ResponseDetalleInventario response = new ResponseDetalleInventario();
            response.setExito(false);
            response.setMessage(e.getMessage());
            return response;
        } catch (Exception e) {
            String mensajeError = "Error inesperado al obtener el detalle del inventario: " + e.getMessage();
            System.err.println("[ERROR] " + mensajeError);
            ResponseDetalleInventario response = new ResponseDetalleInventario();
            response.setExito(false);
            response.setMessage(mensajeError);
            return response;
        }
    }
}