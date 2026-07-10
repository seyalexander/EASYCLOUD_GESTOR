package com.SeyaCloudGestion.GestionSistema.feacture.almacenes.application.useCase;

import com.SeyaCloudGestion.GestionSistema.common.exceptions.ResourceNotFoundException;
import com.SeyaCloudGestion.GestionSistema.feacture.almacenes.application.dto.request.RequestDetalleAlmacen;
import com.SeyaCloudGestion.GestionSistema.feacture.almacenes.application.dto.request.RequestEditarAllAlmacen;
import com.SeyaCloudGestion.GestionSistema.feacture.almacenes.application.dto.response.ResponseDetalleAlmacen;
import com.SeyaCloudGestion.GestionSistema.feacture.almacenes.application.dto.response.ResponseEditarAllAlmacen;
import com.SeyaCloudGestion.GestionSistema.feacture.almacenes.domain.services.AlmacenService;
import com.SeyaCloudGestion.GestionSistema.feacture.almacenes.domain.validations.VerificarCambiosAlmacen;
import org.springframework.stereotype.Component;

@Component
public class EdicionAllAlmacenUseCase {
    private final AlmacenService almacenesService;
    private final VerificarCambiosAlmacen verificarCambios;
    public EdicionAllAlmacenUseCase(AlmacenService almacenesService, VerificarCambiosAlmacen verificarCambios) {
        this.almacenesService = almacenesService;
        this.verificarCambios = verificarCambios;
    }
    public ResponseEditarAllAlmacen EdicionAllAlmacenes(RequestEditarAllAlmacen request) {
        try {
            RequestDetalleAlmacen requestDetalle = new RequestDetalleAlmacen();
            requestDetalle.setIdAlmacen(request.getIdAlmacen());

            ResponseDetalleAlmacen detalleBD= almacenesService.DetalleAlmacen(requestDetalle);

            if (!detalleBD.isExito() || detalleBD.getAlmacen() == null) {
                throw new ResourceNotFoundException("El Id no existe.");
            }
            //verificar cambios
            if (!verificarCambios.verificarCambios(detalleBD.getAlmacen(), request)) {
                throw new ResourceNotFoundException("No se detectaron cambios para actualizar.");
            }

            ResponseEditarAllAlmacen response = almacenesService.EditarAllAlmacen(request);

            if (response.isExito()) {
            }

            return response;

        } catch (IllegalArgumentException | SecurityException e) {
            ResponseEditarAllAlmacen response = new ResponseEditarAllAlmacen();
            response.setExito(false);
            response.setMessage(e.getMessage());
            return response;

        } catch (Exception e) {
            String mensajeError = "Error inesperado al actualizar el almacén: " + e.getMessage();
            System.err.println("[ERROR] " + mensajeError);

            ResponseEditarAllAlmacen response = new ResponseEditarAllAlmacen();
            response.setExito(false);
            response.setMessage(mensajeError);
            return response;
        }
    }
}