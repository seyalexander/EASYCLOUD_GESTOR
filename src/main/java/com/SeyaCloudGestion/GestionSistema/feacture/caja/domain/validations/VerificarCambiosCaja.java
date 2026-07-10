package com.SeyaCloudGestion.GestionSistema.feacture.caja.domain.validations;

import com.SeyaCloudGestion.GestionSistema.common.validation.GlobalVerficarCambios;
import com.SeyaCloudGestion.GestionSistema.feacture.caja.application.dto.request.RequestEditarAllCaja;
import com.SeyaCloudGestion.GestionSistema.feacture.caja.infraestructure.persistence.model.CajaModel; // Asegúrate de que el import de tu modelo sea el correcto
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class VerificarCambiosCaja implements GlobalVerficarCambios<CajaModel, RequestEditarAllCaja> {

    @Override
    public boolean verificarCambios(CajaModel modelBD, RequestEditarAllCaja request) {

        boolean cambioDescripcion =
                !Objects.equals(
                        modelBD.getDescripcion(),
                        request.getDescripcion());

        return cambioDescripcion;
    }
}