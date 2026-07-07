package com.SeyaCloudGestion.GestionSistema.feacture.kardex.application.useCase;

import com.SeyaCloudGestion.GestionSistema.feacture.almacenes.application.dto.response.ResponseDetalleAlmacen;
import com.SeyaCloudGestion.GestionSistema.feacture.almacenes.application.useCase.DetalleAlmacenUseCase;
import com.SeyaCloudGestion.GestionSistema.feacture.articulos.application.dto.response.ResponseDetalleArticulo;
import com.SeyaCloudGestion.GestionSistema.feacture.articulos.application.useCase.DetalleArticuloUseCase;
import com.SeyaCloudGestion.GestionSistema.feacture.kardex.application.dto.request.RequestRegistroKardex;
import com.SeyaCloudGestion.GestionSistema.feacture.kardex.application.dto.request.RequestRegistroKardexRecortado;
import com.SeyaCloudGestion.GestionSistema.feacture.kardex.application.dto.response.ResponseDetalleKardex;
import com.SeyaCloudGestion.GestionSistema.feacture.kardex.application.dto.response.ResponseRegistroKardex;
import com.SeyaCloudGestion.GestionSistema.feacture.kardex.domain.services.KardexService;
import com.SeyaCloudGestion.GestionSistema.feacture.kardex.infraestructure.persistence.model.TipoMovimientoKardex;
import org.springframework.stereotype.Component;

@Component
public class RegistroKardexUseCase {

    private final KardexService kardexService;
    private final DetalleKardexUseCase detalleKardexUseCase;
    private final DetalleArticuloUseCase detalleArticuloUseCase;
    private final DetalleAlmacenUseCase detalleAlmacenUseCase;

    public RegistroKardexUseCase(KardexService kardexService, DetalleKardexUseCase detalleKardexUseCase, DetalleArticuloUseCase detalleArticuloUseCase, DetalleAlmacenUseCase detalleAlmacenUseCase) {
        this.kardexService = kardexService;
        this.detalleKardexUseCase = detalleKardexUseCase;
        this.detalleArticuloUseCase = detalleArticuloUseCase;
        this.detalleAlmacenUseCase = detalleAlmacenUseCase;
    }

    public ResponseRegistroKardex registroKardex(RequestRegistroKardexRecortado request) {
        try {
            System.out.println("--- DEBUG KARDEX ---");
            System.out.println("Cantidad: " + request.getCantidad());
            System.out.println("Costo Unitario: " + request.getCostoUnitario());
            System.out.println("Cálculo Resultado: " + (request.getCantidad() * request.getCostoUnitario()));
            //verificar los id antes de que truene todo
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

            TipoMovimientoKardex tipoMov = request.getTipoMovimiento();

            //traemos el ultimo kardex del producto en este alamcen
            ResponseDetalleKardex detalleBDKardex =detalleKardexUseCase.detalleKardex(request.getIdArticulo(),request.getIdAlmacen());

            //generar cardedx full

            RequestRegistroKardex requestFull = new RequestRegistroKardex();
            requestFull.setIdArticulo(request.getIdArticulo());
            requestFull.setIdAlmacen(request.getIdAlmacen());
            requestFull.setTipoMovimiento(request.getTipoMovimiento());

            //generamos el kardedx
            //si ya hay kardex
            if (detalleBDKardex.isExito() && detalleBDKardex.getKardex() != null) {

                //datos de la BD
                double saldoCantidadBD = detalleBDKardex.getKardex().getSaldoCantidad();
                double saldoCostoBD = detalleBDKardex.getKardex().getSaldoCosto();

                //ingreso
                if (tipoMov.esIngreso()){

                    requestFull.setCantidadEntrada(request.getCantidad());
                    requestFull.setCostoEntrada(requestFull.getCantidadEntrada()*request.getCostoUnitario());
                    requestFull.setCantidadSalida(0);
                    requestFull.setCostoSalida(0);
                    //new saldo cantidad and saldo costo
                    double saldoCantidadNew=saldoCantidadBD+requestFull.getCantidadEntrada();
                    double saldoCostoNew=saldoCostoBD+requestFull.getCostoEntrada();

                    ResponseRegistroKardex response = kardexService.RegistroKardex(requestFull,saldoCantidadNew,saldoCostoNew);
                    if (response.isExito()) {

                    }
                    return response;
                }
                //egreso
                if (tipoMov.esEgreso()){
                    if (saldoCantidadBD <= 0 || saldoCantidadBD < request.getCantidad()) {
                        throw new IllegalArgumentException("No se puede registrar un egreso porque el stock actual es 0 o negativo.");
                    }
                    //set full
                    requestFull.setCantidadSalida(request.getCantidad());
                    //costo promedio
                    double costoPromedioActual =(saldoCostoBD/saldoCantidadBD);
                    double costoSalida=requestFull.getCantidadSalida()*costoPromedioActual;
                    requestFull.setCostoSalida(costoSalida);
                    requestFull.setCantidadEntrada(0);
                    requestFull.setCostoEntrada(0);
                    //new saldo cantidad and saldo costo
                    double saldoCantidadNew=saldoCantidadBD-requestFull.getCantidadSalida();
                    double saldoCostoNew=saldoCostoBD-costoSalida;
                    //seteamos los valores

                    ResponseRegistroKardex response = kardexService.RegistroKardex(requestFull,saldoCantidadNew,saldoCostoNew);
                    if (response.isExito()) {

                    }
                    return response;
                }
            }

            if (tipoMov.esEgreso()) {
                throw new IllegalArgumentException("No se puede iniciar el Kardex de un producto con un movimiento de EGRESO.");
            }
            requestFull.setCantidadEntrada(request.getCantidad());
            requestFull.setCostoEntrada(requestFull.getCantidadEntrada()*request.getCostoUnitario());
            requestFull.setCantidadSalida(0);
            requestFull.setCostoSalida(0);
            //primer kardex
            double saldoCantidad=request.getCantidad();
            double saldoCosto=requestFull.getCostoEntrada();
            ResponseRegistroKardex response = kardexService.RegistroKardex(requestFull,saldoCantidad,saldoCosto);
            if (response.isExito()) {

            }

            return response;

        } catch (IllegalArgumentException | SecurityException e) {
            ResponseRegistroKardex response = new ResponseRegistroKardex();
            response.setExito(false);
            response.setMessage(e.getMessage());
            return response;
        } catch (Exception e) {
            String mensajeError = "Error inesperado al registrar el movimiento en Kardex: " + e.getMessage();
            System.err.println("[ERROR] " + mensajeError);
            ResponseRegistroKardex response = new ResponseRegistroKardex();
            response.setExito(false);
            response.setMessage(mensajeError);
            return response;
        }
    }
}