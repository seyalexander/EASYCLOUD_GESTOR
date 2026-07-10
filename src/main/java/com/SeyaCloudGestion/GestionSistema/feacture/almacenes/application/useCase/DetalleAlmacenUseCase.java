package com.SeyaCloudGestion.GestionSistema.feacture.almacenes.application.useCase;

import com.SeyaCloudGestion.GestionSistema.feacture.almacenes.application.dto.request.RequestDetalleAlmacen;
import com.SeyaCloudGestion.GestionSistema.feacture.almacenes.application.dto.response.ResponseDetalleAlmacen;
import com.SeyaCloudGestion.GestionSistema.feacture.almacenes.domain.services.AlmacenService;
import org.springframework.stereotype.Component;

@Component
public class DetalleAlmacenUseCase {
    private final AlmacenService almacenesService;

    public DetalleAlmacenUseCase(AlmacenService almacenesService) {
        this.almacenesService = almacenesService;
    }
    public ResponseDetalleAlmacen DetalleAlmacenes(long idAlmacenes) {
        try {
            RequestDetalleAlmacen request = new RequestDetalleAlmacen();
            request.setIdAlmacen(idAlmacenes);

            ResponseDetalleAlmacen response = almacenesService.DetalleAlmacen(request);

            if (response.isExito()) {
            }

            return response;

        } catch (IllegalArgumentException | SecurityException e) {
            ResponseDetalleAlmacen response = new ResponseDetalleAlmacen();
            response.setExito(false);
            response.setMessage(e.getMessage());
            return response;

        } catch (Exception e) {
            String mensajeError = "Error inesperado al ver el detalle del almacén: " + e.getMessage();
            System.err.println("[ERROR] " + mensajeError);

            ResponseDetalleAlmacen response = new ResponseDetalleAlmacen();
            response.setExito(false);
            response.setMessage(mensajeError);
            return response;
        }
    }
}