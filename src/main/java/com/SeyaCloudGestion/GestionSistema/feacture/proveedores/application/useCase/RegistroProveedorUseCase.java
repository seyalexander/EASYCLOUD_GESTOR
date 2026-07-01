package com.SeyaCloudGestion.GestionSistema.feacture.proveedores.application.useCase;

import com.SeyaCloudGestion.GestionSistema.feacture.proveedores.application.dto.request.RequestRegistroProveedor;
import com.SeyaCloudGestion.GestionSistema.feacture.proveedores.application.dto.response.ResponseRegistroProveedor;
import com.SeyaCloudGestion.GestionSistema.feacture.proveedores.domain.services.ProveedoresService;
import com.SeyaCloudGestion.GestionSistema.feacture.tipoDocumentos.application.dto.response.ResponseDetalleTipoDocumento;
import com.SeyaCloudGestion.GestionSistema.feacture.tipoDocumentos.application.useCase.DetalleTipoDocumentoUseCase;
import com.SeyaCloudGestion.GestionSistema.feacture.tipoDocumentos.domain.validations.dependencias.TipoDocumentoValidator;
import org.springframework.stereotype.Component;

@Component
public class RegistroProveedorUseCase {
    private final ProveedoresService proveedoresService;
    private final DetalleTipoDocumentoUseCase detalleTipoDocumentoUseCase;
    public RegistroProveedorUseCase(
            ProveedoresService proveedoresService, DetalleTipoDocumentoUseCase detalleTipoDocumentoUseCase
    ){
        this.proveedoresService = proveedoresService;
        this.detalleTipoDocumentoUseCase = detalleTipoDocumentoUseCase;
    }

    public ResponseRegistroProveedor RegistroProveedores(RequestRegistroProveedor request) {
        try {
            //verificar el tipo documento
            ResponseDetalleTipoDocumento detalleTipoDocumentoBD = detalleTipoDocumentoUseCase.DetalleTipoDocumento(request.getIdTipoDocumento());
            if (!detalleTipoDocumentoBD.isExito() || detalleTipoDocumentoBD.getTipoDocumento() == null) {
                throw new IllegalArgumentException("El Id del tipo documento no existe.");
            }
            //verificar que el tipo sea ruc
            if (!"0".equals(detalleTipoDocumentoBD.getTipoDocumento().getCodigoSunat())) {
                throw new IllegalArgumentException("El tipo de documento debe ser RUC (Código Sunat 06).");
            }
            //validaciones de longitud y empezar con 20
            TipoDocumentoValidator.validarDocumento(request.getRuc(), detalleTipoDocumentoBD);

            ResponseRegistroProveedor response = proveedoresService.RegistroProveedores(request);
            if(response.isExito()){}

            return response;

        } catch (IllegalArgumentException | SecurityException e){
            ResponseRegistroProveedor response = new ResponseRegistroProveedor();
            response.setExito(false);
            response.setMessage(e.getMessage());
            return response;
        }
        catch (Exception e){
            String mensajeError = "Error inesperado al registrar los proveedores: " + e.getMessage();
            System.err.println("[ERROR] " + mensajeError);
            ResponseRegistroProveedor response = new ResponseRegistroProveedor();
            response.setExito(false);
            response.setMessage(mensajeError);
            return response;
        }
    }
}