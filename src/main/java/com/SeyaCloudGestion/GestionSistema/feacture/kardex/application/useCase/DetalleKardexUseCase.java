package com.SeyaCloudGestion.GestionSistema.feacture.kardex.application.useCase;

import com.SeyaCloudGestion.GestionSistema.feacture.articulos.application.dto.response.ResponseDetalleArticulo;
import com.SeyaCloudGestion.GestionSistema.feacture.articulos.application.useCase.DetalleArticuloUseCase;
import com.SeyaCloudGestion.GestionSistema.feacture.kardex.application.dto.request.RequestDetalleKardex;
import com.SeyaCloudGestion.GestionSistema.feacture.kardex.application.dto.response.ResponseDetalleKardex;
import com.SeyaCloudGestion.GestionSistema.feacture.kardex.domain.services.KardexService;
import org.springframework.stereotype.Component;

@Component
public class DetalleKardexUseCase {

    private final KardexService kardexService;
    private final DetalleArticuloUseCase detalleArticuloUseCase;

    public DetalleKardexUseCase(KardexService kardexService, DetalleArticuloUseCase detalleArticuloUseCase) {
        this.kardexService = kardexService;
        this.detalleArticuloUseCase = detalleArticuloUseCase;
    }

    public ResponseDetalleKardex detalleKardex(long idArticulo) {
        try {
            //get articulo
            ResponseDetalleArticulo detalleBDArt = detalleArticuloUseCase.DetalleArticulo(idArticulo);

            if (!detalleBDArt.isExito() || detalleBDArt.getArticulos() == null) {
                throw new IllegalArgumentException("El articulo no existe.");
            }

            RequestDetalleKardex request = new RequestDetalleKardex();
            request.setIdArticulo(idArticulo);

            ResponseDetalleKardex response = kardexService.DetalleKardex(request);
            if (response.isExito()) {}

            return response;

        } catch (IllegalArgumentException | SecurityException e) {
            ResponseDetalleKardex response = new ResponseDetalleKardex();
            response.setExito(false);
            response.setMessage(e.getMessage());
            return response;
        } catch (Exception e) {
            String mensajeError = "Error inesperado al obtener el detalle del movimiento de Kardex: " + e.getMessage();
            System.err.println("[ERROR] " + mensajeError);
            ResponseDetalleKardex response = new ResponseDetalleKardex();
            response.setExito(false);
            response.setMessage(mensajeError);
            return response;
        }
    }
}