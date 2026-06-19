package com.SeyaCloudGestion.GestionSistema.feacture.kardex.application.useCase;

import com.SeyaCloudGestion.GestionSistema.feacture.articulos.application.dto.response.ResponseDetalleArticulo;
import com.SeyaCloudGestion.GestionSistema.feacture.articulos.application.useCase.DetalleArticuloUseCase;
import com.SeyaCloudGestion.GestionSistema.feacture.kardex.application.dto.request.RequestListaKardex;
import com.SeyaCloudGestion.GestionSistema.feacture.kardex.application.dto.response.ResponseListaKardex;
import com.SeyaCloudGestion.GestionSistema.feacture.kardex.domain.services.KardexService;
import org.springframework.stereotype.Component;

@Component
public class ListaKardexUseCase {

    private final KardexService kardexService;
    private final DetalleArticuloUseCase detalleArticuloUseCase;

    public ListaKardexUseCase(KardexService kardexService, DetalleArticuloUseCase detalleArticuloUseCase) {
        this.kardexService = kardexService;
        this.detalleArticuloUseCase = detalleArticuloUseCase;
    }

    public ResponseListaKardex listaKardex(RequestListaKardex request) {
        try {
            //get articulo
            ResponseDetalleArticulo detalleBDArt = detalleArticuloUseCase.DetalleArticulo(request.getIdArticulo());

            if (!detalleBDArt.isExito() || detalleBDArt.getArticulos() == null) {
                throw new IllegalArgumentException("El articulo no existe.");
            }

            ResponseListaKardex response = kardexService.listaKardex(request);
            if (response.isExito()) {}

            return response;

        } catch (IllegalArgumentException | SecurityException e) {
            ResponseListaKardex response = new ResponseListaKardex();
            response.setExito(false);
            response.setMessage(e.getMessage());
            response.setKardex(java.util.List.of()); // Mantiene la consistencia de listas vacías en lugar de null
            return response;
        } catch (Exception e) {
            String mensajeError = "Error inesperado al listar los movimientos de Kardex: " + e.getMessage();
            System.err.println("[ERROR] " + mensajeError);
            ResponseListaKardex response = new ResponseListaKardex();
            response.setExito(false);
            response.setMessage(mensajeError);
            response.setKardex(java.util.List.of()); // Mantiene la consistencia de listas vacías en lugar de null
            return response;
        }
    }
}