package com.SeyaCloudGestion.GestionSistema.feacture.almacenes.application.useCase;

import com.SeyaCloudGestion.GestionSistema.common.exceptions.ResourceNotFoundException;
import com.SeyaCloudGestion.GestionSistema.feacture.almacenes.application.dto.request.RequestDetalleAlmacenes;
import com.SeyaCloudGestion.GestionSistema.feacture.almacenes.application.dto.request.RequestEditarEstadoAlmacenes;
import com.SeyaCloudGestion.GestionSistema.feacture.almacenes.application.dto.response.ResponseDetalleAlmacenes;
import com.SeyaCloudGestion.GestionSistema.feacture.almacenes.application.dto.response.ResponseEditarEstadoAlmacenes;
import com.SeyaCloudGestion.GestionSistema.feacture.almacenes.domain.services.AlmacenesService;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class EdicionAlmacenesEstadoUseCase {
    private final AlmacenesService almacenesService;

    public EdicionAlmacenesEstadoUseCase(AlmacenesService almacenesService) {
        this.almacenesService = almacenesService;
    }
    public ResponseEditarEstadoAlmacenes AnularAlmacenes(long idAlmacenes) {
        try {
            RequestDetalleAlmacenes requestDetalle = new RequestDetalleAlmacenes();
            requestDetalle.setIdAlmacenes(idAlmacenes);

            ResponseDetalleAlmacenes detalleBD= almacenesService.DetalleAlmacenes(requestDetalle);

            if (!detalleBD.isExito() || detalleBD.getAlmacenes() == null) {
                throw new ResourceNotFoundException("El Id no existe.");
            }

            if (Objects.equals(detalleBD.getAlmacenes().getEstado(), 0)) {
                throw new IllegalArgumentException("El almacen ya se encuentra anulado.");
            }

            RequestEditarEstadoAlmacenes request = new RequestEditarEstadoAlmacenes();
            request.setIdAlmacenes(idAlmacenes);

            ResponseEditarEstadoAlmacenes response = almacenesService.EditarEstadoAlmacenes(request, 0);

            if (response.isExito()) {
            }

            return response;

        } catch (IllegalArgumentException | SecurityException e) {
            ResponseEditarEstadoAlmacenes response = new ResponseEditarEstadoAlmacenes();
            response.setExito(false);
            response.setMessage(e.getMessage());
            return response;

        } catch (Exception e) {
            String mensajeError = "Error inesperado al actualizar el almacén: " + e.getMessage();
            System.err.println("[ERROR] " + mensajeError);

            ResponseEditarEstadoAlmacenes response = new ResponseEditarEstadoAlmacenes();
            response.setExito(false);
            response.setMessage(mensajeError);
            return response;
        }
    }

    public ResponseEditarEstadoAlmacenes ActivarAlmacenes(long idAlmacenes) {
        try {
            RequestDetalleAlmacenes requestDetalle = new RequestDetalleAlmacenes();
            requestDetalle.setIdAlmacenes(idAlmacenes);

            ResponseDetalleAlmacenes detalleBD= almacenesService.DetalleAlmacenes(requestDetalle);

            if (!detalleBD.isExito() || detalleBD.getAlmacenes() == null) {
                throw new ResourceNotFoundException("El Id no existe.");
            }

            if (Objects.equals(detalleBD.getAlmacenes().getEstado(), 1)) {
                throw new IllegalArgumentException("El almacen ya se encuentra activado.");
            }

            RequestEditarEstadoAlmacenes request = new RequestEditarEstadoAlmacenes();
            request.setIdAlmacenes(idAlmacenes);

            ResponseEditarEstadoAlmacenes response = almacenesService.EditarEstadoAlmacenes(request, 1);

            if (response.isExito()) {
            }

            return response;

        } catch (IllegalArgumentException | SecurityException e) {
            ResponseEditarEstadoAlmacenes response = new ResponseEditarEstadoAlmacenes();
            response.setExito(false);
            response.setMessage(e.getMessage());
            return response;

        } catch (Exception e) {
            String mensajeError = "Error inesperado al actualizar el almacén: " + e.getMessage();
            System.err.println("[ERROR] " + mensajeError);

            ResponseEditarEstadoAlmacenes response = new ResponseEditarEstadoAlmacenes();
            response.setExito(false);
            response.setMessage(mensajeError);
            return response;
        }
    }
}