package com.SeyaCloudGestion.GestionSistema.feacture.caja.application.useCase;

import com.SeyaCloudGestion.GestionSistema.common.exceptions.ResourceNotFoundException;
import com.SeyaCloudGestion.GestionSistema.feacture.caja.application.dto.request.RequestRegistroCaja;
import com.SeyaCloudGestion.GestionSistema.feacture.caja.application.dto.request.RequestRegistroSerieCaja;
import com.SeyaCloudGestion.GestionSistema.feacture.caja.application.dto.response.ResponseDetalleCaja;
import com.SeyaCloudGestion.GestionSistema.feacture.caja.application.dto.response.ResponseRegistroCaja;
import com.SeyaCloudGestion.GestionSistema.feacture.caja.application.dto.response.ResponseRegistroSerieCaja;
import com.SeyaCloudGestion.GestionSistema.feacture.caja.domain.services.CajaService;
import com.SeyaCloudGestion.GestionSistema.feacture.serieDocumento.application.dto.request.RequestDetalleSeries;
import com.SeyaCloudGestion.GestionSistema.feacture.serieDocumento.application.dto.response.ResponseDetalleSerieDocumento;
import com.SeyaCloudGestion.GestionSistema.feacture.serieDocumento.domain.services.SerieDocumentoService;
import org.springframework.stereotype.Component;

@Component
public class RegistroSerieCajaUseCase {

    private final CajaService cajaService;
    private final SerieDocumentoService serieDocumentoService;
    private final DetalleCajaUseCase detalleCajaUseCase;
    public RegistroSerieCajaUseCase(CajaService cajaService, SerieDocumentoService serieDocumentoService, DetalleCajaUseCase detalleCajaUseCase) {
        this.cajaService = cajaService;
        this.serieDocumentoService = serieDocumentoService;
        this.detalleCajaUseCase = detalleCajaUseCase;
    }

    public ResponseRegistroSerieCaja RegistroSerieCaja(RequestRegistroSerieCaja request) {
        try {
            //get id caja
            ResponseDetalleCaja detalleBDCaja= detalleCajaUseCase.DetalleCaja(request.getIdCaja());

            if (!detalleBDCaja.isExito() || detalleBDCaja.getCaja() == null) {
                throw new ResourceNotFoundException("El Id de la caja no existe.");
            }

            //get id serie
            RequestDetalleSeries requestDetalle = new RequestDetalleSeries();
            requestDetalle.setIdSeries(request.getIdSerieDocumento());

            ResponseDetalleSerieDocumento detalleBD= serieDocumentoService.DetalleSerieDocumento(requestDetalle);

            if (!detalleBD.isExito() || detalleBD.getSerieDocumento() == null) {
                throw new ResourceNotFoundException("El Id de la serie no existe.");
            }

            ResponseRegistroSerieCaja response = cajaService.RegistroSerieCaja(request);

            if (response.isExito()) {
            }

            return response;

        } catch (IllegalArgumentException | SecurityException e) {
            ResponseRegistroSerieCaja response = new ResponseRegistroSerieCaja();
            response.setExito(false);
            response.setMessage(e.getMessage());
            return response;
        } catch (Exception e) {
            String mensajeError = "Error inesperado al registrar la serie caja: " + e.getMessage();
            System.err.println("[ERROR] " + mensajeError);

            ResponseRegistroSerieCaja response = new ResponseRegistroSerieCaja();
            response.setExito(false);
            response.setMessage(mensajeError);
            return response;
        }
    }
}