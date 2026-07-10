package com.SeyaCloudGestion.GestionSistema.feacture.listaPrecios.domain.validations;

import com.SeyaCloudGestion.GestionSistema.common.validation.GlobalVerficarCambios;
import com.SeyaCloudGestion.GestionSistema.feacture.familia.application.dto.request.RequestEditarAllFamilia;
import com.SeyaCloudGestion.GestionSistema.feacture.familia.infraestructure.persistence.model.FamiliaModel;
import com.SeyaCloudGestion.GestionSistema.feacture.listaPrecios.application.dto.request.RequestEditarAllListaPrecios;
import com.SeyaCloudGestion.GestionSistema.feacture.listaPrecios.infraestructure.persistence.model.ListaPreciosModel;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class VerificarCambiosListaPrecios implements GlobalVerficarCambios<ListaPreciosModel, RequestEditarAllListaPrecios>{
    @Override
    public boolean verificarCambios(ListaPreciosModel modelBD, RequestEditarAllListaPrecios request) {
        boolean cambioDescripcion =
                !Objects.equals(
                        modelBD.getDescripcion(),
                        request.getDescripcion());

        boolean cambioEstado =
                modelBD.getEstado() != request.getEstado();

        return cambioDescripcion || cambioEstado;

    }
}
