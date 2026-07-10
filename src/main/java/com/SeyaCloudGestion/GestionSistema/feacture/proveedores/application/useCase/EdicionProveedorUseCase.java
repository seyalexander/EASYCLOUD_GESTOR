package com.SeyaCloudGestion.GestionSistema.feacture.proveedores.application.useCase;

import com.SeyaCloudGestion.GestionSistema.feacture.proveedores.application.dto.request.RequestEditarAllProveedor;
import com.SeyaCloudGestion.GestionSistema.feacture.proveedores.application.dto.response.ResponseDetalleProveedor;
import com.SeyaCloudGestion.GestionSistema.feacture.proveedores.application.dto.response.ResponseEditarAllProveedor;
import com.SeyaCloudGestion.GestionSistema.feacture.proveedores.domain.services.ProveedoresService;
import com.SeyaCloudGestion.GestionSistema.feacture.proveedores.domain.validations.VerificarCambiosProveedor;
import com.SeyaCloudGestion.GestionSistema.feacture.tipoDocumentos.application.dto.request.RequestDetalleTipoDocumento;
import com.SeyaCloudGestion.GestionSistema.feacture.tipoDocumentos.application.dto.response.ResponseDetalleTipoDocumento;
import com.SeyaCloudGestion.GestionSistema.feacture.tipoDocumentos.application.useCase.DetalleTipoDocumentoUseCase;
import com.SeyaCloudGestion.GestionSistema.feacture.tipoDocumentos.domain.validations.dependencias.TipoDocumentoValidator;
import org.springframework.stereotype.Component;

@Component
public class EdicionProveedorUseCase {

    private final ProveedoresService proveedoresService;
    private final DetalleProveedorUseCase detalleProveedorUseCase;
    private final VerificarCambiosProveedor verificarCambiosProveedores;
    private final DetalleTipoDocumentoUseCase detalleTipoDocumentoUseCase;

    public EdicionProveedorUseCase(
            ProveedoresService proveedoresService,
            DetalleProveedorUseCase detalleProveedorUseCase, VerificarCambiosProveedor verificarCambiosProveedores, DetalleTipoDocumentoUseCase detalleTipoDocumentoUseCase
    ){
        this.proveedoresService = proveedoresService;
        this.detalleProveedorUseCase = detalleProveedorUseCase;
        this.verificarCambiosProveedores = verificarCambiosProveedores;
        this.detalleTipoDocumentoUseCase = detalleTipoDocumentoUseCase;
    }

    public ResponseEditarAllProveedor EdicionAllProveedores(RequestEditarAllProveedor request) {
        try {
            // id proveedor
            ResponseDetalleProveedor detalleBD = detalleProveedorUseCase.DetalleProveedores(request.getIdProveedor());

            if (!detalleBD.isExito() || detalleBD.getProveedor() == null) {
                throw new IllegalArgumentException("El proveedor no existe.");
            }
            //validaciones de longitud y empezar con 20
            ResponseDetalleTipoDocumento detalleTipoDocumentoBD= detalleTipoDocumentoUseCase.DetalleTipoDocumento(detalleBD.getProveedor().getIdTipoDocumento());

            TipoDocumentoValidator.validarDocumento(request.getRuc(), detalleTipoDocumentoBD);

            if (!verificarCambiosProveedores.verificarCambios(detalleBD.getProveedor(), request)) {
                throw new IllegalArgumentException("No se detectaron cambios para actualizar.");
            }

            ResponseEditarAllProveedor response = proveedoresService.EditarAllProveedores(request);
            if(response.isExito()){}

            return response;

        } catch (IllegalArgumentException | SecurityException e){
            ResponseEditarAllProveedor response = new ResponseEditarAllProveedor();
            response.setExito(false);
            response.setMessage(e.getMessage());
            return response;
        } catch (Exception e){
            String mensajeError = "Error inesperado al actualizar el proveedor: " + e.getMessage();
            System.err.println("[ERROR] " + mensajeError);
            ResponseEditarAllProveedor response = new ResponseEditarAllProveedor();
            response.setExito(false);
            response.setMessage(mensajeError);
            return response;
        }
    }
}