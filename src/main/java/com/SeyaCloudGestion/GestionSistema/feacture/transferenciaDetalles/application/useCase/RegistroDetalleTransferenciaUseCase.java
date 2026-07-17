package com.SeyaCloudGestion.GestionSistema.feacture.transferenciaDetalles.application.useCase;

import com.SeyaCloudGestion.GestionSistema.feacture.articulos.application.dto.response.ResponseDetalleArticulo;
import com.SeyaCloudGestion.GestionSistema.feacture.articulos.application.useCase.DetalleArticuloUseCase;
import com.SeyaCloudGestion.GestionSistema.feacture.transferenciaDetalles.application.dto.request.RequestRegistroDetalleTransferencia;
import com.SeyaCloudGestion.GestionSistema.feacture.transferenciaDetalles.application.dto.response.ResponseRegistroTransferenciaDetalle;
import com.SeyaCloudGestion.GestionSistema.feacture.transferenciaDetalles.domain.services.TransferenciaDetalleService;
import org.springframework.stereotype.Component;

@Component
public class RegistroDetalleTransferenciaUseCase {

    private final TransferenciaDetalleService transferenciaDetalleService;
    private final DetalleArticuloUseCase detalleArticuloUseCase;

    public RegistroDetalleTransferenciaUseCase(TransferenciaDetalleService transferenciaDetalleService, DetalleArticuloUseCase detalleArticuloUseCase) {
        this.transferenciaDetalleService = transferenciaDetalleService;
        this.detalleArticuloUseCase = detalleArticuloUseCase;
    }

    public ResponseRegistroTransferenciaDetalle RegistroDetalleTransferencia(long idTransferencia, RequestRegistroDetalleTransferencia request,double costoUnitario) {
        try {
            //get articulo
            ResponseDetalleArticulo detalleBDArt = detalleArticuloUseCase.DetalleArticulo(request.getIdArticulo());
            if (!detalleBDArt.isExito() || detalleBDArt.getArticulos() == null) {
                throw new IllegalArgumentException("El articulo no existe.");
            }

            ResponseRegistroTransferenciaDetalle response = transferenciaDetalleService.RegistroDetalleTransferencia(idTransferencia, request,costoUnitario);

            if (response.isExito()) {
            }

            return response;

        } catch (IllegalArgumentException | SecurityException e) {
            ResponseRegistroTransferenciaDetalle response = new ResponseRegistroTransferenciaDetalle();
            response.setExito(false);
            response.setMessage(e.getMessage());
            return response;
        } catch (Exception e) {
            String mensajeError = "Error inesperado al registrar el detalle de la transferencia: " + e.getMessage();
            System.err.println("[ERROR] " + mensajeError);
            ResponseRegistroTransferenciaDetalle response = new ResponseRegistroTransferenciaDetalle();
            response.setExito(false);
            response.setMessage(mensajeError);
            return response;
        }
    }
}