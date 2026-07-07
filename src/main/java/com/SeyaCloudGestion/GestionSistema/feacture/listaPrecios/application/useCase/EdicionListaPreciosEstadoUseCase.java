package com.SeyaCloudGestion.GestionSistema.feacture.listaPrecios.application.useCase;

import com.SeyaCloudGestion.GestionSistema.feacture.listaPrecios.application.dto.request.RequestEditarEstadoListaPrecios;
import com.SeyaCloudGestion.GestionSistema.feacture.listaPrecios.application.dto.response.ResponseDetalleListaPrecios;
import com.SeyaCloudGestion.GestionSistema.feacture.listaPrecios.application.dto.response.ResponseEditarEstadoListaPrecios;
import com.SeyaCloudGestion.GestionSistema.feacture.listaPrecios.domain.services.ListaPreciosService;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class EdicionListaPreciosEstadoUseCase {
    private final ListaPreciosService listaPreciosService;
    private final DetalleListaPreciosUseCase detalleListaPreciosUseCase;

    public EdicionListaPreciosEstadoUseCase(ListaPreciosService listaPreciosService, DetalleListaPreciosUseCase detalleListaPreciosUseCase) {
        this.listaPreciosService = listaPreciosService;
        this.detalleListaPreciosUseCase = detalleListaPreciosUseCase;
    }
    public ResponseEditarEstadoListaPrecios AnularListaPrecios(long idListaPrecio) {
        try {
            //listaprecio
            ResponseDetalleListaPrecios detalleBDPrecio= detalleListaPreciosUseCase.DetalleListaPrecios(idListaPrecio);

            if (!detalleBDPrecio.isExito() || detalleBDPrecio.getListaPrecios() == null) {
                throw new IllegalArgumentException("La lista precios no existe.");
            }

            if (Objects.equals(detalleBDPrecio.getListaPrecios().getEstado(), 0)) {
                throw new IllegalArgumentException("La lista ya se encuentra anulada.");
            }

            RequestEditarEstadoListaPrecios request = new RequestEditarEstadoListaPrecios();
            request.setIdListaPrecios(idListaPrecio);

            ResponseEditarEstadoListaPrecios response = listaPreciosService.EditarEstadoListaPrecios(request, 0);

            if (response.isExito()) {}

            return response;

        } catch (IllegalArgumentException | SecurityException e) {
            ResponseEditarEstadoListaPrecios response = new ResponseEditarEstadoListaPrecios();
            response.setExito(false);
            response.setMessage(e.getMessage());
            return response;
        } catch (Exception e) {
            String mensajeError = "Error inesperado al actualizar la lista de precios: " + e.getMessage();
            System.err.println("[ERROR] " + mensajeError);

            ResponseEditarEstadoListaPrecios response = new ResponseEditarEstadoListaPrecios();
            response.setExito(false);
            response.setMessage(mensajeError);
            return response;
        }
    }

    public ResponseEditarEstadoListaPrecios ActivarListaPrecios(long idListaPrecio) {
        try {
            //listaprecio
            ResponseDetalleListaPrecios detalleBDPrecio= detalleListaPreciosUseCase.DetalleListaPrecios(idListaPrecio);

            if (!detalleBDPrecio.isExito() || detalleBDPrecio.getListaPrecios() == null) {
                throw new IllegalArgumentException("La lista precios no existe.");
            }

            if (Objects.equals(detalleBDPrecio.getListaPrecios().getEstado(), 1)) {
                throw new IllegalArgumentException("La lista ya se encuentra activada.");
            }

            RequestEditarEstadoListaPrecios request = new RequestEditarEstadoListaPrecios();
            request.setIdListaPrecios(idListaPrecio);

            ResponseEditarEstadoListaPrecios response = listaPreciosService.EditarEstadoListaPrecios(request, 1);

            if (response.isExito()) {}

            return response;

        } catch (IllegalArgumentException | SecurityException e) {
            ResponseEditarEstadoListaPrecios response = new ResponseEditarEstadoListaPrecios();
            response.setExito(false);
            response.setMessage(e.getMessage());
            return response;
        } catch (Exception e) {
            String mensajeError = "Error inesperado al actualizar la lista de precios: " + e.getMessage();
            System.err.println("[ERROR] " + mensajeError);

            ResponseEditarEstadoListaPrecios response = new ResponseEditarEstadoListaPrecios();
            response.setExito(false);
            response.setMessage(mensajeError);
            return response;
        }
    }
}