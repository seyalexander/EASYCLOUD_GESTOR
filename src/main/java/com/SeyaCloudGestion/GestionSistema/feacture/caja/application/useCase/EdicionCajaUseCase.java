package com.SeyaCloudGestion.GestionSistema.feacture.caja.application.useCase;

import com.SeyaCloudGestion.GestionSistema.feacture.caja.application.dto.request.RequestDetalleCaja;
import com.SeyaCloudGestion.GestionSistema.feacture.caja.application.dto.request.RequestEditarAllCaja;
import com.SeyaCloudGestion.GestionSistema.feacture.caja.application.dto.response.ResponseDetalleCaja;
import com.SeyaCloudGestion.GestionSistema.feacture.caja.application.dto.response.ResponseEditarAllCaja;
import com.SeyaCloudGestion.GestionSistema.feacture.caja.domain.services.CajaService;
import com.SeyaCloudGestion.GestionSistema.feacture.caja.domain.validations.VerificarCambiosCaja;
import org.springframework.stereotype.Component;

@Component
public class EdicionCajaUseCase {

    private final CajaService cajaService;
    private final VerificarCambiosCaja verificarCambiosCaja;

    public EdicionCajaUseCase(
            CajaService cajaService,
            VerificarCambiosCaja verificarCambiosCaja
    ) {
        this.cajaService = cajaService;
        this.verificarCambiosCaja = verificarCambiosCaja;
    }

    public ResponseEditarAllCaja EdicionAllCaja(RequestEditarAllCaja request) {
        try {
            //get id
            RequestDetalleCaja requestDetalle = new RequestDetalleCaja();
            requestDetalle.setIdCaja(request.getIdCaja());

            ResponseDetalleCaja detalleBD = cajaService.DetalleCaja(requestDetalle);

            if (!detalleBD.isExito() || detalleBD.getCaja() == null) {
                throw new IllegalArgumentException("La caja no existe.");
            }

            // camvbios
            if (!verificarCambiosCaja.verificarCambios(detalleBD.getCaja(), request)) {
                throw new IllegalArgumentException("No se detectaron cambios para actualizar.");
            }

            ResponseEditarAllCaja response = cajaService.EditarAllCaja(request);
            if (response.isExito()) {}

            return response;

        } catch (IllegalArgumentException | SecurityException e) {
            ResponseEditarAllCaja response = new ResponseEditarAllCaja();
            response.setExito(false);
            response.setMessage(e.getMessage());
            return response;
        } catch (Exception e) {
            String mensajeError = "Error inesperado al actualizar la caja: " + e.getMessage();
            System.err.println("[ERROR] " + mensajeError);

            ResponseEditarAllCaja response = new ResponseEditarAllCaja();
            response.setExito(false);
            response.setMessage(mensajeError);
            return response;
        }
    }
}