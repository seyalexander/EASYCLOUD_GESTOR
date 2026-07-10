package com.SeyaCloudGestion.GestionSistema.feacture.codigoBarras.application.useCase;

import com.SeyaCloudGestion.GestionSistema.common.exceptions.ResourceNotFoundException;
import com.SeyaCloudGestion.GestionSistema.feacture.codigoBarras.application.dto.request.RequestDetalleCodigoBarra;
import com.SeyaCloudGestion.GestionSistema.feacture.codigoBarras.application.dto.request.RequestEditarAllCodigoBarra;
import com.SeyaCloudGestion.GestionSistema.feacture.codigoBarras.application.dto.response.ResponseDetalleCodigoBarra;
import com.SeyaCloudGestion.GestionSistema.feacture.codigoBarras.application.dto.response.ResponseEditarAllCodigoBarra;
import com.SeyaCloudGestion.GestionSistema.feacture.codigoBarras.domain.services.CodigoBarraService;
import com.SeyaCloudGestion.GestionSistema.feacture.codigoBarras.domain.validations.VerificarCambiosCodigoBarra;
import org.springframework.stereotype.Component;

@Component
public class EdicionAllCodigoBarraUseCase {
    private  final CodigoBarraService codigoBarraService;
    private final VerificarCambiosCodigoBarra verificarCambiosCodigoBarra;
    private final DetalleCodigoBarraUseCase detalleCodigoBarraUseCase;
    public EdicionAllCodigoBarraUseCase(CodigoBarraService codigoBarraService, VerificarCambiosCodigoBarra verificarCambiosCodigoBarra, DetalleCodigoBarraUseCase detalleCodigoBarraUseCase) {
        this.codigoBarraService = codigoBarraService;
        this.verificarCambiosCodigoBarra = verificarCambiosCodigoBarra;
        this.detalleCodigoBarraUseCase = detalleCodigoBarraUseCase;
    }
    public ResponseEditarAllCodigoBarra EdicionAllCodigoBarra(RequestEditarAllCodigoBarra request) {
        try {
            //verificar el id
            ResponseDetalleCodigoBarra detalleBDCodigoBarra= detalleCodigoBarraUseCase.DetalleCodigoBarra(request.getIdCodigoBarra());

            if (!detalleBDCodigoBarra.isExito() || detalleBDCodigoBarra.getCodigoBarra() == null) {
                throw new ResourceNotFoundException("El Id no existe.");
            }
            //validar edicion
            if (!verificarCambiosCodigoBarra.verificarCambios(detalleBDCodigoBarra.getCodigoBarra(),request)) {
                throw new ResourceNotFoundException("No se detectaron cambios para actualizar.");
            }

            //obtenemos el id del articulo
            long idArticulo = detalleBDCodigoBarra.getCodigoBarra().getIdArticulo();


            ResponseEditarAllCodigoBarra response = codigoBarraService.EditarAllCodigoBarra(request,idArticulo);

            if (response.isExito()) {}

            return response;

        } catch (IllegalArgumentException | SecurityException e) {
            ResponseEditarAllCodigoBarra response = new ResponseEditarAllCodigoBarra();
            response.setExito(false);
            response.setMessage(e.getMessage());
            return response;
        } catch (Exception e) {
            String mensajeError = "Error inesperado al actualizar el código de barra: " + e.getMessage();
            System.err.println("[ERROR] " + mensajeError);

            ResponseEditarAllCodigoBarra response = new ResponseEditarAllCodigoBarra();
            response.setExito(false);
            response.setMessage(mensajeError);
            return response;
        }
    }
}
