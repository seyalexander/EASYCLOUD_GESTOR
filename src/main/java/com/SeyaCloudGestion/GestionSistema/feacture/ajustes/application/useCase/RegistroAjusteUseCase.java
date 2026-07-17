package com.SeyaCloudGestion.GestionSistema.feacture.ajustes.application.useCase;

import com.SeyaCloudGestion.GestionSistema.feacture.ajustes.application.dto.request.RequestRegistrarAjuste;
import com.SeyaCloudGestion.GestionSistema.feacture.ajustes.application.dto.response.ResponseRegistroAjuste;
import com.SeyaCloudGestion.GestionSistema.feacture.ajustes.domain.services.AjustesService;
import com.SeyaCloudGestion.GestionSistema.feacture.almacenes.application.dto.response.ResponseDetalleAlmacen;
import com.SeyaCloudGestion.GestionSistema.feacture.almacenes.application.useCase.DetalleAlmacenUseCase;
import com.SeyaCloudGestion.GestionSistema.feacture.articulos.application.dto.response.ResponseDetalleArticulo;
import com.SeyaCloudGestion.GestionSistema.feacture.articulos.application.useCase.DetalleArticuloUseCase;
import com.SeyaCloudGestion.GestionSistema.feacture.stocks.application.dto.response.ResponseDetalleSotck;
import com.SeyaCloudGestion.GestionSistema.feacture.stocks.application.useCase.DetalleSotckUseCase;
import org.springframework.stereotype.Component;

@Component
public class RegistroAjusteUseCase {

    private final AjustesService ajustesService;
    private final DetalleArticuloUseCase detalleArticuloUseCase;
    private final DetalleAlmacenUseCase detalleAlmacenUseCase;
    private final DetalleSotckUseCase detalleSotckUseCase;

    public RegistroAjusteUseCase(AjustesService ajustesService, DetalleArticuloUseCase detalleArticuloUseCase, DetalleAlmacenUseCase detalleAlmacenUseCase, DetalleSotckUseCase detalleSotckUseCase) {
        this.ajustesService = ajustesService;
        this.detalleArticuloUseCase = detalleArticuloUseCase;
        this.detalleAlmacenUseCase = detalleAlmacenUseCase;
        this.detalleSotckUseCase = detalleSotckUseCase;
    }

    public ResponseRegistroAjuste registroAjustes(RequestRegistrarAjuste request) {
        try {
            //get articulo
            ResponseDetalleArticulo detalleBDArt = detalleArticuloUseCase.DetalleArticulo(request.getIdArticulo());
            if (!detalleBDArt.isExito() || detalleBDArt.getArticulos() == null) {
                throw new IllegalArgumentException("El articulo no existe.");
            }
            //get almacen
            ResponseDetalleAlmacen detalleBDAlm = detalleAlmacenUseCase.DetalleAlmacenes(request.getIdAlmacen());
            if (!detalleBDAlm.isExito() || detalleBDAlm.getAlmacen() == null) {
                throw new IllegalArgumentException("El almacen no existe.");
            }
            //get stock
            ResponseDetalleSotck responseBDStock = detalleSotckUseCase.DetalleSotck(request.getIdArticulo(), request.getIdArticulo());
            if (!responseBDStock.isExito() || responseBDStock.getSotck() == null) {
                throw new IllegalArgumentException("No existe stock para el articulo en el almacen especificado.");
            }

            ResponseRegistroAjuste response = ajustesService.RegistroAjustes(request);

            if (response.isExito()) {
            }

            return response;

        } catch (IllegalArgumentException | SecurityException e) {
            ResponseRegistroAjuste response = new ResponseRegistroAjuste();
            response.setExito(false);
            response.setMessage(e.getMessage());
            return response;
        } catch (Exception e) {
            String mensajeError = "Error inesperado al registrar el ajuste de inventario: " + e.getMessage();
            System.err.println("[ERROR] " + mensajeError);
            ResponseRegistroAjuste response = new ResponseRegistroAjuste();
            response.setExito(false);
            response.setMessage(mensajeError);
            return response;
        }
    }
}