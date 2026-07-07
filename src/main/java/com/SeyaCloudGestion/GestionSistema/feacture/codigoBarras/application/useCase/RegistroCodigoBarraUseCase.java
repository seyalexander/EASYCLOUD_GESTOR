package com.SeyaCloudGestion.GestionSistema.feacture.codigoBarras.application.useCase;

import com.SeyaCloudGestion.GestionSistema.feacture.articulos.application.dto.response.ResponseDetalleArticulo;
import com.SeyaCloudGestion.GestionSistema.feacture.articulos.application.useCase.DetalleArticuloUseCase;
import com.SeyaCloudGestion.GestionSistema.feacture.codigoBarras.application.dto.request.RequestRegistroCodigoBarra;
import com.SeyaCloudGestion.GestionSistema.feacture.codigoBarras.application.dto.response.ResponseRegistroCodigoBarra;
import com.SeyaCloudGestion.GestionSistema.feacture.codigoBarras.domain.services.CodigoBarraService;
import com.SeyaCloudGestion.GestionSistema.feacture.codigoBarras.domain.validations.CodigoBarraValidator;
import org.springframework.stereotype.Component;

@Component
public class RegistroCodigoBarraUseCase {
    private  final CodigoBarraService codigoBarraService;
    private final DetalleArticuloUseCase detalleArticuloUseCase;

    public RegistroCodigoBarraUseCase(CodigoBarraService codigoBarraService, DetalleArticuloUseCase detalleArticuloUseCase) {
        this.codigoBarraService = codigoBarraService;
        this.detalleArticuloUseCase = detalleArticuloUseCase;
    }
    public ResponseRegistroCodigoBarra RegistroCodigoBarra(RequestRegistroCodigoBarra request) {
        try {
            //articulo
            ResponseDetalleArticulo detalleBDArticulo= detalleArticuloUseCase.DetalleArticulo(request.getIdArticulo());

            if (!detalleBDArticulo.isExito() || detalleBDArticulo.getArticulos() == null) {
                throw new IllegalArgumentException("El articulo no existe.");
            }

            CodigoBarraValidator.validarDependenciasParaRegistro(detalleBDArticulo);

            ResponseRegistroCodigoBarra response = codigoBarraService.RegistroCodigoBarra(request);

            if (response.isExito()) {}

            return response;

        } catch (IllegalArgumentException | SecurityException e) {
            ResponseRegistroCodigoBarra response = new ResponseRegistroCodigoBarra();
            response.setExito(false);
            response.setMessage(e.getMessage());
            return response;
        } catch (Exception e) {
            String mensajeError = "Error inesperado al registrar el código de barra: " + e.getMessage();
            System.err.println("[ERROR] " + mensajeError);

            ResponseRegistroCodigoBarra response = new ResponseRegistroCodigoBarra();
            response.setExito(false);
            response.setMessage(mensajeError);
            return response;
        }
    }
}
