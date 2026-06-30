package com.SeyaCloudGestion.GestionSistema.feacture.comprobantes.application.useCase;

import com.SeyaCloudGestion.GestionSistema.feacture.caja.application.useCase.DetalleCajaUseCase;
import com.SeyaCloudGestion.GestionSistema.feacture.comprobantes.application.dto.request.RequestProcesarRegistroComprobante;
import com.SeyaCloudGestion.GestionSistema.feacture.comprobantes.application.dto.request.RequestRegistroComprobante;
import com.SeyaCloudGestion.GestionSistema.feacture.comprobantes.application.dto.response.ResponseProcesarRegistroComprobante;
import com.SeyaCloudGestion.GestionSistema.feacture.comprobantes.application.dto.response.ResponseRegistroComprobante;
import com.SeyaCloudGestion.GestionSistema.feacture.serieCaja.application.dto.request.RequestListaSerieCaja;
import com.SeyaCloudGestion.GestionSistema.feacture.serieCaja.application.dto.response.ResponseListaSerieCaja;
import com.SeyaCloudGestion.GestionSistema.feacture.serieCaja.application.useCase.ListaSerieCajaUseCase;
import com.SeyaCloudGestion.GestionSistema.feacture.serieCaja.infraestructure.persistence.model.SerieCajaModel;
import com.SeyaCloudGestion.GestionSistema.feacture.tipoDocumentos.application.dto.response.ResponseDetalleTipoDocumento;
import com.SeyaCloudGestion.GestionSistema.feacture.tipoDocumentos.application.useCase.DetalleTipoDocumentoUseCase;
import com.SeyaCloudGestion.GestionSistema.feacture.tipocomprobante.application.dto.response.ResponseDetalleTipoComprobante;
import com.SeyaCloudGestion.GestionSistema.feacture.tipocomprobante.application.useCase.DetalleTipoComprobanteUseCase;
import com.SeyaCloudGestion.GestionSistema.feacture.turnoCaja.application.dto.response.ResponseDetalleTurnoCaja;
import com.SeyaCloudGestion.GestionSistema.feacture.turnoCaja.application.useCase.DetalleTurnoCajaUseCase;
import com.SeyaCloudGestion.GestionSistema.feacture.turnoCaja.infraestructure.persistence.model.EstadoCaja;
import com.SeyaCloudGestion.GestionSistema.feacture.venta.application.dto.response.ResponseDetalleVenta;
import com.SeyaCloudGestion.GestionSistema.feacture.venta.application.useCase.DetalleVentaUseCase;
import org.springframework.stereotype.Component;

@Component
public class ProcesarRegistroComprobanteUseCase {

    private final RegistroComprobanteUseCase registroComprobanteUseCase;
    private final DetalleVentaUseCase detalleVentaUseCase;
    private final DetalleTipoComprobanteUseCase detalleTipoComprobanteUseCase;
    private final DetalleTipoDocumentoUseCase detalleTipoDocumentoUseCase;
    private final ListaSerieCajaUseCase listaSerieCajaUseCase;
    private final DetalleTurnoCajaUseCase detalleTurnoCajaUseCase;

    public ProcesarRegistroComprobanteUseCase(RegistroComprobanteUseCase registroComprobanteUseCase, DetalleVentaUseCase detalleVentaUseCase, DetalleTipoComprobanteUseCase detalleTipoComprobanteUseCase, DetalleTipoDocumentoUseCase detalleTipoDocumentoUseCase, ListaSerieCajaUseCase listaSerieCajaUseCase, DetalleTurnoCajaUseCase detalleTurnoCajaUseCase) {
        this.registroComprobanteUseCase = registroComprobanteUseCase;
        this.detalleVentaUseCase = detalleVentaUseCase;
        this.detalleTipoComprobanteUseCase = detalleTipoComprobanteUseCase;
        this.detalleTipoDocumentoUseCase = detalleTipoDocumentoUseCase;
        this.listaSerieCajaUseCase = listaSerieCajaUseCase;
        this.detalleTurnoCajaUseCase = detalleTurnoCajaUseCase;
    }

