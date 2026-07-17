package com.SeyaCloudGestion.GestionSistema.feacture.transferenciaDetalles.application.useCase;

import com.SeyaCloudGestion.GestionSistema.feacture.articulos.application.dto.response.ResponseDetalleArticulo;
import com.SeyaCloudGestion.GestionSistema.feacture.articulos.application.useCase.DetalleArticuloUseCase;
import com.SeyaCloudGestion.GestionSistema.feacture.kardex.application.dto.response.ResponseDetalleKardex;
import com.SeyaCloudGestion.GestionSistema.feacture.kardex.application.useCase.DetalleKardexUseCase;
import com.SeyaCloudGestion.GestionSistema.feacture.stocks.application.dto.response.ResponseDetalleSotck;
import com.SeyaCloudGestion.GestionSistema.feacture.stocks.application.useCase.DetalleSotckUseCase;
import com.SeyaCloudGestion.GestionSistema.feacture.transferenciaDetalles.application.dto.request.RequestRegistroDetalleTransferencia;
import com.SeyaCloudGestion.GestionSistema.feacture.transferenciaDetalles.application.dto.response.ResponseFullRegistroTransferenciaDetalle;
import com.SeyaCloudGestion.GestionSistema.feacture.transferenciaDetalles.application.dto.response.ResponseRegistroTransferenciaDetalle;
import com.SeyaCloudGestion.GestionSistema.feacture.transferenciaDetalles.domain.services.TransferenciaDetalleService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RegistroProcesarFullDetalleTransferenciaUseCase {

    private final DetalleArticuloUseCase detalleArticuloUseCase;
    private final RegistroDetalleTransferenciaUseCase registroDetalleTransferenciaUseCase;
    private final DetalleSotckUseCase detalleSotckUseCase;
    private final DetalleKardexUseCase detalleKardexUseCase;

    public RegistroProcesarFullDetalleTransferenciaUseCase(DetalleArticuloUseCase detalleArticuloUseCase, RegistroDetalleTransferenciaUseCase registroDetalleTransferenciaUseCase, DetalleSotckUseCase detalleSotckUseCase, DetalleKardexUseCase detalleKardexUseCase) {
        this.detalleArticuloUseCase = detalleArticuloUseCase;
        this.registroDetalleTransferenciaUseCase = registroDetalleTransferenciaUseCase;
        this.detalleSotckUseCase = detalleSotckUseCase;
        this.detalleKardexUseCase = detalleKardexUseCase;
    }
    //FUL:LLLLLLLL
    public ResponseFullRegistroTransferenciaDetalle RegistroDetalleTransferencia(long idTransferencia, List<RequestRegistroDetalleTransferencia>  detalles,long idAlmacen) {
        try {

            //haremos las validaciones en el for priemro
            for (RequestRegistroDetalleTransferencia d : detalles) {
                //get articulo
                ResponseDetalleArticulo detalleBDArt = detalleArticuloUseCase.DetalleArticulo(d.getIdArticulo());
                if (!detalleBDArt.isExito() || detalleBDArt.getArticulos() == null) {
                    throw new IllegalArgumentException("El articulo no existe.");
                }
                //stock
                ResponseDetalleSotck detalleBDStock = detalleSotckUseCase.DetalleSotck(d.getIdArticulo(),idAlmacen);
                if (detalleBDStock.getSotck().getStock() < d.getCantidad()) {
                    throw new IllegalArgumentException("Stock insuficiente para el artículo ID [" + d.getIdArticulo() + "] en el almacén de origen.");
                }

            }
            //seteamos los detalles
            for (RequestRegistroDetalleTransferencia d : detalles) {
                //get kardex
                //Aqui cae venta, ajuste salida y ajuste entrada
                ResponseDetalleKardex detalleBDKardex = detalleKardexUseCase.detalleKardex(d.getIdArticulo(), idAlmacen);
                if (!detalleBDKardex.isExito() && detalleBDKardex.getKardex() == null) {
                    throw new IllegalArgumentException("Error al procesar los detalles de la transferencia el kardex no existe." +detalleBDKardex.getMessage());
                }
                double saldoCantidadBD = detalleBDKardex.getKardex().getSaldoCantidad();
                double saldoCostoBD = detalleBDKardex.getKardex().getSaldoCosto();
                double costoUnitarioParaMovimiento = 0.00;
                if (saldoCantidadBD > 0) {
                    costoUnitarioParaMovimiento = saldoCostoBD / saldoCantidadBD;
                }
                //aqui registramos 1x1
                ResponseRegistroTransferenciaDetalle responseRegistroTransferenciaDetalle =
                        registroDetalleTransferenciaUseCase.RegistroDetalleTransferencia(idTransferencia, d,costoUnitarioParaMovimiento);

                if (!responseRegistroTransferenciaDetalle.isExito()) {
                    throw new IllegalArgumentException("Fallo al insertar la fila del detalle de transferencia para el artículo ID [" + d.getIdArticulo() + "]");
                }

            }

            ResponseFullRegistroTransferenciaDetalle responser = new ResponseFullRegistroTransferenciaDetalle();
            responser.setExito(true);
            if (responser.isExito()) {

            }

            return responser;

        } catch (IllegalArgumentException | SecurityException e) {
            ResponseFullRegistroTransferenciaDetalle response = new ResponseFullRegistroTransferenciaDetalle();
            response.setExito(false);
            response.setMessage(e.getMessage());
            return response;
        } catch (Exception e) {
            String mensajeError = "Error inesperado al registrar el proceso de registro de los detalles de la transferencia: " + e.getMessage();
            System.err.println("[ERROR] " + mensajeError);
            ResponseFullRegistroTransferenciaDetalle response = new ResponseFullRegistroTransferenciaDetalle();
            response.setExito(false);
            response.setMessage(mensajeError);
            return response;
        }
    }
}