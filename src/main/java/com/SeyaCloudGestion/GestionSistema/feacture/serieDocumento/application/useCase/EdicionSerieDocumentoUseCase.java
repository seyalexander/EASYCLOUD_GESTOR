package com.SeyaCloudGestion.GestionSistema.feacture.serieDocumento.application.useCase;

import com.SeyaCloudGestion.GestionSistema.common.exceptions.ResourceNotFoundException;
import com.SeyaCloudGestion.GestionSistema.feacture.serieDocumento.application.dto.request.RequestDetalleSeries;
import com.SeyaCloudGestion.GestionSistema.feacture.serieDocumento.application.dto.request.RequestEditarAllSeries;
import com.SeyaCloudGestion.GestionSistema.feacture.serieDocumento.application.dto.response.ResponseDetalleSerieDocumento;
import com.SeyaCloudGestion.GestionSistema.feacture.serieDocumento.application.dto.response.ResponseEditarAllSerieDocumento;
import com.SeyaCloudGestion.GestionSistema.feacture.serieDocumento.domain.services.SerieDocumentoService;
import com.SeyaCloudGestion.GestionSistema.feacture.serieDocumento.domain.validations.VerificarCambiosSerieDocumento;
import org.springframework.stereotype.Component;

@Component
public class EdicionSerieDocumentoUseCase {

    private final SerieDocumentoService serieDocumentoService;
    private final VerificarCambiosSerieDocumento verificarCambios;

    public EdicionSerieDocumentoUseCase(SerieDocumentoService serieDocumentoService, VerificarCambiosSerieDocumento verificarCambios) {
        this.serieDocumentoService = serieDocumentoService;
        this.verificarCambios = verificarCambios;
    }

    public ResponseEditarAllSerieDocumento EdicionAllSerieDocumento(RequestEditarAllSeries request) {
        try {
            //get it
            RequestDetalleSeries requestDetalle = new RequestDetalleSeries();
            requestDetalle.setIdSeries(request.getIdSerieDocumento());

            ResponseDetalleSerieDocumento detalleBD= serieDocumentoService.DetalleSerieDocumento(requestDetalle);

            if (!detalleBD.isExito() || detalleBD.getSerieDocumento() == null) {
                throw new ResourceNotFoundException("El Id no existe.");
            }
            // verificamos los cmabio s
            //verificar cambios
            if (!verificarCambios.verificarCambios(detalleBD.getSerieDocumento(), request)) {
                throw new ResourceNotFoundException("No se detectaron cambios para actualizar.");
            }

            ResponseEditarAllSerieDocumento response = serieDocumentoService.EditarAllSerieDocumento(request);
            if(response.isExito()){}
            return response;

        }catch (IllegalArgumentException | SecurityException e){
            ResponseEditarAllSerieDocumento response = new ResponseEditarAllSerieDocumento();
            response.setExito(false);
            response.setMessage(e.getMessage());
            return response;
        }
        catch (Exception e){
            String mensajeError = "Error inesperado al actualizar la serie de documento: " + e.getMessage();
            System.err.println("[ERROR] " + mensajeError);
            ResponseEditarAllSerieDocumento response = new ResponseEditarAllSerieDocumento();
            response.setExito(false);
            response.setMessage(mensajeError);
            return response;
        }
    }
}