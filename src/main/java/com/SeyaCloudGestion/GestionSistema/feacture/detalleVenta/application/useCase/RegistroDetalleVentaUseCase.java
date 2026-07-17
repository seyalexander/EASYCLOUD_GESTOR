package com.SeyaCloudGestion.GestionSistema.feacture.detalleVenta.application.useCase;

import com.SeyaCloudGestion.GestionSistema.feacture.almacenes.application.dto.response.ResponseDetalleAlmacen;
import com.SeyaCloudGestion.GestionSistema.feacture.almacenes.application.useCase.DetalleAlmacenUseCase;
import com.SeyaCloudGestion.GestionSistema.feacture.articulos.application.dto.response.ResponseDetalleArticulo;
import com.SeyaCloudGestion.GestionSistema.feacture.articulos.application.useCase.DetalleArticuloUseCase;
import com.SeyaCloudGestion.GestionSistema.feacture.detalleVenta.application.dto.request.RequestRegistroDetalleVenta;
import com.SeyaCloudGestion.GestionSistema.feacture.detalleVenta.application.dto.response.ResponseRegistroDetalleVenta;
import com.SeyaCloudGestion.GestionSistema.feacture.detalleVenta.domain.services.DetalleVentaService;
import com.SeyaCloudGestion.GestionSistema.feacture.kardex.application.dto.response.ResponseDetalleKardex;
import com.SeyaCloudGestion.GestionSistema.feacture.kardex.application.useCase.DetalleKardexUseCase;
import com.SeyaCloudGestion.GestionSistema.feacture.stocks.application.dto.response.ResponseDetalleSotck;
import com.SeyaCloudGestion.GestionSistema.feacture.stocks.application.useCase.DetalleSotckUseCase;
import org.springframework.stereotype.Component;

@Component
public class RegistroDetalleVentaUseCase {
    private final DetalleVentaService detalleVentaService;
    private final DetalleArticuloUseCase detalleArticuloUseCase;
    private final DetalleAlmacenUseCase detalleAlmacenUseCase;
    private final DetalleKardexUseCase detalleKardexUseCase;
    private final DetalleSotckUseCase detalleSotckUseCase;
    public RegistroDetalleVentaUseCase(
            DetalleVentaService detalleVentaService, DetalleArticuloUseCase detalleArticuloUseCase, DetalleAlmacenUseCase detalleAlmacenUseCase, DetalleKardexUseCase detalleKardexUseCase, DetalleSotckUseCase detalleSotckUseCase
    ) {
        this.detalleVentaService = detalleVentaService;
        this.detalleArticuloUseCase = detalleArticuloUseCase;
        this.detalleAlmacenUseCase = detalleAlmacenUseCase;
        this.detalleKardexUseCase = detalleKardexUseCase;
        this.detalleSotckUseCase = detalleSotckUseCase;
    }

    public ResponseRegistroDetalleVenta registrarDetalleVenta(long idVenta,RequestRegistroDetalleVenta request) {
        try {
            //detalle stock
            ResponseDetalleSotck responseDetalleSotck = detalleSotckUseCase.DetalleSotck(request.getIdArticulo(),request.getIdAlmacen());
            if (!responseDetalleSotck.isExito() || responseDetalleSotck.getSotck() == null) {
                throw new IllegalArgumentException("El stock no existe.");
            }
            //validar stock
            double stockActual = responseDetalleSotck.getSotck().getStock();
            if (stockActual <= 0 || stockActual < request.getCantidad()) {
                throw new IllegalArgumentException("Stock insuficiente en almacén. Disponible: " + stockActual + ", Solicitado: " + request.getCantidad());
            }

            //get kardex
            ResponseDetalleKardex responseKardex =detalleKardexUseCase.detalleKardex(request.getIdArticulo(),request.getIdAlmacen());
            if (!responseKardex.isExito() || responseKardex.getKardex() == null) {
                throw new IllegalArgumentException("El kardex no existe.");
            }


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
            //total y desceutno
            double subTotal = request.getCantidad()*detalleBDArt.getArticulos().getPrecioVenta();
            double descuento =subTotal* request.getDescuento();
            double total = subTotal-descuento;
            //costoUnitario
            double saldoCantidadKardex = responseKardex.getKardex().getSaldoCantidad();
            double costoUnitario;

            if (saldoCantidadKardex > 0) {
                costoUnitario = responseKardex.getKardex().getSaldoCosto() / saldoCantidadKardex;
            } else {
                costoUnitario = detalleBDArt.getArticulos().getCostoCompra();
            }

            ResponseRegistroDetalleVenta response = detalleVentaService.registrarDetalleVenta(idVenta,request,total,costoUnitario);
            if (response.isExito()) {
                response.setTotal(total);
            }

            return response;

        } catch (IllegalArgumentException | SecurityException e) {
            ResponseRegistroDetalleVenta response = new ResponseRegistroDetalleVenta();
            response.setExito(false);
            response.setMessage(e.getMessage());
            return response;
        } catch (Exception e) {
            String mensajeError = "Error inesperado al registrar el detalle de la venta: " + e.getMessage();
            System.err.println("[ERROR] " + mensajeError);
            ResponseRegistroDetalleVenta response = new ResponseRegistroDetalleVenta();
            response.setExito(false);
            response.setMessage(mensajeError);
            return response;
        }
    }
}