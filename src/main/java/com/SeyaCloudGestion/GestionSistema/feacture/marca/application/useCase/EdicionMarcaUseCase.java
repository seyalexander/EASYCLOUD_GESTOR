package com.SeyaCloudGestion.GestionSistema.feacture.marca.application.useCase;

import com.SeyaCloudGestion.GestionSistema.feacture.marca.application.dto.request.RequestDetalleMarca;
import com.SeyaCloudGestion.GestionSistema.feacture.marca.application.dto.request.RequestEditarAllMarca;
import com.SeyaCloudGestion.GestionSistema.feacture.marca.application.dto.response.ResponseDetalleMarca;
import com.SeyaCloudGestion.GestionSistema.feacture.marca.application.dto.response.ResponseEditarAllMarca;
import com.SeyaCloudGestion.GestionSistema.feacture.marca.domain.services.MarcaService;
import com.SeyaCloudGestion.GestionSistema.feacture.marca.domain.validations.VerificarCambiosMarca;
import org.springframework.stereotype.Component;

@Component
public class EdicionMarcaUseCase {
    private final MarcaService marcaService;
    private final VerificarCambiosMarca verificarCambiosMarca;

    public EdicionMarcaUseCase(MarcaService marcaService, VerificarCambiosMarca verificarCambiosMarca) {
        this.marcaService = marcaService;
        this.verificarCambiosMarca = verificarCambiosMarca;
    }

    public ResponseEditarAllMarca EdicionAllMarca(RequestEditarAllMarca request) {
        try {
            RequestDetalleMarca requestDetalle = new RequestDetalleMarca();
            requestDetalle.setIdMarca(request.getIdMarca());

            ResponseDetalleMarca detalleBD= marcaService.DetalleMarca(requestDetalle);

            if (!detalleBD.isExito() || detalleBD.getMarca() == null) {
                throw new IllegalArgumentException("La marca no existe.");
            }

            if (!verificarCambiosMarca.verificarCambios(detalleBD.getMarca(), request)) {
                throw new IllegalArgumentException("No se detectaron cambios para actualizar.");
            }

            ResponseEditarAllMarca response = marcaService.EditarAllMarca(request);
            if (response.isExito()) {

            }

            return response;

        } catch (IllegalArgumentException | SecurityException e) {
            ResponseEditarAllMarca response = new ResponseEditarAllMarca();
            response.setExito(false);
            response.setMessage(e.getMessage());
            return response;
        } catch (Exception e) {
            String mensajeError = "Error inesperado al editar la marca: " + e.getMessage();
            System.err.println("[ERROR] " + mensajeError);
            ResponseEditarAllMarca response = new ResponseEditarAllMarca();
            response.setExito(false);
            response.setMessage(mensajeError);
            return response;
        }
    }
}