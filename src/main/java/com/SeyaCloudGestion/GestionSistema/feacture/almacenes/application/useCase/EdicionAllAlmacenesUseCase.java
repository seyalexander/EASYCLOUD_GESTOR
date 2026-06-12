package com.SeyaCloudGestion.GestionSistema.feacture.almacenes.application.useCase;

import com.SeyaCloudGestion.GestionSistema.common.exceptions.ResourceNotFoundException;
import com.SeyaCloudGestion.GestionSistema.feacture.almacenes.application.dto.request.RequestDetalleAlmacenes;
import com.SeyaCloudGestion.GestionSistema.feacture.almacenes.application.dto.request.RequestEditarAllAlmacenes;
import com.SeyaCloudGestion.GestionSistema.feacture.almacenes.application.dto.response.ResponseDetalleAlmacenes;
import com.SeyaCloudGestion.GestionSistema.feacture.almacenes.application.dto.response.ResponseEditarAllAlmacenes;
import com.SeyaCloudGestion.GestionSistema.feacture.almacenes.domain.services.AlmacenesService;
import com.SeyaCloudGestion.GestionSistema.feacture.almacenes.domain.validations.VerificarCambiosAlmacen;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class EdicionAllAlmacenesUseCase {
    private final AlmacenesService almacenesService;
    private final VerificarCambiosAlmacen verificarCambios;
    public EdicionAllAlmacenesUseCase(AlmacenesService almacenesService, VerificarCambiosAlmacen verificarCambios) {
        this.almacenesService = almacenesService;
        this.verificarCambios = verificarCambios;
    }
    public ResponseEditarAllAlmacenes EdicionAllAlmacenes(RequestEditarAllAlmacenes request) {
        try {
            RequestDetalleAlmacenes requestDetalle = new RequestDetalleAlmacenes();
            requestDetalle.setIdAlmacenes(request.getIdAlmacenes());

            ResponseDetalleAlmacenes detalleBD= almacenesService.DetalleAlmacenes(requestDetalle);

            if (!detalleBD.isExito() || detalleBD.getAlmacenes() == null) {
                throw new ResourceNotFoundException("El Id no existe.");
            }
            //verificar cambios
            if (!verificarCambios.verificarCambios(detalleBD.getAlmacenes(), request)) {
                throw new ResourceNotFoundException("No se detectaron cambios para actualizar.");
            }

            ResponseEditarAllAlmacenes response = almacenesService.EditarAllAlmacenes(request);

            if (response.isExito()) {
            }

            return response;

        } catch (IllegalArgumentException | SecurityException e) {
            ResponseEditarAllAlmacenes response = new ResponseEditarAllAlmacenes();
            response.setExito(false);
            response.setMessage(e.getMessage());
            return response;

        } catch (Exception e) {
            String mensajeError = "Error inesperado al actualizar el almacén: " + e.getMessage();
            System.err.println("[ERROR] " + mensajeError);

            ResponseEditarAllAlmacenes response = new ResponseEditarAllAlmacenes();
            response.setExito(false);
            response.setMessage(mensajeError);
            return response;
        }
    }
}