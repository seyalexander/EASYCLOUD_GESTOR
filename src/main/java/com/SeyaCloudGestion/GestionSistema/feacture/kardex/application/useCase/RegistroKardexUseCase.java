package com.SeyaCloudGestion.GestionSistema.feacture.kardex.application.useCase;

import com.SeyaCloudGestion.GestionSistema.feacture.almacenes.application.dto.response.ResponseDetalleAlmacen;
import com.SeyaCloudGestion.GestionSistema.feacture.almacenes.application.useCase.DetalleAlmacenUseCase;
import com.SeyaCloudGestion.GestionSistema.feacture.articulos.application.dto.response.ResponseDetalleArticulo;
import com.SeyaCloudGestion.GestionSistema.feacture.articulos.application.useCase.DetalleArticuloUseCase;
import com.SeyaCloudGestion.GestionSistema.feacture.kardex.application.dto.request.RequestRegistroKardex;
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

    public ResponseRegistroKardex registroKardex(RequestRegistroKardex request) {
        try {
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
            //si ya hay kardex
            if (detalleBDKardex.isExito() || detalleBDKardex.getKardex() != null) {

                double saldoCantidadBD = detalleBDKardex.getKardex().getSaldoCantidad();
                double saldoCostoBD = detalleBDKardex.getKardex().getSaldoCosto();

                //ingreso
                if (tipoMov.esIngreso()){

                    //new saldo cantidad and saldo costo
                    double saldoCantidadNew=saldoCantidadBD+request.getCantidadEntrada();
                    double saldoCostoNew=saldoCostoBD+request.getCostoEntrada();

                    ResponseRegistroKardex response = kardexService.RegistroKardex(request,saldoCantidadNew,saldoCostoNew);
                    if (response.isExito()) {

                    }
                    return response;
                }
                //egreso
                if (tipoMov.esEgreso()){
                    if (saldoCantidadBD <= 0) {
                        throw new IllegalArgumentException("No se puede registrar un egreso porque el stock actual es 0 o negativo.");
                    }

                    //costo promedio
                    double costoPromedioActual =(saldoCostoBD/saldoCantidadBD);
                    double costoSalida=request.getCantidadSalida()*costoPromedioActual;
                    request.setCostoSalida(costoSalida);

                    //new saldo cantidad and saldo costo
                    double saldoCantidadNew=saldoCantidadBD-request.getCantidadSalida();
                    double saldoCostoNew=saldoCostoBD-costoSalida;
                    //seteamos los valores

                    ResponseRegistroKardex response = kardexService.RegistroKardex(request,saldoCantidadNew,saldoCostoNew);
                    if (response.isExito()) {

                    }
                    return response;
                }
            }

            if (tipoMov.esEgreso()) {
                throw new IllegalArgumentException("No se puede iniciar el Kardex de un producto con un movimiento de EGRESO.");
            }

            //primer kardex
            double saldoCantidad=request.getCantidadEntrada();
            double saldoCosto=request.getCostoEntrada();
            ResponseRegistroKardex response = kardexService.RegistroKardex(request,saldoCantidad,saldoCosto);
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