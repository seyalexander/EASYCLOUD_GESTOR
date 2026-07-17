package com.SeyaCloudGestion.GestionSistema.feacture.detalleInventario.application.useCase;

import com.SeyaCloudGestion.GestionSistema.feacture.almacenes.application.dto.response.ResponseDetalleAlmacen;
import com.SeyaCloudGestion.GestionSistema.feacture.almacenes.application.useCase.DetalleAlmacenUseCase;
import com.SeyaCloudGestion.GestionSistema.feacture.articulos.application.dto.response.ResponseDetalleArticulo;
import com.SeyaCloudGestion.GestionSistema.feacture.articulos.application.useCase.DetalleArticuloUseCase;
import com.SeyaCloudGestion.GestionSistema.feacture.detalleInventario.application.dto.request.RequestRegistroDetalleInventario;
import com.SeyaCloudGestion.GestionSistema.feacture.detalleInventario.application.dto.response.ResponseRegistroDetalleInventario;
import com.SeyaCloudGestion.GestionSistema.feacture.detalleInventario.domain.services.DetalleInventarioService;
import com.SeyaCloudGestion.GestionSistema.feacture.inventarios.application.dto.response.ResponseDetalleInventario;
import com.SeyaCloudGestion.GestionSistema.feacture.inventarios.application.useCase.DetalleInventarioUseCase;
import com.SeyaCloudGestion.GestionSistema.feacture.inventarios.infraestructure.persistence.model.EstadoInventario;
import com.SeyaCloudGestion.GestionSistema.feacture.kardex.application.useCase.DetalleKardexUseCase;
import com.SeyaCloudGestion.GestionSistema.feacture.stocks.application.dto.response.ResponseDetalleSotck;
import com.SeyaCloudGestion.GestionSistema.feacture.stocks.application.useCase.DetalleSotckUseCase;
import org.springframework.stereotype.Component;

@Component
public class RegistroDetalleInventarioUseCase {
    private final DetalleInventarioService detalleInventarioService;
    private final DetalleInventarioUseCase detalleInventarioCabezeraUseCase;
    private final DetalleSotckUseCase detalleSotckUseCase;

    private final DetalleArticuloUseCase detalleArticuloUseCase;
    private final DetalleAlmacenUseCase detalleAlmacenUseCase;
    public RegistroDetalleInventarioUseCase(
            DetalleInventarioService detalleInventarioService, DetalleInventarioUseCase detalleInventarioCabezeraUseCase, DetalleSotckUseCase detalleSotckUseCase, DetalleArticuloUseCase detalleArticuloUseCase, DetalleAlmacenUseCase detalleAlmacenUseCase
    ) {
        this.detalleInventarioService = detalleInventarioService;
        this.detalleInventarioCabezeraUseCase = detalleInventarioCabezeraUseCase;
        this.detalleSotckUseCase = detalleSotckUseCase;
        this.detalleArticuloUseCase = detalleArticuloUseCase;
        this.detalleAlmacenUseCase = detalleAlmacenUseCase;
    }

    public ResponseRegistroDetalleInventario registrarDetalleInventario(long idInventarioCabezera, RequestRegistroDetalleInventario request, long idAlmacen) {
        try {

            //get detalle cabezera
            ResponseDetalleInventario responseBDCabezera = detalleInventarioCabezeraUseCase.DetalleInventario(idInventarioCabezera);
            if (!responseBDCabezera.isExito() || responseBDCabezera.getInventario()   == null) {
                throw new IllegalArgumentException("El inventario no existe.");
            }
            EstadoInventario estado = responseBDCabezera.getInventario().getEstado();
            //verificar que no estea cerrado
            if (estado.equals(EstadoInventario.AJUSTADO)) {
                throw new IllegalArgumentException("El inventario ya esta ajustado no se pueden insertar detalles.");
            }
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
            //get stock
            ResponseDetalleSotck responseBDStock = detalleSotckUseCase.DetalleSotck(request.getIdArticulo(), idAlmacen);
            if (!responseBDStock.isExito() || responseBDStock.getSotck() == null) {
                throw new IllegalArgumentException("No existe stock para el articulo en el almacen especificado.");
            }
            double total = responseBDStock.getSotck().getStock();
            ResponseRegistroDetalleInventario response = detalleInventarioService.registrarDetalleIventario(idInventarioCabezera,request, total);
            if (response.isExito()) {
            }

            return response;

        } catch (IllegalArgumentException | SecurityException e) {
            ResponseRegistroDetalleInventario response = new ResponseRegistroDetalleInventario();
            response.setExito(false);
            response.setMessage(e.getMessage());
            return response;
        } catch (Exception e) {
            String mensajeError = "Error inesperado al registrar el detalle del inventario: " + e.getMessage();
            System.err.println("[ERROR] " + mensajeError);
            ResponseRegistroDetalleInventario response = new ResponseRegistroDetalleInventario();
            response.setExito(false);
            response.setMessage(mensajeError);
            return response;
        }
    }
}