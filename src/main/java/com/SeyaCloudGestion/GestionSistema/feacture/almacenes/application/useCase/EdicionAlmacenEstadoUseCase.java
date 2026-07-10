package com.SeyaCloudGestion.GestionSistema.feacture.almacenes.application.useCase;

import com.SeyaCloudGestion.GestionSistema.common.exceptions.ResourceNotFoundException;
import com.SeyaCloudGestion.GestionSistema.feacture.almacenes.application.dto.request.RequestDetalleAlmacen;
import com.SeyaCloudGestion.GestionSistema.feacture.almacenes.application.dto.request.RequestEditarEstadoAlmacen;
import com.SeyaCloudGestion.GestionSistema.feacture.almacenes.application.dto.response.ResponseDetalleAlmacen;
import com.SeyaCloudGestion.GestionSistema.feacture.almacenes.application.dto.response.ResponseEditarEstadoAlmacen;
import com.SeyaCloudGestion.GestionSistema.feacture.almacenes.domain.services.AlmacenService;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class EdicionAlmacenEstadoUseCase {
    private final AlmacenService almacenesService;

    public EdicionAlmacenEstadoUseCase(AlmacenService almacenesService) {
        this.almacenesService = almacenesService;
    }
    public ResponseEditarEstadoAlmacen AnularAlmacenes(long idAlmacenes) {
        try {
            RequestDetalleAlmacen requestDetalle = new RequestDetalleAlmacen();
            requestDetalle.setIdAlmacen(idAlmacenes);

            ResponseDetalleAlmacen detalleBD= almacenesService.DetalleAlmacen(requestDetalle);

            if (!detalleBD.isExito() || detalleBD.getAlmacen() == null) {
                throw new ResourceNotFoundException("El Id no existe.");
            }

            if (Objects.equals(detalleBD.getAlmacen().getEstado(), 0)) {
                throw new IllegalArgumentException("El almacen ya se encuentra anulado.");
            }

            RequestEditarEstadoAlmacen request = new RequestEditarEstadoAlmacen();
            request.setIdAlmacen(idAlmacenes);

            ResponseEditarEstadoAlmacen response = almacenesService.EditarEstadoAlmacen(request, 0);

            if (response.isExito()) {
            }

            return response;

        } catch (IllegalArgumentException | SecurityException e) {
            ResponseEditarEstadoAlmacen response = new ResponseEditarEstadoAlmacen();
            response.setExito(false);
            response.setMessage(e.getMessage());
            return response;

        } catch (Exception e) {
            String mensajeError = "Error inesperado al actualizar el almacén: " + e.getMessage();
            System.err.println("[ERROR] " + mensajeError);

            ResponseEditarEstadoAlmacen response = new ResponseEditarEstadoAlmacen();
            response.setExito(false);
            response.setMessage(mensajeError);
            return response;
        }
    }

    public ResponseEditarEstadoAlmacen ActivarAlmacenes(long idAlmacenes) {
        try {
            RequestDetalleAlmacen requestDetalle = new RequestDetalleAlmacen();
            requestDetalle.setIdAlmacen(idAlmacenes);

            ResponseDetalleAlmacen detalleBD= almacenesService.DetalleAlmacen(requestDetalle);

            if (!detalleBD.isExito() || detalleBD.getAlmacen() == null) {
                throw new ResourceNotFoundException("El Id no existe.");
            }

            if (Objects.equals(detalleBD.getAlmacen().getEstado(), 1)) {
                throw new IllegalArgumentException("El almacen ya se encuentra activado.");
            }

            RequestEditarEstadoAlmacen request = new RequestEditarEstadoAlmacen();
            request.setIdAlmacen(idAlmacenes);

            ResponseEditarEstadoAlmacen response = almacenesService.EditarEstadoAlmacen(request, 1);

            if (response.isExito()) {
            }

            return response;

        } catch (IllegalArgumentException | SecurityException e) {
            ResponseEditarEstadoAlmacen response = new ResponseEditarEstadoAlmacen();
            response.setExito(false);
            response.setMessage(e.getMessage());
            return response;

        } catch (Exception e) {
            String mensajeError = "Error inesperado al actualizar el almacén: " + e.getMessage();
            System.err.println("[ERROR] " + mensajeError);

            ResponseEditarEstadoAlmacen response = new ResponseEditarEstadoAlmacen();
            response.setExito(false);
            response.setMessage(mensajeError);
            return response;
        }
    }
}