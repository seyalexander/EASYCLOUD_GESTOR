package com.SeyaCloudGestion.GestionSistema.feacture.serieDocumento.application.useCase;

import com.SeyaCloudGestion.GestionSistema.feacture.serieDocumento.application.dto.request.RequestDetalleSeries;
import com.SeyaCloudGestion.GestionSistema.feacture.serieDocumento.application.dto.request.RequestObtenerCorrelativo;
import com.SeyaCloudGestion.GestionSistema.feacture.serieDocumento.application.dto.response.ResponseDetalleSerieDocumento;
import com.SeyaCloudGestion.GestionSistema.feacture.serieDocumento.application.dto.response.ResponseObtenerCorrelativo;
import com.SeyaCloudGestion.GestionSistema.feacture.serieDocumento.domain.services.SerieDocumentoService;
import org.springframework.stereotype.Component;

@Component
public class ObtenerCorrelativoUseCase {
    private final SerieDocumentoService serieDocumentoService;
    private final DetalleSerieDocumentoUseCase detalleSerieDocumentoUseCase;

    public ObtenerCorrelativoUseCase(SerieDocumentoService serieDocumentoService, DetalleSerieDocumentoUseCase detalleSerieDocumentoUseCase) {
        this.serieDocumentoService = serieDocumentoService;
        this.detalleSerieDocumentoUseCase = detalleSerieDocumentoUseCase;
    }

    public ResponseObtenerCorrelativo ObtenerSiguienteNumero(long idSerieDocumento) {
        try {
            //get id
            ResponseDetalleSerieDocumento responseBD = detalleSerieDocumentoUseCase.DetalleSerieDocumento(idSerieDocumento);
            if (!responseBD.isExito() || responseBD.getSerieDocumento() == null) {
                throw new IllegalArgumentException("La serie no existe.");
            }

            RequestObtenerCorrelativo request = new RequestObtenerCorrelativo();
            request.setIdSerieDocumento(idSerieDocumento);

            ResponseObtenerCorrelativo response = serieDocumentoService.ObtenerCorelativo(request);

            if (response.isExito()) {
            }

            return response;

        } catch (IllegalArgumentException | SecurityException e) {
            ResponseObtenerCorrelativo response = new ResponseObtenerCorrelativo();
            response.setExito(false);
            response.setMessage(e.getMessage());
            return response;
        } catch (Exception e) {
            String mensajeError = "Error inesperado al generar el siguiente correlativo: " + e.getMessage();
            System.err.println("[ERROR] " + mensajeError);
            ResponseObtenerCorrelativo response = new ResponseObtenerCorrelativo();
            response.setExito(false);
            response.setMessage(mensajeError);
            return response;
        }
    }
}