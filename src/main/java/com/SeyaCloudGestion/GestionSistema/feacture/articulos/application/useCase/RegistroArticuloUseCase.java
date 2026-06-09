package com.SeyaCloudGestion.GestionSistema.feacture.articulos.application.useCase;

import com.SeyaCloudGestion.GestionSistema.feacture.articulos.application.dto.request.RequestRegistroArticulo;
import com.SeyaCloudGestion.GestionSistema.feacture.articulos.application.dto.response.ResponseRegistroArticulo;
import com.SeyaCloudGestion.GestionSistema.feacture.articulos.domain.services.ArticulosService;
import com.SeyaCloudGestion.GestionSistema.feacture.articulos.domain.validations.ValidacionRequest_RegistrarArticulo;
import com.SeyaCloudGestion.GestionSistema.feacture.marca.application.dto.request.RequestDetalleMarca;
import com.SeyaCloudGestion.GestionSistema.feacture.marca.application.dto.response.ResponseDetalleMarca;
import com.SeyaCloudGestion.GestionSistema.feacture.marca.domain.services.MarcaService;
import com.SeyaCloudGestion.GestionSistema.feacture.subFamilia.application.dto.request.RequestDetalleSubFamilia;
import com.SeyaCloudGestion.GestionSistema.feacture.subFamilia.application.dto.response.ResponseDetalleSubFamilia;
import com.SeyaCloudGestion.GestionSistema.feacture.subFamilia.domain.services.SubFamiliaService;
import com.SeyaCloudGestion.GestionSistema.feacture.unidadMedida.application.dto.request.RequestDetalleUnidadMedida;
import com.SeyaCloudGestion.GestionSistema.feacture.unidadMedida.application.dto.response.ResponseDetalleUnidadMedida;
import com.SeyaCloudGestion.GestionSistema.feacture.unidadMedida.domain.services.UnidadMedidaService;
import org.springframework.stereotype.Component;

@Component
public class RegistroArticuloUseCase {

    private final ArticulosService articulosService;
    private final SubFamiliaService subFamiliaService;
    private final UnidadMedidaService unidadMedidaService;
    private final MarcaService marcaService;

    public RegistroArticuloUseCase(
            ArticulosService articulosService, SubFamiliaService subFamiliaService, UnidadMedidaService unidadMedidaService, MarcaService marcaService
    ){
        this.articulosService = articulosService;
        this.subFamiliaService = subFamiliaService;
        this.unidadMedidaService = unidadMedidaService;
        this.marcaService = marcaService;
    }

    public ResponseRegistroArticulo RegistrarArticulo(RequestRegistroArticulo request) {
        try {
            // Obtener idUsuario del token
            //long userId = SecurityUtils.getCurrentUserId();
            long userId = 1L;
            long idEmpresa = 1L;
            //verificaciones all
            RequestDetalleSubFamilia requestDetalleSubFamilia = new RequestDetalleSubFamilia();
            requestDetalleSubFamilia.setIdSubFamilia(request.getIdSubFamilia());
            ResponseDetalleSubFamilia detalleBDSub= subFamiliaService.DetalleSubFamilia(requestDetalleSubFamilia);

            RequestDetalleMarca requestDetalleMar = new RequestDetalleMarca();
            requestDetalleMar.setIdMarca(request.getIdMarca());
            ResponseDetalleMarca detalleBDMar= marcaService.DetalleMarca(requestDetalleMar);

            RequestDetalleUnidadMedida requestDetalleUnidadMedida = new RequestDetalleUnidadMedida();
            requestDetalleUnidadMedida.setIdUnidadMedida(request.getIdUnidadMedida());
            ResponseDetalleUnidadMedida detalleBDUni= unidadMedidaService.DetalleUnidadMedida(requestDetalleUnidadMedida);

            ValidacionRequest_RegistrarArticulo.validarRegistroArticulo(detalleBDSub,detalleBDMar, detalleBDUni);

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
