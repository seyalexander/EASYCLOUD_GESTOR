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
    private final DetalleMarcaUseCase detalleMarcaUseCase;
    public EdicionMarcaUseCase(MarcaService marcaService, VerificarCambiosMarca verificarCambiosMarca, DetalleMarcaUseCase detalleMarcaUseCase) {
        this.marcaService = marcaService;
        this.verificarCambiosMarca = verificarCambiosMarca;
        this.detalleMarcaUseCase = detalleMarcaUseCase;
    }

    public ResponseEditarAllMarca EdicionAllMarca(RequestEditarAllMarca request) {
        try {
            //marca
            ResponseDetalleMarca detalleBDMarca= detalleMarcaUseCase.detalleMarcas(request.getIdMarca());

            if (!detalleBDMarca.isExito() || detalleBDMarca.getMarca() == null) {
                throw new IllegalArgumentException("La marca no existe.");
            }

            if (!verificarCambiosMarca.verificarCambios(detalleBDMarca.getMarca(), request)) {
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