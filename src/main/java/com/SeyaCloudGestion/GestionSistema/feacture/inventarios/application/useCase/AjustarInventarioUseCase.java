package com.SeyaCloudGestion.GestionSistema.feacture.inventarios.application.useCase;

import com.SeyaCloudGestion.GestionSistema.feacture.ajustes.application.dto.response.ResponseRegistroFullAjuste;
import com.SeyaCloudGestion.GestionSistema.feacture.ajustes.application.useCase.RegistroFullAjusteUseCase;
import com.SeyaCloudGestion.GestionSistema.feacture.detalleInventario.application.dto.response.ResponseListaDetalleInventario;
import com.SeyaCloudGestion.GestionSistema.feacture.detalleInventario.application.useCase.ListaDetalleInventarioUseCase;
import com.SeyaCloudGestion.GestionSistema.feacture.inventarios.application.dto.request.RequestAjustarInventario;
import com.SeyaCloudGestion.GestionSistema.feacture.inventarios.application.dto.response.ResponseAjustarInventario;
import com.SeyaCloudGestion.GestionSistema.feacture.inventarios.application.dto.response.ResponseDetalleInventario;
import com.SeyaCloudGestion.GestionSistema.feacture.inventarios.domain.services.InventarioService;
import com.SeyaCloudGestion.GestionSistema.feacture.inventarios.infraestructure.persistence.model.EstadoInventario;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class AjustarInventarioUseCase {

    private final InventarioService inventarioService;
    private final DetalleInventarioUseCase detalleInventarioUseCase;
    private final ListaDetalleInventarioUseCase listaDetalleInventarioUseCase;
    private final RegistroFullAjusteUseCase registroFullAjusteUseCase;
    public AjustarInventarioUseCase(
            InventarioService inventarioService, DetalleInventarioUseCase detalleInventarioUseCase, ListaDetalleInventarioUseCase listaDetalleInventarioUseCase, RegistroFullAjusteUseCase registroFullAjusteUseCase
    ) {
        this.inventarioService = inventarioService;
        this.detalleInventarioUseCase = detalleInventarioUseCase;
        this.listaDetalleInventarioUseCase = listaDetalleInventarioUseCase;
        this.registroFullAjusteUseCase = registroFullAjusteUseCase;
    }

    public ResponseAjustarInventario AjustarInventario(RequestAjustarInventario request) {
        try {
            //get detalle cabezera
            ResponseDetalleInventario responseBDCabezera = detalleInventarioUseCase.DetalleInventario(request.getIdInventarioCabecera());
            if (!responseBDCabezera.isExito() || responseBDCabezera.getInventario() == null) {
                throw new IllegalArgumentException("El inventario no existe.");
            }
            EstadoInventario estado = responseBDCabezera.getInventario().getEstado();
            //verificar que no estea cerrado
            if (estado.equals(EstadoInventario.ABIERTO)) {
                throw new IllegalArgumentException("Aun no se a realizado el conteo fisico del inventario no se pueded ajustar");
            }
            if (estado.equals(EstadoInventario.AJUSTADO)) {
                throw new IllegalArgumentException("El inventario ya esta ajustado no se puede ajustar");
            }
            //traer los detalles de la bd
            ResponseListaDetalleInventario responseBDDetalles = listaDetalleInventarioUseCase.listarDetalleInventario(request.getIdInventarioCabecera(),responseBDCabezera.getInventario().getIdAlmacen());
            if (!responseBDDetalles.isExito() || responseBDDetalles.getDetalles() == null || responseBDDetalles.getDetalles().isEmpty()) {
                throw new IllegalArgumentException("El inventario no tiene detalles registrados, no se puede cerrar.");
            }
            //pasamos al full
            ResponseRegistroFullAjuste responseAjuste = registroFullAjusteUseCase.registroAjustes(request.getIdInventarioCabecera(),responseBDDetalles.getDetalles());
            if (!responseAjuste.isExito()) {
                throw new IllegalArgumentException("Error al Ajustar el inventario " + responseAjuste.getMessage());
            }
            //poner ajustado
            ResponseAjustarInventario response = inventarioService.AjusteInventario(request);
            if (!response.isExito()) {
                throw new IllegalArgumentException("No se pudo actualizar el estado del inventario a AJUSTADO.");
            }
            return response;

        } catch (IllegalArgumentException | SecurityException e) {
            ResponseAjustarInventario response = new ResponseAjustarInventario();
            response.setExito(false);
            response.setMessage(e.getMessage());
            return response;
        } catch (Exception e) {
            String mensajeError = "Error inesperado al cerrar el inventario: " + e.getMessage();
            System.err.println("[ERROR] " + mensajeError);
            ResponseAjustarInventario response = new ResponseAjustarInventario();
            response.setExito(false);
            response.setMessage(mensajeError);
            return response;
        }
    }
}