package com.SeyaCloudGestion.GestionSistema.feacture.articulos.application.useCase;

import com.SeyaCloudGestion.GestionSistema.feacture.articulos.application.dto.request.RequestRegistroArticulo;
import com.SeyaCloudGestion.GestionSistema.feacture.articulos.application.dto.response.ResponseRegistroArticulo;
import com.SeyaCloudGestion.GestionSistema.feacture.articulos.domain.services.ArticulosService;
import org.springframework.stereotype.Component;

@Component
public class RegistroArticuloUseCase {

    private final ArticulosService articulosService;

    public RegistroArticuloUseCase(
            ArticulosService articulosService
    ){
        this.articulosService = articulosService;
    }

    public ResponseRegistroArticulo RegistrarArticulo(RequestRegistroArticulo request) {
        try {
            // Obtener idUsuario del token
            //long userId = SecurityUtils.getCurrentUserId();
            long userId = 1L;
            long idEmpresa = 1L;


            ResponseRegistroArticulo response = articulosService.registrarArticulo(request);
            if(response.isExito()){}

            return response;

        }catch (IllegalArgumentException | SecurityException e){
            ResponseRegistroArticulo response = new ResponseRegistroArticulo();
            response.setExito(false);
            response.setMessage(e.getMessage());
            return response;
        }
        catch (Exception e){
            String mensajeError = "Error inesperado al registrar un artículo: " + e.getMessage();
            System.err.println("[ERROR] " + mensajeError);
            ResponseRegistroArticulo response = new ResponseRegistroArticulo();
            response.setExito(false);
            response.setMessage(mensajeError);
            return response;
        }
    }
}
