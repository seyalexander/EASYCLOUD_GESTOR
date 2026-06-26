package com.SeyaCloudGestion.GestionSistema.feacture.comprobantes.application.useCase;

import com.SeyaCloudGestion.GestionSistema.feacture.comprobantes.application.dto.request.RequestRegistroComprobante;
import com.SeyaCloudGestion.GestionSistema.feacture.comprobantes.application.dto.response.ResponseRegistroComprobante;
import com.SeyaCloudGestion.GestionSistema.feacture.comprobantes.domain.services.ComprobanteService;
import com.SeyaCloudGestion.GestionSistema.feacture.serieDocumento.application.dto.response.ResponseDetalleSerieDocumento;
import com.SeyaCloudGestion.GestionSistema.feacture.serieDocumento.application.dto.response.ResponseObtenerCorrelativo;
import com.SeyaCloudGestion.GestionSistema.feacture.serieDocumento.application.useCase.DetalleSerieDocumentoUseCase;
import com.SeyaCloudGestion.GestionSistema.feacture.serieDocumento.application.useCase.ObtenerCorrelativoUseCase;
import com.SeyaCloudGestion.GestionSistema.feacture.tipoDocumentos.application.useCase.DetalleTipoDocumentoUseCase;
import com.SeyaCloudGestion.GestionSistema.feacture.tipocomprobante.application.dto.response.ResponseDetalleTipoComprobante;
import com.SeyaCloudGestion.GestionSistema.feacture.tipocomprobante.application.useCase.DetalleTipoComprobanteUseCase;
import com.SeyaCloudGestion.GestionSistema.feacture.venta.application.dto.response.ResponseDetalleVenta;
import com.SeyaCloudGestion.GestionSistema.feacture.venta.application.useCase.DetalleVentaUseCase;
import org.springframework.stereotype.Component;

@Component
public class RegistroComprobanteUseCase {

    private final ComprobanteService comprobanteService;
    private final DetalleVentaUseCase detalleVentaUseCase;
    private final DetalleTipoComprobanteUseCase detalleTipoComprobanteUseCase;
    private final DetalleSerieDocumentoUseCase detalleSerieDocumentoUseCase;
    private final ObtenerCorrelativoUseCase obtenerCorrelativoUseCase;

    public RegistroComprobanteUseCase(ComprobanteService comprobanteService, DetalleVentaUseCase detalleVentaUseCase, DetalleTipoComprobanteUseCase detalleTipoComprobanteUseCase, DetalleSerieDocumentoUseCase detalleSerieDocumentoUseCase, ObtenerCorrelativoUseCase obtenerCorrelativoUseCase) {
        this.comprobanteService = comprobanteService;
        this.detalleVentaUseCase = detalleVentaUseCase;
        this.detalleTipoComprobanteUseCase = detalleTipoComprobanteUseCase;
        this.detalleSerieDocumentoUseCase = detalleSerieDocumentoUseCase;
        this.obtenerCorrelativoUseCase = obtenerCorrelativoUseCase;
    }

    public ResponseRegistroComprobante registrarComprobante(RequestRegistroComprobante request) {
        try {
            //get id venta
            ResponseDetalleVenta responseBDVenta = detalleVentaUseCase.DetalleVenta(request.getIdVenta());
            if (!responseBDVenta.isExito() || responseBDVenta.getVenta() == null) {
                throw new IllegalArgumentException("La venta no existe.");
            }
            //get id tipo documento (factura o voleta)
            ResponseDetalleTipoComprobante responseBDTipoComprobante = detalleTipoComprobanteUseCase.DetalleTipoComprobante(request.getIdTipoComprobante());
            if (!responseBDTipoComprobante.isExito() || responseBDTipoComprobante.getTipoCompobante() == null) {
                throw new IllegalArgumentException("El tipo de comprobante no existe.");
            }
            //get serie
            ResponseDetalleSerieDocumento responseBDSerie = detalleSerieDocumentoUseCase.DetalleSerieDocumento(request.getIdSerieDocumento());
            if (!responseBDSerie.isExito() || responseBDSerie.getSerieDocumento() == null) {
                throw new IllegalArgumentException("La serie no existe.");
            }
            //obetener el correlativo
            ResponseObtenerCorrelativo responseBDcorrelativo = obtenerCorrelativoUseCase.ObtenerSiguienteNumero(responseBDSerie.getSerieDocumento().getIdSerieDocumento());
            if (!responseBDcorrelativo.isExito()) {
                throw new RuntimeException("Error al reservar el correlativo: " + responseBDcorrelativo.getMessage());
            }
            String numero=responseBDcorrelativo.getCorrelativo();
            //nulos por ahora
            String urlXml="";
            String urlPdf="";
            ResponseRegistroComprobante response = comprobanteService.RegistroComprobante(request, urlXml, urlPdf,numero);

            if (response.isExito()) {

            }

            return response;

        } catch (IllegalArgumentException | SecurityException e) {
            ResponseRegistroComprobante response = new ResponseRegistroComprobante();
            response.setExito(false);
            response.setMessage(e.getMessage());
            return response;
        } catch (Exception e) {
            String mensajeError = "Error inesperado al registrar el comprobante: " + e.getMessage();
            System.err.println("[ERROR] " + mensajeError);
            ResponseRegistroComprobante response = new ResponseRegistroComprobante();
            response.setExito(false);
            response.setMessage(mensajeError);
            return response;
        }
    }
}