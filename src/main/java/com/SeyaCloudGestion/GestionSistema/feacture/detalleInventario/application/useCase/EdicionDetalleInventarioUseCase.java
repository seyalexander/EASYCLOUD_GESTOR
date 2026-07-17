package com.SeyaCloudGestion.GestionSistema.feacture.detalleInventario.application.useCase;

import com.SeyaCloudGestion.GestionSistema.feacture.almacenes.application.dto.response.ResponseDetalleAlmacen;
import com.SeyaCloudGestion.GestionSistema.feacture.almacenes.application.useCase.DetalleAlmacenUseCase;
import com.SeyaCloudGestion.GestionSistema.feacture.articulos.application.dto.response.ResponseDetalleArticulo;
import com.SeyaCloudGestion.GestionSistema.feacture.articulos.application.useCase.DetalleArticuloUseCase;
import com.SeyaCloudGestion.GestionSistema.feacture.detalleInventario.application.dto.request.RequestEditarDetalleInventario;
import com.SeyaCloudGestion.GestionSistema.feacture.detalleInventario.application.dto.request.RequestRegistroDetalleInventario;
import com.SeyaCloudGestion.GestionSistema.feacture.detalleInventario.application.dto.response.ResponseEditarDetalleInventario;
import com.SeyaCloudGestion.GestionSistema.feacture.detalleInventario.application.dto.response.ResponseRegistroDetalleInventario;
import com.SeyaCloudGestion.GestionSistema.feacture.detalleInventario.domain.services.DetalleInventarioService;
import com.SeyaCloudGestion.GestionSistema.feacture.inventarios.application.dto.response.ResponseDetalleInventario;
import com.SeyaCloudGestion.GestionSistema.feacture.inventarios.application.useCase.DetalleInventarioUseCase;
import com.SeyaCloudGestion.GestionSistema.feacture.inventarios.infraestructure.persistence.model.EstadoInventario;
import com.SeyaCloudGestion.GestionSistema.feacture.kardex.application.useCase.DetalleKardexUseCase;
import org.springframework.stereotype.Component;

@Component
public class EdicionDetalleInventarioUseCase {
    private final DetalleInventarioService detalleinventatioService;
    private final DetalleInventarioUseCase detalleInventarioCabezeraUseCase;

    private final DetalleArticuloUseCase detalleArticuloUseCase;
    private final DetalleAlmacenUseCase detalleAlmacenUseCase;
    public EdicionDetalleInventarioUseCase(
            DetalleInventarioService detalleinventatioService, DetalleInventarioUseCase detalleInventarioCabezeraUseCase, DetalleArticuloUseCase detalleArticuloUseCase, DetalleAlmacenUseCase detalleAlmacenUseCase
    ) {
        this.detalleinventatioService = detalleinventatioService;
        this.detalleInventarioCabezeraUseCase = detalleInventarioCabezeraUseCase;
        this.detalleArticuloUseCase = detalleArticuloUseCase;
        this.detalleAlmacenUseCase = detalleAlmacenUseCase;
    }

    public ResponseEditarDetalleInventario editarDetalleInventario(long idInventarioCabezera, RequestEditarDetalleInventario request, long idAlmacen) {
        try {
            //get detalle cabezera
            ResponseDetalleInventario responseBDCabezera = detalleInventarioCabezeraUseCase.DetalleInventario(idInventarioCabezera);
            if (!responseBDCabezera.isExito() || responseBDCabezera.getInventario()   == null) {
                throw new IllegalArgumentException("El inventario no existe.");
            }
            EstadoInventario estado = responseBDCabezera.getInventario().getEstado();
            //verificar que no estea cerrado
            if (estado.equals(EstadoInventario.AJUSTADO)) {
                throw new IllegalArgumentException("El inventario ya esta ajustado no se pueden editar los detalles.");
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
            ResponseEditarDetalleInventario response = detalleinventatioService.editarDetalleIventario(idInventarioCabezera,request);
            if (response.isExito()) {
            }

            return response;

        } catch (IllegalArgumentException | SecurityException e) {
            ResponseEditarDetalleInventario response = new ResponseEditarDetalleInventario();
            response.setExito(false);
            response.setMessage(e.getMessage());
            return response;
        } catch (Exception e) {
            String mensajeError = "Error inesperado al editar el detalle del inventario: " + e.getMessage();
            System.err.println("[ERROR] " + mensajeError);
            ResponseEditarDetalleInventario response = new ResponseEditarDetalleInventario();
            response.setExito(false);
            response.setMessage(mensajeError);
            return response;
        }
    }
}