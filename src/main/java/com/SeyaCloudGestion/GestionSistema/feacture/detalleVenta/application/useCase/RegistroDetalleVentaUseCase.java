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
import org.springframework.stereotype.Component;

@Component
public class RegistroDetalleVentaUseCase {
    private final DetalleVentaService detalleVentaService;
    private final DetalleArticuloUseCase detalleArticuloUseCase;
    private final DetalleAlmacenUseCase detalleAlmacenUseCase;
    private final DetalleKardexUseCase detalleKardexUseCase;
    public RegistroDetalleVentaUseCase(
            DetalleVentaService detalleVentaService, DetalleArticuloUseCase detalleArticuloUseCase, DetalleAlmacenUseCase detalleAlmacenUseCase, DetalleKardexUseCase detalleKardexUseCase
    ) {
        this.detalleVentaService = detalleVentaService;
        this.detalleArticuloUseCase = detalleArticuloUseCase;
        this.detalleAlmacenUseCase = detalleAlmacenUseCase;
        this.detalleKardexUseCase = detalleKardexUseCase;
    }

    public ResponseRegistroDetalleVenta registrarDetalleVenta(RequestRegistroDetalleVenta request) {
        try {
            //get kardex
            ResponseDetalleKardex responseKardex =detalleKardexUseCase.detalleKardex(request.getIdArticulo(),request.getIdAlmacen());
            if (!responseKardex.isExito() || responseKardex.getKardex() == null) {
                throw new IllegalArgumentException("El kardex no existe.");
            }
            //validar stock
            double stockKardex = responseKardex.getKardex().getSaldoCantidad();
            if (stockKardex <= 0 || stockKardex < request.getCantidad()) {
                throw new IllegalArgumentException("Stock insuficiente. Disponible: " + stockKardex + ", Solicitado: " + request.getCantidad());
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
            double subTotal = request.getCantidad()*request.getPrecioUnitario();
            double descuento =subTotal* request.getDescuento();
            double total = subTotal-descuento;
            //costoUnitario
            double costoUnitario;

            if (stockKardex > 0) {
                costoUnitario = responseKardex.getKardex().getSaldoCosto() / stockKardex;
            } else {
                costoUnitario = detalleBDArt.getArticulos().getCostoCompra();
            }

            ResponseRegistroDetalleVenta response = detalleVentaService.registrarDetalleVenta(request,total,costoUnitario);
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