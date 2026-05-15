package com.SeyaCloudGestion.GestionSistema.feacture.moneda.application.useCase;

import com.SeyaCloudGestion.GestionSistema.feacture.moneda.application.dto.request.RequestDetalleMoneda;
import com.SeyaCloudGestion.GestionSistema.feacture.moneda.application.dto.request.RequestEditarAllMoneda;
import com.SeyaCloudGestion.GestionSistema.feacture.moneda.application.dto.request.RequestEditarEstadoMoneda;
import com.SeyaCloudGestion.GestionSistema.feacture.moneda.application.dto.response.ResponseDetalleMoneda;
import com.SeyaCloudGestion.GestionSistema.feacture.moneda.application.dto.response.ResponseEditarAllMoneda;
import com.SeyaCloudGestion.GestionSistema.feacture.moneda.application.dto.response.ResponseEditarEstadoMoneda;
import com.SeyaCloudGestion.GestionSistema.feacture.moneda.domain.services.MonedaService;
import com.SeyaCloudGestion.GestionSistema.feacture.moneda.infraestructure.persistence.model.MonedaModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class EditarEstadoMonedaUseCase {

    private final MonedaService monedaService;

    public EditarEstadoMonedaUseCase(
            MonedaService monedaService
    ){
        this.monedaService = monedaService;
    }

    public ResponseEditarEstadoMoneda AnularAllMoneda(long idMoneda) {
        try {
            RequestDetalleMoneda requestDetalle = new RequestDetalleMoneda();
            requestDetalle.setIdMoneda(idMoneda);

            ResponseDetalleMoneda moneda = monedaService.DetalleMoneda(requestDetalle);

            if (!moneda.isExito()) {
                throw new IllegalArgumentException("No se pudo obtener la moneda");
            }

            if (moneda.getMoneda().getEsPrincipal() == 1) {
                throw new IllegalArgumentException("No puedes anular la moneda predeterminada");
            }

            RequestEditarEstadoMoneda request = new RequestEditarEstadoMoneda();
            request.setIdMoneda(idMoneda);

            long userId = 1L;

            ResponseEditarEstadoMoneda response =
                    monedaService.EditarEstadoMoneda(request, 0, userId);

            return response;

        } catch (IllegalArgumentException | SecurityException e) {
            ResponseEditarEstadoMoneda response = new ResponseEditarEstadoMoneda();
            response.setExito(false);
            response.setMessage(e.getMessage());
            return response;

        } catch (Exception e) {
            String mensajeError = "Error inesperado al anular la moneda: " + e.getMessage();
            System.err.println("[ERROR] " + mensajeError);

            ResponseEditarEstadoMoneda response = new ResponseEditarEstadoMoneda();
            response.setExito(false);
            response.setMessage(mensajeError);
            return response;
        }
    }

    public ResponseEditarEstadoMoneda ActivarAllMoneda(long idMoneda) {
        try {

            RequestEditarEstadoMoneda request = new RequestEditarEstadoMoneda();
            request.setIdMoneda(idMoneda);
            //long userId = SecurityUtils.getCurrentUserId();
            long userId = 1L;

            ResponseEditarEstadoMoneda response = monedaService.EditarEstadoMoneda(request, 1, userId);
            if(response.isExito()){}

            return response;

        }catch (IllegalArgumentException | SecurityException e){
            ResponseEditarEstadoMoneda response = new ResponseEditarEstadoMoneda();
            response.setExito(false);
            response.setMessage(e.getMessage());
            return response;
        }
        catch (Exception e){
            String mensajeError = "Error inesperado al activar la moneda: " + e.getMessage();
            System.err.println("[ERROR] " + mensajeError);
            ResponseEditarEstadoMoneda response = new ResponseEditarEstadoMoneda();
            response.setExito(false);
            response.setMessage(mensajeError);
            return response;
        }
    }
}
