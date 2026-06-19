package com.SeyaCloudGestion.GestionSistema.feacture.kardex.application.useCase;

import com.SeyaCloudGestion.GestionSistema.feacture.articulos.application.dto.response.ResponseDetalleArticulo;
import com.SeyaCloudGestion.GestionSistema.feacture.articulos.application.useCase.DetalleArticuloUseCase;
import com.SeyaCloudGestion.GestionSistema.feacture.kardex.application.dto.request.RequestRegistroKardex;
import com.SeyaCloudGestion.GestionSistema.feacture.kardex.application.dto.response.ResponseRegistroKardex;
import com.SeyaCloudGestion.GestionSistema.feacture.kardex.domain.services.KardexService;
import org.springframework.stereotype.Component;

@Component
public class RegistroKardexUseCase {

    private final KardexService kardexService;
    private final DetalleArticuloUseCase detalleArticuloUseCase;

    public RegistroKardexUseCase(KardexService kardexService, DetalleArticuloUseCase detalleArticuloUseCase) {
        this.kardexService = kardexService;
        this.detalleArticuloUseCase = detalleArticuloUseCase;
    }

    public ResponseRegistroKardex registroKardex(RequestRegistroKardex request) {
        try {
            //get articulo
            ResponseDetalleArticulo detalleBDArt = detalleArticuloUseCase.DetalleArticulo(request.getIdArticulo());

            if (!detalleBDArt.isExito() || detalleBDArt.getArticulos() == null) {
                throw new IllegalArgumentException("El articulo no existe.");
            }

            ResponseRegistroKardex response = kardexService.RegistroKardex(request);
            if (response.isExito()) {}

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