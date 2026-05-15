package com.SeyaCloudGestion.GestionSistema.feacture.login.application.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestLogin {
    private String usuario;
    private String clave;
}
