package com.SeyaCloudGestion.GestionSistema.feacture.serieCaja.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.feacture.serieCaja.application.dto.request.RequestDetalleSerieCaja;
import com.SeyaCloudGestion.GestionSistema.feacture.serieCaja.application.dto.request.RequestListaSerieCaja;
import com.SeyaCloudGestion.GestionSistema.feacture.serieCaja.application.dto.response.ResponseDetalleSerieCaja;
import com.SeyaCloudGestion.GestionSistema.feacture.serieCaja.application.dto.response.ResponseListaSerieCaja;

public interface ISerieCajaListado {
    ResponseListaSerieCaja listaSerieCaja(RequestListaSerieCaja request);
}