package com.SeyaCloudGestion.GestionSistema.feacture.tipoDocumentos.application.useCase;

import com.SeyaCloudGestion.GestionSistema.common.exceptions.ResourceNotFoundException;
import com.SeyaCloudGestion.GestionSistema.feacture.tipoDocumentos.application.dto.request.RequestDetalleTipoDocumento;
import com.SeyaCloudGestion.GestionSistema.feacture.tipoDocumentos.application.dto.request.RequestEditarAllTipoDocumento;
import com.SeyaCloudGestion.GestionSistema.feacture.tipoDocumentos.application.dto.request.RequestEditarEstadoTipoDocumento;
import com.SeyaCloudGestion.GestionSistema.feacture.tipoDocumentos.application.dto.response.ResponseDetalleTipoDocumento;
import com.SeyaCloudGestion.GestionSistema.feacture.tipoDocumentos.application.dto.response.ResponseEditarAllTipoDocumento;
import com.SeyaCloudGestion.GestionSistema.feacture.tipoDocumentos.application.dto.response.ResponseEditarEstadoTipoDocumento;
import com.SeyaCloudGestion.GestionSistema.feacture.tipoDocumentos.domain.services.TipoDocumentoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Objects;


@Component
public class EdicionEstadoTipoDocumentoUseCase {
    private final TipoDocumentoService tipoDocumentoService;

    public EdicionEstadoTipoDocumentoUseCase(
            TipoDocumentoService tipoDocumentoService
    ){
        this.tipoDocumentoService = tipoDocumentoService;
    }

    public ResponseEditarEstadoTipoDocumento AnularTipoDocumento(long idTipoDocumentos) {
        try {
            RequestDetalleTipoDocumento requestDetalle = new RequestDetalleTipoDocumento();
            requestDetalle.setIdTipoDocumentos(idTipoDocumentos);

            ResponseDetalleTipoDocumento detalleBD= tipoDocumentoService.DetalleTipoDocumento(requestDetalle);

            if (!detalleBD.isExito() || detalleBD.getTipoDocumento() == null) {
                throw new ResourceNotFoundException("El Id no existe.");
            }

            if (Objects.equals(detalleBD.getTipoDocumento().getEstado(), 0)) {
                throw new IllegalArgumentException("El tipo documento ya se encuentra anulado.");
            }

            RequestEditarEstadoTipoDocumento request  = new RequestEditarEstadoTipoDocumento();
            request.setIdTipoDocumento(idTipoDocumentos);

            // VALIDACIÓN DE CAMPOS
            if (request == null) {
                String mensajeError = "No se encontró datos para editar";
                throw new IllegalArgumentException(mensajeError);
            }

            if (request.getIdTipoDocumento() == 0) {
                String mensajeError = "No se está enviando correctamente el código del tipo de documento";
                throw new IllegalArgumentException(mensajeError);
            }


            //long userId = SecurityUtils.getCurrentUserId();
            long userId = 1L;

            ResponseEditarEstadoTipoDocumento response = tipoDocumentoService.EditarEstadoTipoDocumento(request, 0, userId);
            if(response.isExito()){

            }
            return response;

        }catch (IllegalArgumentException | SecurityException e){
            ResponseEditarEstadoTipoDocumento response = new ResponseEditarEstadoTipoDocumento();
            response.setExito(false);
            response.setMessage(e.getMessage());
            return response;
        }
        catch (Exception e){
            String mensajeError = "Error inesperado al editar el tipos de documento: " + e.getMessage();
            System.err.println("[ERROR] " + mensajeError);
            ResponseEditarEstadoTipoDocumento response = new ResponseEditarEstadoTipoDocumento();
            response.setExito(false);
            response.setMessage(mensajeError);
            return response;
        }
    }

    public ResponseEditarEstadoTipoDocumento ActivarTipoDocumento(long idTipoDocumentos) {
        try {
            RequestDetalleTipoDocumento requestDetalle = new RequestDetalleTipoDocumento();
            requestDetalle.setIdTipoDocumentos(idTipoDocumentos);

            ResponseDetalleTipoDocumento detalleBD= tipoDocumentoService.DetalleTipoDocumento(requestDetalle);

            if (!detalleBD.isExito() || detalleBD.getTipoDocumento() == null) {
                throw new ResourceNotFoundException("El Id no existe.");
            }

            if (Objects.equals(detalleBD.getTipoDocumento().getEstado(), 1)) {
                throw new IllegalArgumentException("El tipo documento ya se encuentra activado.");
            }

            RequestEditarEstadoTipoDocumento request  = new RequestEditarEstadoTipoDocumento();
            request.setIdTipoDocumento(idTipoDocumentos);

            // VALIDACIÓN DE CAMPOS
            if (request == null) {
                String mensajeError = "No se encontró datos para editar";
                throw new IllegalArgumentException(mensajeError);
            }

            if (request.getIdTipoDocumento() == 0) {
                String mensajeError = "No se está enviando correctamente el código del tipo de documento";
                throw new IllegalArgumentException(mensajeError);
            }


            //long userId = SecurityUtils.getCurrentUserId();
            long userId = 1L;

            ResponseEditarEstadoTipoDocumento response = tipoDocumentoService.EditarEstadoTipoDocumento(request, 1, userId);
            if(response.isExito()){

            }
            return response;

        }catch (IllegalArgumentException | SecurityException e){
            ResponseEditarEstadoTipoDocumento response = new ResponseEditarEstadoTipoDocumento();
            response.setExito(false);
            response.setMessage(e.getMessage());
            return response;
        }
        catch (Exception e){
            String mensajeError = "Error inesperado al editar el tipos de documento: " + e.getMessage();
            System.err.println("[ERROR] " + mensajeError);
            ResponseEditarEstadoTipoDocumento response = new ResponseEditarEstadoTipoDocumento();
            response.setExito(false);
            response.setMessage(mensajeError);
            return response;
        }
    }
}
