package com.SeyaCloudGestion.GestionSistema.feacture.inventarios.application.useCase;

import com.SeyaCloudGestion.GestionSistema.feacture.detalleInventario.application.dto.request.RequestEditarDetalleInventario;
import com.SeyaCloudGestion.GestionSistema.feacture.detalleInventario.application.dto.response.ResponseEditarDetalleInventario;
import com.SeyaCloudGestion.GestionSistema.feacture.detalleInventario.application.dto.response.ResponseListaDetalleInventario;
import com.SeyaCloudGestion.GestionSistema.feacture.detalleInventario.application.useCase.EdicionDetalleInventarioUseCase;
import com.SeyaCloudGestion.GestionSistema.feacture.detalleInventario.application.useCase.ListaDetalleInventarioUseCase;
import com.SeyaCloudGestion.GestionSistema.feacture.inventarios.application.dto.request.RequestConteoFisicoInventario;
import com.SeyaCloudGestion.GestionSistema.feacture.inventarios.application.dto.response.ResponseConteoFisicoInventario;
import com.SeyaCloudGestion.GestionSistema.feacture.inventarios.application.dto.response.ResponseDetalleInventario;
import com.SeyaCloudGestion.GestionSistema.feacture.inventarios.domain.services.InventarioService;
import com.SeyaCloudGestion.GestionSistema.feacture.inventarios.infraestructure.persistence.model.EstadoInventario;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class ConteoFisicoInventarioUseCase {

    private final InventarioService inventarioService;
    private final DetalleInventarioUseCase detalleInventarioUseCase;
    private final ListaDetalleInventarioUseCase listaDetalleInventarioUseCase;
    private final EdicionDetalleInventarioUseCase edicionDetalleInventarioUseCase;
    public ConteoFisicoInventarioUseCase(
            InventarioService inventarioService, DetalleInventarioUseCase detalleInventarioUseCase, ListaDetalleInventarioUseCase listaDetalleInventarioUseCase, EdicionDetalleInventarioUseCase edicionDetalleInventarioUseCase
    ) {
        this.inventarioService = inventarioService;
        this.detalleInventarioUseCase = detalleInventarioUseCase;
        this.listaDetalleInventarioUseCase = listaDetalleInventarioUseCase;
        this.edicionDetalleInventarioUseCase = edicionDetalleInventarioUseCase;
    }

    public ResponseConteoFisicoInventario ConteoFisicoInventario(RequestConteoFisicoInventario request) {
        try {
            //get detalle cabezera
            ResponseDetalleInventario responseBDCabezera = detalleInventarioUseCase.DetalleInventario(request.getIdInventarioCabecera());
            if (!responseBDCabezera.isExito() || responseBDCabezera.getInventario() == null) {
                throw new IllegalArgumentException("El inventario no existe.");
            }
            EstadoInventario estado = responseBDCabezera.getInventario().getEstado();
            //verificar que no estea cerrado
            if (estado.equals(EstadoInventario.AJUSTADO)) {
                throw new IllegalArgumentException("El inventario ya esta ajustado no se pueden editar los detalles");
            }
            //traer los detalles de la bd
            ResponseListaDetalleInventario responseBDDetalles = listaDetalleInventarioUseCase.listarDetalleInventario(request.getIdInventarioCabecera(),responseBDCabezera.getInventario().getIdAlmacen());
            if (!responseBDDetalles.isExito() || responseBDDetalles.getDetalles() == null || responseBDDetalles.getDetalles().isEmpty()) {
                throw new IllegalArgumentException("El inventario no tiene detalles registrados, no se puede cerrar.");
            }
            //obtenemos todos los id para verificar que sean los id correcpondientes
            Set<Long> idsArticulosValidos = new HashSet<>();
            for (var detalleBD : responseBDDetalles.getDetalles()) {
                idsArticulosValidos.add(detalleBD.getIdArticulo());
            }
            //recorremos la lista verificacmos y insertamos
            for (RequestEditarDetalleInventario item : request.getDetalles()) {

                if (!idsArticulosValidos.contains(item.getIdArticulo())) {
                    throw new IllegalArgumentException("El artículo con ID " + item.getIdArticulo() +
                            " no pertenece a los detalles iniciales de este inventario. Operación abortada.");
                }

                // Mapeas los datos al DTO de tu service
                RequestEditarDetalleInventario dtoService = new RequestEditarDetalleInventario();
                dtoService.setIdArticulo(item.getIdArticulo());
                dtoService.setStockFisico(item.getStockFisico());

                // Llama al service para aplicar el SP sp_ActualizarStockFisicoDetalle
                ResponseEditarDetalleInventario resUpdate = edicionDetalleInventarioUseCase.editarDetalleInventario(request.getIdInventarioCabecera(), dtoService, responseBDCabezera.getInventario().getIdAlmacen());

                if (!resUpdate.isExito()) {
                    throw new IllegalArgumentException("Error al actualizar el artículo con ID " + item.getIdArticulo() + ": " + resUpdate.getMessage());
                }
            }

            ResponseConteoFisicoInventario response = inventarioService.ConteoFisicoInventario(request);
            if (response.isExito()) {
            }

            return response;

        } catch (IllegalArgumentException | SecurityException e) {
            ResponseConteoFisicoInventario response = new ResponseConteoFisicoInventario();
            response.setExito(false);
            response.setMessage(e.getMessage());
            return response;
        } catch (Exception e) {
            String mensajeError = "Error inesperado al cerrar el inventario: " + e.getMessage();
            System.err.println("[ERROR] " + mensajeError);
            ResponseConteoFisicoInventario response = new ResponseConteoFisicoInventario();
            response.setExito(false);
            response.setMessage(mensajeError);
            return response;
        }
    }
}