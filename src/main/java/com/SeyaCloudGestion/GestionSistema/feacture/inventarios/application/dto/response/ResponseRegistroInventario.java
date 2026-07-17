package com.SeyaCloudGestion.GestionSistema.feacture.inventarios.application.dto.response;

import com.SeyaCloudGestion.GestionSistema.common.response.ResponseGeneral;
import lombok.Data;

@Data
public class ResponseRegistroInventario extends ResponseGeneral {
    private long idInventarioCabecera;
}
