package com.SeyaCloudGestion.GestionSistema.feacture.detalleVenta.application.useCase;

import com.SeyaCloudGestion.GestionSistema.feacture.almacenes.application.dto.response.ResponseDetalleAlmacen;
import com.SeyaCloudGestion.GestionSistema.feacture.almacenes.application.useCase.DetalleAlmacenUseCase;
import com.SeyaCloudGestion.GestionSistema.feacture.articulos.application.dto.response.ResponseDetalleArticulo;
import com.SeyaCloudGestion.GestionSistema.feacture.articulos.application.useCase.DetalleArticuloUseCase;
import com.SeyaCloudGestion.GestionSistema.feacture.detalleVenta.application.dto.request.RequestRegistroDetalleVenta;
import com.SeyaCloudGestion.GestionSistema.feacture.detalleVenta.application.dto.response.ResponseRegistroDetalleVenta;
import com.SeyaCloudGestion.GestionSistema.feacture.detalleVenta.domain.services.DetalleVentaService;
import org.springframework.stereotype.Component;

@Component
public class RegistroDetalleVentaUseCase {
    private final DetalleVentaService detalleVentaService;
    private final DetalleArticuloUseCase detalleArticuloUseCase;
    private final DetalleAlmacenUseCase detalleAlmacenUseCase;
    public RegistroDetalleVentaUseCase(
            DetalleVentaService detalleVentaService, DetalleArticuloUseCase detalleArticuloUseCase, DetalleAlmacenUseCase detalleAlmacenUseCase
    ) {
        this.detalleVentaService = detalleVentaService;
        this.detalleArticuloUseCase = detalleArticuloUseCase;
        this.detalleAlmacenUseCase = detalleAlmacenUseCase;
    }

    public ResponseRegistroDetalleVenta registrarDetalleVenta(RequestRegistroDetalleVenta request) {
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
            //total y desceutno
            double subTotal = request.getCantidad()*request.getPrecioUnitario();
            double descuento =subTotal* request.getDescuento();
            double total = subTotal-descuento;

            ResponseRegistroDetalleVenta response = detalleVentaService.registrarDetalleVenta(request,total);
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