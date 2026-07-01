package com.SeyaCloudGestion.GestionSistema.feacture.proveedores.domain.validations;

import com.SeyaCloudGestion.GestionSistema.common.validation.GlobalVerficarCambios;
import com.SeyaCloudGestion.GestionSistema.feacture.clientes.application.dto.request.RequestEditarAllCliente;
import com.SeyaCloudGestion.GestionSistema.feacture.clientes.infraestructure.persistence.model.ClienteModel;
import com.SeyaCloudGestion.GestionSistema.feacture.proveedores.application.dto.request.RequestEditarAllProveedor;
import com.SeyaCloudGestion.GestionSistema.feacture.proveedores.infraestructure.persistence.model.ProveedorModel;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class VerificarCambiosProveedor implements GlobalVerficarCambios<ProveedorModel, RequestEditarAllProveedor> {
    @Override
    public boolean verificarCambios(ProveedorModel modelBD, RequestEditarAllProveedor request) {

        boolean cambioRazonSocial = !Objects.equals(modelBD.getRazonSocial(), request.getRazonSocial());

        boolean cambioRuc = !Objects.equals(modelBD.getRuc(), request.getRuc());

        boolean cambioTelefono = !Objects.equals(modelBD.getTelefono(), request.getTelefono());

        boolean cambioEmail = !Objects.equals(modelBD.getEmail(), request.getEmail());

        boolean cambioDireccion = !Objects.equals(modelBD.getDireccion(), request.getDireccion());

        boolean cambioEstado = modelBD.getEstado() != request.getEstado();

        return cambioRazonSocial
                || cambioRuc
                || cambioTelefono
                || cambioEmail
                || cambioDireccion
                || cambioEstado;
    }
}
