package com.SeyaCloudGestion.GestionSistema.feacture.movimientosStock.application.useCase;

import com.SeyaCloudGestion.GestionSistema.common.exceptions.ResourceNotFoundException;
import com.SeyaCloudGestion.GestionSistema.feacture.almacenes.application.dto.request.RequestDetalleAlmacen;
import com.SeyaCloudGestion.GestionSistema.feacture.almacenes.application.dto.response.ResponseDetalleAlmacen;
import com.SeyaCloudGestion.GestionSistema.feacture.almacenes.domain.services.AlmacenService;
import com.SeyaCloudGestion.GestionSistema.feacture.articulos.application.dto.request.RequestDetalleArticulo;
import com.SeyaCloudGestion.GestionSistema.feacture.articulos.application.dto.response.ResponseDetalleArticulo;
import com.SeyaCloudGestion.GestionSistema.feacture.articulos.domain.services.ArticulosService;
import com.SeyaCloudGestion.GestionSistema.feacture.movimientosStock.application.dto.request.RequestRegistroMovimientoStock;
import com.SeyaCloudGestion.GestionSistema.feacture.movimientosStock.application.dto.response.ResponseRegistroMovimientoStock;
import com.SeyaCloudGestion.GestionSistema.feacture.movimientosStock.domain.services.MovimientoStockService;
import com.SeyaCloudGestion.GestionSistema.feacture.tipoMovimientos.application.dto.request.RequestDetalleTipoMovimiento;
import com.SeyaCloudGestion.GestionSistema.feacture.tipoMovimientos.application.dto.response.ResponseDetalleTipoMovimiento;
import com.SeyaCloudGestion.GestionSistema.feacture.tipoMovimientos.domain.services.TipoMovimientoService;
import org.springframework.stereotype.Component;

@Component
public class RegistroMovimientoStockUseCase {
    private final MovimientoStockService movimientoStockService;
    private final ArticulosService articulosService;
    private final AlmacenService almacenesService;
    private final TipoMovimientoService tipoMovimientoService;

    public RegistroMovimientoStockUseCase(
            MovimientoStockService movimientoStockService, ArticulosService articulosService, AlmacenService almacenesService, TipoMovimientoService tipoMovimientoService
    ) {
        this.movimientoStockService = movimientoStockService;
        this.articulosService = articulosService;
        this.almacenesService = almacenesService;
        this.tipoMovimientoService = tipoMovimientoService;
    }

    public ResponseRegistroMovimientoStock RegistroMovimientoStock(RequestRegistroMovimientoStock request) {
        try {
            //articulo
            RequestDetalleArticulo requestDetalleArt = new RequestDetalleArticulo();
            requestDetalleArt.setIdArticulo(request.getIdArticulo());

            ResponseDetalleArticulo detalleBDArt= articulosService.DetalleArticulos(requestDetalleArt);

            if (!detalleBDArt.isExito() || detalleBDArt.getArticulos() == null) {
                throw new IllegalArgumentException("El articulo no existe.");
            }
            //almacen
            RequestDetalleAlmacen requestDetalleAlm = new RequestDetalleAlmacen();
            requestDetalleAlm.setIdAlmacen(request.getIdAlmacen());

            ResponseDetalleAlmacen detalleBDAlm= almacenesService.DetalleAlmacen(requestDetalleAlm);

            if (!detalleBDAlm.isExito() || detalleBDAlm.getAlmacen() == null) {
                throw new ResourceNotFoundException("El almacen no existe.");
            }

            //tipo movmiento
            RequestDetalleTipoMovimiento requestDetalle = new RequestDetalleTipoMovimiento();
            requestDetalle.setIdTipoMovimiento(request.getIdTipoMovimiento());

            ResponseDetalleTipoMovimiento detalleBD= tipoMovimientoService.DetalleTipoMovimiento(requestDetalle);

            if (!detalleBD.isExito() || detalleBD.getTipoMovimiento() == null) {
                throw new ResourceNotFoundException("El tipo movimineto no existe.");
            }

            ResponseRegistroMovimientoStock response = movimientoStockService.RegistroMovimientoStock(request);

            if (response.isExito()) {

            }

            return response;

        } catch (IllegalArgumentException | SecurityException e) {
            ResponseRegistroMovimientoStock response = new ResponseRegistroMovimientoStock();
            response.setExito(false);
            response.setMessage(e.getMessage());
            return response;

        } catch (Exception e) {
            String mensajeError = "Error inesperado al registrar el movimiento de stock: " + e.getMessage();
            System.err.println("[ERROR] " + mensajeError);

            ResponseRegistroMovimientoStock response = new ResponseRegistroMovimientoStock();
            response.setExito(false);
            response.setMessage(mensajeError);
            return response;
        }
    }
}