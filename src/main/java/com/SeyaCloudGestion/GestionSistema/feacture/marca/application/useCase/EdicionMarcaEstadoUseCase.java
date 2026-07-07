package com.SeyaCloudGestion.GestionSistema.feacture.marca.application.useCase;

import com.SeyaCloudGestion.GestionSistema.feacture.marca.application.dto.request.RequestEditarEstadoMarca;
import com.SeyaCloudGestion.GestionSistema.feacture.marca.application.dto.response.ResponseDetalleMarca;
import com.SeyaCloudGestion.GestionSistema.feacture.marca.application.dto.response.ResponseEditarEstadoMarca;
import com.SeyaCloudGestion.GestionSistema.feacture.marca.domain.services.MarcaService;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class EdicionMarcaEstadoUseCase {
    private final MarcaService marcaService;
    private final DetalleMarcaUseCase detalleMarcaUseCase;

    public EdicionMarcaEstadoUseCase(MarcaService marcaService, DetalleMarcaUseCase detalleMarcaUseCase) {
        this.marcaService = marcaService;
        this.detalleMarcaUseCase = detalleMarcaUseCase;
    }

    public ResponseEditarEstadoMarca AnularMarca(Long idMarca) {
        try {
            //marca
            ResponseDetalleMarca detalleBDMarca= detalleMarcaUseCase.detalleMarcas(idMarca);

            if (!detalleBDMarca.isExito() || detalleBDMarca.getMarca() == null) {
                throw new IllegalArgumentException("La marca no existe.");
            }

            if (Objects.equals(detalleBDMarca.getMarca().getEstado(), 0)) {
                throw new IllegalArgumentException("La familia ya se encuentra anulada.");
            }

            RequestEditarEstadoMarca request = new RequestEditarEstadoMarca();
            request.setIdMarca(idMarca);
            ResponseEditarEstadoMarca response = marcaService.EditarEstadoMarca(request,0);
            if(response.isExito()){}

            return response;

        }catch (IllegalArgumentException | SecurityException e){
            ResponseEditarEstadoMarca response = new ResponseEditarEstadoMarca();
            response.setExito(false);
            response.setMessage(e.getMessage());
            return response;
        }
        catch (Exception e){
            String mensajeError = "Error inesperado al actualizar el estado de la marca: " + e.getMessage();
            System.err.println("[ERROR] " + mensajeError);
            ResponseEditarEstadoMarca response = new ResponseEditarEstadoMarca();
            response.setExito(false);
            response.setMessage(mensajeError);
            return response;
        }
    }

    public ResponseEditarEstadoMarca ActivarMarca(Long idMarca) {
        try {
            //marca
            ResponseDetalleMarca detalleBDMarca= detalleMarcaUseCase.detalleMarcas(idMarca);

            if (!detalleBDMarca.isExito() || detalleBDMarca.getMarca() == null) {
                throw new IllegalArgumentException("La marca no existe.");
            }

            if (Objects.equals(detalleBDMarca.getMarca().getEstado(), 1)) {
                throw new IllegalArgumentException("La familia ya se encuentra activada.");
            }

            RequestEditarEstadoMarca request = new RequestEditarEstadoMarca();
            request.setIdMarca(idMarca);
            ResponseEditarEstadoMarca response = marcaService.EditarEstadoMarca(request,1);
            if(response.isExito()){}

            return response;

        }catch (IllegalArgumentException | SecurityException e){
            ResponseEditarEstadoMarca response = new ResponseEditarEstadoMarca();
            response.setExito(false);
            response.setMessage(e.getMessage());
            return response;
        }
        catch (Exception e){
            String mensajeError = "Error inesperado al actualizar el estado de la marca: " + e.getMessage();
            System.err.println("[ERROR] " + mensajeError);
            ResponseEditarEstadoMarca response = new ResponseEditarEstadoMarca();
            response.setExito(false);
            response.setMessage(mensajeError);
            return response;
        }
    }
}