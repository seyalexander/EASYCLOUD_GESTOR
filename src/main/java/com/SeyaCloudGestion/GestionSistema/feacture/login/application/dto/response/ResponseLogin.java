package com.SeyaCloudGestion.GestionSistema.feacture.login.application.dto.response;

import com.SeyaCloudGestion.GestionSistema.common.response.ResponseGeneral;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ResponseLogin extends ResponseGeneral {
    private String token;
    private String nombre;
    private String apellido;
    private String descripcionRol;
    private long idRol;
}
