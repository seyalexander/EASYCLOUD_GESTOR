package com.SeyaCloudGestion.GestionSistema.feacture.listaPrecios.application.useCase;

import com.SeyaCloudGestion.GestionSistema.feacture.familia.application.dto.response.ResponseDetalleFamilia;
import com.SeyaCloudGestion.GestionSistema.feacture.listaPrecios.application.dto.request.RequestDetalleListaPrecios;
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

    public EdicionAllListaPreciosUseCase(ListaPreciosService listaPreciosService, VerificarCambiosListaPrecios verificarCambiosListaPrecios) {
        this.listaPreciosService = listaPreciosService;
        this.verificarCambiosListaPrecios = verificarCambiosListaPrecios;
    }
    public ResponseEditarAllListaPrecios EditarAllListaPrecios(RequestEditarAllListaPrecios request) {
        try {
            RequestDetalleListaPrecios requestDetalle = new RequestDetalleListaPrecios();
            requestDetalle.setIdListaPrecios(request.getIdListaPrecios());

            ResponseDetalleListaPrecios detalleBD= listaPreciosService.DetalleListaPrecios(requestDetalle);

            if (!detalleBD.isExito() || detalleBD.getListaPrecios() == null) {
                throw new IllegalArgumentException("La lista precios no existe.");
            }

            if (!verificarCambiosListaPrecios.verificarCambios(detalleBD.getListaPrecios(), request)) {
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