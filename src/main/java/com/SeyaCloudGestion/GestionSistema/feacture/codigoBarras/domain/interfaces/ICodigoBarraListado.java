package com.SeyaCloudGestion.GestionSistema.feacture.codigoBarras.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.feacture.codigoBarras.application.dto.request.RequestListaCodigoBarra;
import com.SeyaCloudGestion.GestionSistema.feacture.codigoBarras.application.dto.response.ResponseListaCodigoBarra;

public interface ICodigoBarraListado {
    ResponseListaCodigoBarra ListaCodigoBarra( );
    //ResponseListaCodigoBarra listaCodigoBarra(RequestListaCodigoBarra request);

}