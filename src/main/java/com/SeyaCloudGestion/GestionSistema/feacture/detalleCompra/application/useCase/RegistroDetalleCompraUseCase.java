package com.SeyaCloudGestion.GestionSistema.feacture.detalleCompra.application.useCase;

import com.SeyaCloudGestion.GestionSistema.feacture.almacenes.application.dto.response.ResponseDetalleAlmacen;
import com.SeyaCloudGestion.GestionSistema.feacture.almacenes.application.useCase.DetalleAlmacenUseCase;
import com.SeyaCloudGestion.GestionSistema.feacture.articulos.application.dto.response.ResponseDetalleArticulo;
import com.SeyaCloudGestion.GestionSistema.feacture.articulos.application.useCase.DetalleArticuloUseCase;
import com.SeyaCloudGestion.GestionSistema.feacture.detalleCompra.application.dto.request.RequestRegistroDetalleCompra;
import com.SeyaCloudGestion.GestionSistema.feacture.detalleCompra.application.dto.response.ResponseRegistroDetalleCompra;
import com.SeyaCloudGestion.GestionSistema.feacture.detalleCompra.domain.services.DetalleCompraService;
import com.SeyaCloudGestion.GestionSistema.feacture.kardex.application.dto.response.ResponseDetalleKardex;
import com.SeyaCloudGestion.GestionSistema.feacture.kardex.application.useCase.DetalleKardexUseCase;
import org.springframework.stereotype.Component;

@Component
public class RegistroDetalleCompraUseCase {
    private final DetalleCompraService detalleVentaService;
    private final DetalleArticuloUseCase detalleArticuloUseCase;
    private final DetalleAlmacenUseCase detalleAlmacenUseCase;
    private final DetalleKardexUseCase detalleKardexUseCase;
    public RegistroDetalleCompraUseCase(
            DetalleCompraService detalleVentaService, DetalleArticuloUseCase detalleArticuloUseCase, DetalleAlmacenUseCase detalleAlmacenUseCase, DetalleKardexUseCase detalleKardexUseCase
    ) {
        this.detalleVentaService = detalleVentaService;
        this.detalleArticuloUseCase = detalleArticuloUseCase;
        this.detalleAlmacenUseCase = detalleAlmacenUseCase;
        this.detalleKardexUseCase = detalleKardexUseCase;
    }

    public ResponseRegistroDetalleCompra registrarDetalleCompra(long idCompra,RequestRegistroDetalleCompra request,long idAlmacen) {
        try {
            //get kardex
            /*
            ResponseDetalleKardex responseKardex =detalleKardexUseCase.detalleKardex(request.getIdArticulo(),idAlmacen);
            if (!responseKardex.isExito() || responseKardex.getKardex() == null) {
                throw new IllegalArgumentException("El kardex no existe.");
            }
             */
            //get articulo
            ResponseDetalleArticulo detalleBDArt = detalleArticuloUseCase.DetalleArticulo(request.getIdArticulo());
            if (!detalleBDArt.isExito() || detalleBDArt.getArticulos() == null) {
                throw new IllegalArgumentException("El articulo no existe.");
            }
            //get almacen
            ResponseDetalleAlmacen detalleBDAlm = detalleAlmacenUseCase.DetalleAlmacenes(idAlmacen);
            if (!detalleBDAlm.isExito() || detalleBDAlm.getAlmacen() == null) {
                throw new IllegalArgumentException("El almacen no existe.");
            }
            //total
            double total = request.getCantidad()*request.getCostoUnitario();

            ResponseRegistroDetalleCompra response = detalleVentaService.registrarDetalleCompra(idCompra,request, total);
            if (response.isExito()) {
                response.setTotal(total);
            }

            return response;

        } catch (IllegalArgumentException | SecurityException e) {
            ResponseRegistroDetalleCompra response = new ResponseRegistroDetalleCompra();
            response.setExito(false);
            response.setMessage(e.getMessage());
            return response;
        } catch (Exception e) {
            String mensajeError = "Error inesperado al registrar el detalle de la venta: " + e.getMessage();
            System.err.println("[ERROR] " + mensajeError);
            ResponseRegistroDetalleCompra response = new ResponseRegistroDetalleCompra();
            response.setExito(false);
            response.setMessage(mensajeError);
            return response;
        }
    }
}