    public ResponseProcesarRegistroComprobante procesarRegistro(RequestProcesarRegistroComprobante request) {
        ResponseProcesarRegistroComprobante response = new ResponseProcesarRegistroComprobante();
        try {
            //idVenta
            ResponseDetalleVenta responsBDVenta =detalleVentaUseCase.DetalleVenta(request.getIdVenta());
            if (!responsBDVenta.isExito() || responsBDVenta.getVenta() == null) {
                throw new IllegalArgumentException("La venta no existe.");
            }
            //idcaja
            //turno caja
            ResponseDetalleTurnoCaja detalleBDturnoCaja = detalleTurnoCajaUseCase.DetalleTurnoCaja(request.getIdCaja(), EstadoCaja.ABIERTO);
            if (!detalleBDturnoCaja.isExito() || detalleBDturnoCaja.getTurnoCaja() == null) {
                throw new IllegalArgumentException("El turno caja no existe.");
            }
            //get lista de las series acopladas ala caja
            RequestListaSerieCaja requestListaSerie= new RequestListaSerieCaja();
            requestListaSerie.setIdCaja(request.getIdCaja());
            ResponseListaSerieCaja responseListSeries = listaSerieCajaUseCase.ListaSerieCaja(requestListaSerie);
            if (responseListSeries.getSerieCajas().isEmpty()){
                throw new IllegalArgumentException("No existen series asignadas a esta caja.");
            }
            //get id tipo documento (factura o voleta)
            ResponseDetalleTipoComprobante responseBDTipoComprobante = detalleTipoComprobanteUseCase.DetalleTipoComprobante(request.getIdTipoComprobante());
            if (!responseBDTipoComprobante.isExito() || responseBDTipoComprobante.getTipoCompobante() == null) {
                throw new IllegalArgumentException("El tipo de comprobante no existe.");
            }
            //obetenemos la serie conforme el comprovante
            Long idSerieDocumentoDetectado = null;

            for (SerieCajaModel serie : responseListSeries.getSerieCajas()) {
                // Validamos si el idTipoComprobante de la serie de la caja coincide con el solicitado en la venta
                if (serie.getIdTipoComprobante()==request.getIdTipoComprobante()) {
                    idSerieDocumentoDetectado = serie.getIdSerieDocumento();
                    break;
                }
            }

            if (idSerieDocumentoDetectado == null) {
                throw new IllegalArgumentException("La caja actual no tiene configurada una serie para el tipo de comprobante solicitado.");
            }

            //get id tipoDocumentoIdentidad
            ResponseDetalleTipoDocumento responseBDTipoDocumento = detalleTipoDocumentoUseCase.DetalleTipoDocumento(request.getIdTipoDocumentoCliente());
            if (!responseBDTipoDocumento.isExito() || responseBDTipoDocumento.getTipoDocumento() == null) {
                throw new IllegalArgumentException("El tipo de documento no existe.");
            }
            //validacion codigods
            String codigoComprobante = responseBDTipoComprobante.getTipoCompobante().getCodigoSunat();
            String tipoDocCliente = responseBDTipoDocumento.getTipoDocumento().getCodigoSunat();
            String numDocCliente = request.getNumeroDocumentoCliente();
            //factura
            if ("01".equals(codigoComprobante)) {
                // no se puede factura sin dni
                if ("1".equals(tipoDocCliente) || "0".equals(tipoDocCliente)) {
                    throw new IllegalArgumentException("Error: No se puede emitir una FACTURA utilizando un DNI o Documento Sin RUC.");
                }
            }

            //boleta
            /*
            if ("03".equals(codigoComprobante)) {
                if ("1".equals(tipoDocCliente) && (numDocCliente == null || numDocCliente.length() != 8)) {
                    throw new IllegalArgumentException("Error: El número de documento para una BOLETA con DNI debe tener exactamente 8 dígitos.");
                }
            }
             */

            RequestRegistroComprobante registroSimpleReq = new RequestRegistroComprobante();
            registroSimpleReq.setIdVenta(request.getIdVenta());
            registroSimpleReq.setIdTipoComprobante(request.getIdTipoComprobante());
            registroSimpleReq.setIdSerieDocumento(idSerieDocumentoDetectado);

            // generamos el registro
            ResponseRegistroComprobante resultadoRegistro = registroComprobanteUseCase.registrarComprobante(registroSimpleReq);

            if (!resultadoRegistro.isExito()) {
                throw new RuntimeException("Error en el registro base del comprobante: " + resultadoRegistro.getMessage());
            }

            response.setExito(true);
            response.setMessage("El comprobante fue procesado y registrado correctamente.");

        } catch (IllegalArgumentException e) {
            response.setExito(false);
            response.setMessage(e.getMessage());
        } catch (Exception e) {
            response.setExito(false);
            response.setMessage("Error inesperado al procesar el comprobante: " + e.getMessage());
        }
        return response;
    }
}