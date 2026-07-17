package com.SeyaCloudGestion.GestionSistema.feacture.inventarios.application.useCase;

import com.SeyaCloudGestion.GestionSistema.feacture.almacenes.application.dto.response.ResponseDetalleAlmacen;
import com.SeyaCloudGestion.GestionSistema.feacture.almacenes.application.useCase.DetalleAlmacenUseCase;
import com.SeyaCloudGestion.GestionSistema.feacture.detalleInventario.application.dto.request.RequestRegistroDetalleInventario;
import com.SeyaCloudGestion.GestionSistema.feacture.detalleInventario.application.dto.response.ResponseRegistroDetalleInventario;
import com.SeyaCloudGestion.GestionSistema.feacture.detalleInventario.application.useCase.RegistroDetalleInventarioUseCase;
import com.SeyaCloudGestion.GestionSistema.feacture.inventarios.application.dto.request.RequestRegistroInventario;
import com.SeyaCloudGestion.GestionSistema.feacture.inventarios.application.dto.response.ResponseRegistroInventario;
import com.SeyaCloudGestion.GestionSistema.feacture.inventarios.domain.services.InventarioService;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class RegistroInventarioUseCase {

    private final InventarioService inventarioService;
    private final DetalleAlmacenUseCase detalleAlmacenUseCase;
    private final RegistroDetalleInventarioUseCase registroDetalleInventarioUseCase;
    public RegistroInventarioUseCase(
            InventarioService inventarioService, DetalleAlmacenUseCase detalleAlmacenUseCase, RegistroDetalleInventarioUseCase registroDetalleInventarioUseCase
    ) {
        this.inventarioService = inventarioService;
        this.detalleAlmacenUseCase = detalleAlmacenUseCase;
        this.registroDetalleInventarioUseCase = registroDetalleInventarioUseCase;
    }

    public ResponseRegistroInventario RegistroInventario(RequestRegistroInventario request) {
        try {
            //get almacen
            ResponseDetalleAlmacen detalleBDAlm = detalleAlmacenUseCase.DetalleAlmacenes(request.getIdAlmacen());
            if (!detalleBDAlm.isExito() || detalleBDAlm.getAlmacen() == null) {
                throw new IllegalArgumentException("El almacen no existe.");
            }
            //verificaqar duplicado
            Set<Long> articulosProcesados = new HashSet<>();

            for (RequestRegistroDetalleInventario detalle : request.getDetalles()) {
                long idArticulo = detalle.getIdArticulo();

                if (!articulosProcesados.add(idArticulo)) {
                    throw new IllegalArgumentException("El artículo con ID " + idArticulo + " está duplicado en la lista de detalles.");
                }
            }
            ResponseRegistroInventario response = inventarioService.RegistroInventario(request);
            if (response.isExito()) {
                long idInventarioCabezeraGenerado = response.getIdInventarioCabecera();

                for (RequestRegistroDetalleInventario detalle : request.getDetalles()) {

                    ResponseRegistroDetalleInventario responseDetalle = registroDetalleInventarioUseCase.registrarDetalleInventario(idInventarioCabezeraGenerado, detalle, request.getIdAlmacen());
                    if (!responseDetalle.isExito()) {
                        throw new IllegalArgumentException("Error al registrar el detalle del inventario: " + responseDetalle.getMessage());
                    }
                }
            }

            return response;

        } catch (IllegalArgumentException | SecurityException e) {
            ResponseRegistroInventario response = new ResponseRegistroInventario();
            response.setExito(false);
            response.setMessage(e.getMessage());
            return response;
        } catch (Exception e) {
            String mensajeError = "Error inesperado al registrar los inventarios: " + e.getMessage();
            System.err.println("[ERROR] " + mensajeError);
            ResponseRegistroInventario response = new ResponseRegistroInventario();
            response.setExito(false);
            response.setMessage(mensajeError);
            return response;
        }
    }
}