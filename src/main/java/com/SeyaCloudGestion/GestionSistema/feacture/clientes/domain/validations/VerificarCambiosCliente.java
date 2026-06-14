package com.SeyaCloudGestion.GestionSistema.feacture.clientes.domain.validations;

import com.SeyaCloudGestion.GestionSistema.common.validation.GlobalVerficarCambios;
import com.SeyaCloudGestion.GestionSistema.feacture.clientes.application.dto.request.RequestEditarAllCliente;
import com.SeyaCloudGestion.GestionSistema.feacture.clientes.infraestructure.persistence.model.ClienteModel;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class VerificarCambiosCliente implements GlobalVerficarCambios<ClienteModel, RequestEditarAllCliente> {
    @Override
    public boolean verificarCambios(ClienteModel modelBD, RequestEditarAllCliente request) {
        boolean cambioNombres = !Objects.equals(modelBD.getNombres(), request.getNombres());

        boolean cambioApellidos = !Objects.equals(modelBD.getApellidos(), request.getApellidos());

        boolean cambioRazonSocial = !Objects.equals(modelBD.getRazonSocial(), request.getRazonSocial());

        boolean cambioNumeroDocumento = !Objects.equals(modelBD.getNumeroDocumento(), request.getNumeroDocumento());

        boolean cambioIdTipoDocumento = modelBD.getIdTipoDocumento() != request.getIdTipoDocumento();

        boolean cambioIdTipoCliente = modelBD.getIdTipoCliente() != request.getIdTipoCliente();

        boolean cambioTelefono = !Objects.equals(modelBD.getTelefono(), request.getTelefono());

        boolean cambioEmail = !Objects.equals(modelBD.getEmail(), request.getEmail());

        boolean cambioEstado = modelBD.getEstado() != request.getEstado();

        return cambioNombres
                || cambioApellidos
                || cambioRazonSocial
                || cambioNumeroDocumento
                || cambioIdTipoDocumento
                || cambioIdTipoCliente
                || cambioTelefono
                || cambioEmail
                || cambioEstado;
    }
}
