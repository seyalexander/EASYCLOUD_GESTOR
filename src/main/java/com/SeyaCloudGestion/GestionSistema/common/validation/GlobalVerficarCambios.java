package com.SeyaCloudGestion.GestionSistema.common.validation;

public interface GlobalVerficarCambios <MODEL_BD,REQUEST>{

     boolean verificarCambios(MODEL_BD modeloBD, REQUEST request);
}
