package com.SeyaCloudGestion.GestionSistema.feacture.serieDocumento.application.useCase;

import com.SeyaCloudGestion.GestionSistema.common.exceptions.ResourceNotFoundException;
import com.SeyaCloudGestion.GestionSistema.feacture.serieDocumento.application.dto.request.RequestDetalleSeries;
import com.SeyaCloudGestion.GestionSistema.feacture.serieDocumento.application.dto.request.RequestEditarEstadoSeries;
import com.SeyaCloudGestion.GestionSistema.feacture.serieDocumento.application.dto.response.ResponseDetalleSerieDocumento;
import com.SeyaCloudGestion.GestionSistema.feacture.serieDocumento.application.dto.response.ResponseEditarEstadoSerieDocumento;
import com.SeyaCloudGestion.GestionSistema.feacture.serieDocumento.domain.services.SerieDocumentoService;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class EdicionSerieDocumentoEstadoUseCase {
    private final SerieDocumentoService serieDocumentoService;

    public EdicionSerieDocumentoEstadoUseCase(SerieDocumentoService serieDocumentoService) {
        this.serieDocumentoService = serieDocumentoService;
    }
    public ResponseEditarEstadoSerieDocumento AnularSerieDocumento(Long idSerieDocumento) {
        try {
            RequestDetalleSeries requestDetalle = new RequestDetalleSeries();
            requestDetalle.setIdSeries(idSerieDocumento);

            ResponseDetalleSerieDocumento detalleBD= serieDocumentoService.DetalleSerieDocumento(requestDetalle);

            if (!detalleBD.isExito() || detalleBD.getSerieDocumento() == null) {
                throw new ResourceNotFoundException("El Id no existe.");
            }

            if (Objects.equals(detalleBD.getSerieDocumento().getEstado(), 0)) {
                throw new IllegalArgumentException("La serie ya se encuentra anulada.");
            }

            RequestEditarEstadoSeries request = new RequestEditarEstadoSeries();
            request.setIdSeries(idSerieDocumento);
            ResponseEditarEstadoSerieDocumento response = serieDocumentoService.EditarEstadoSerieDocumento(request,0);
            if(response.isExito()){}
            return response;

        }catch (IllegalArgumentException | SecurityException e){
            ResponseEditarEstadoSerieDocumento response = new ResponseEditarEstadoSerieDocumento();
            response.setExito(false);
            response.setMessage(e.getMessage());
            return response;
        }
        catch (Exception e){
            String mensajeError = "Error inesperado al actualizar el estado de la serie de documento: " + e.getMessage();
            System.err.println("[ERROR] " + mensajeError);
            ResponseEditarEstadoSerieDocumento response = new ResponseEditarEstadoSerieDocumento();
            response.setExito(false);
            response.setMessage(mensajeError);
            return response;
        }
    }

    public ResponseEditarEstadoSerieDocumento ActivarSerieDocumento(Long idSerieDocumento) {
        try {
            RequestDetalleSeries requestDetalle = new RequestDetalleSeries();
            requestDetalle.setIdSeries(idSerieDocumento);

            ResponseDetalleSerieDocumento detalleBD= serieDocumentoService.DetalleSerieDocumento(requestDetalle);

            if (!detalleBD.isExito() || detalleBD.getSerieDocumento() == null) {
                throw new ResourceNotFoundException("El Id no existe.");
            }

            if (Objects.equals(detalleBD.getSerieDocumento().getEstado(), 1)) {
                throw new IllegalArgumentException("La serie ya se encuentra activada.");
            }

            RequestEditarEstadoSeries request = new RequestEditarEstadoSeries();
            request.setIdSeries(idSerieDocumento);
            ResponseEditarEstadoSerieDocumento response = serieDocumentoService.EditarEstadoSerieDocumento(request,1);
            if(response.isExito()){}
            return response;

        }catch (IllegalArgumentException | SecurityException e){
            ResponseEditarEstadoSerieDocumento response = new ResponseEditarEstadoSerieDocumento();
            response.setExito(false);
            response.setMessage(e.getMessage());
            return response;
        }
        catch (Exception e){
            String mensajeError = "Error inesperado al actualizar el estado de la serie de documento: " + e.getMessage();
            System.err.println("[ERROR] " + mensajeError);
            ResponseEditarEstadoSerieDocumento response = new ResponseEditarEstadoSerieDocumento();
            response.setExito(false);
            response.setMessage(mensajeError);
            return response;
        }
    }
}