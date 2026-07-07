package com.SeyaCloudGestion.GestionSistema.feacture.listaPrecios.application.useCase;

import com.SeyaCloudGestion.GestionSistema.feacture.listaPrecios.application.dto.request.RequestEditarAllListaPrecios;
import com.SeyaCloudGestion.GestionSistema.feacture.listaPrecios.application.dto.response.ResponseDetalleListaPrecios;
import com.SeyaCloudGestion.GestionSistema.feacture.listaPrecios.application.dto.response.ResponseEditarAllListaPrecios;
import com.SeyaCloudGestion.GestionSistema.feacture.listaPrecios.domain.services.ListaPreciosService;
import com.SeyaCloudGestion.GestionSistema.feacture.listaPrecios.domain.validations.VerificarCambiosListaPrecios;
import org.springframework.stereotype.Component;

@Component
public class EdicionAllListaPreciosUseCase {
    private final ListaPreciosService listaPreciosService;
    private final VerificarCambiosListaPrecios  verificarCambiosListaPrecios;
    private final DetalleListaPreciosUseCase detalleListaPreciosUseCase;

    public EdicionAllListaPreciosUseCase(ListaPreciosService listaPreciosService, VerificarCambiosListaPrecios verificarCambiosListaPrecios, DetalleListaPreciosUseCase detalleListaPreciosUseCase) {
        this.listaPreciosService = listaPreciosService;
        this.verificarCambiosListaPrecios = verificarCambiosListaPrecios;
        this.detalleListaPreciosUseCase = detalleListaPreciosUseCase;
    }
    public ResponseEditarAllListaPrecios EditarAllListaPrecios(RequestEditarAllListaPrecios request) {
        try {
            //listaprecio
            ResponseDetalleListaPrecios detalleBDPrecio= detalleListaPreciosUseCase.DetalleListaPrecios(request.getIdListaPrecios());

            if (!detalleBDPrecio.isExito() || detalleBDPrecio.getListaPrecios() == null) {
                throw new IllegalArgumentException("La lista precios no existe.");
            }

            if (!verificarCambiosListaPrecios.verificarCambios(detalleBDPrecio.getListaPrecios(), request)) {
                throw new IllegalArgumentException("No se detectaron cambios para actualizar.");
            }
            ResponseEditarAllListaPrecios response = listaPreciosService.EditarAllListaPrecios(request);

            if (response.isExito()) {}

            return response;

        } catch (IllegalArgumentException | SecurityException e) {
            ResponseEditarAllListaPrecios response = new ResponseEditarAllListaPrecios();
            response.setExito(false);
            response.setMessage(e.getMessage());
            return response;
        } catch (Exception e) {
            String mensajeError = "Error inesperado al actualizar la lista de precios: " + e.getMessage();
            System.err.println("[ERROR] " + mensajeError);

            ResponseEditarAllListaPrecios response = new ResponseEditarAllListaPrecios();
            response.setExito(false);
            response.setMessage(mensajeError);
            return response;
        }
    }
}