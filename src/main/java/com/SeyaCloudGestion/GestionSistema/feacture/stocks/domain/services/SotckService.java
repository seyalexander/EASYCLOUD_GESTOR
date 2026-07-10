package com.SeyaCloudGestion.GestionSistema.feacture.stocks.domain.services;

import com.SeyaCloudGestion.GestionSistema.feacture.stocks.application.dto.request.*;
import com.SeyaCloudGestion.GestionSistema.feacture.stocks.application.dto.response.*;
import com.SeyaCloudGestion.GestionSistema.feacture.stocks.domain.interfaces.ISotckDetalle;
import com.SeyaCloudGestion.GestionSistema.feacture.stocks.domain.interfaces.ISotckEdicion;
import com.SeyaCloudGestion.GestionSistema.feacture.stocks.domain.interfaces.ISotckListado;
import com.SeyaCloudGestion.GestionSistema.feacture.stocks.domain.interfaces.ISotckRegistro;
import com.SeyaCloudGestion.GestionSistema.feacture.stocks.infraestructure.persistence.repository.crud.SotckDetalleRepository;
import com.SeyaCloudGestion.GestionSistema.feacture.stocks.infraestructure.persistence.repository.crud.SotckEdicionRepository;
import com.SeyaCloudGestion.GestionSistema.feacture.stocks.infraestructure.persistence.repository.crud.StockListadoRepository; // Ojo: Aquí en tu imagen dice Stock...
import com.SeyaCloudGestion.GestionSistema.feacture.stocks.infraestructure.persistence.repository.crud.SotckRegistroRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SotckService implements ISotckListado, ISotckRegistro, ISotckEdicion, ISotckDetalle {

    private final StockListadoRepository sotckListadoRepository;
    private final SotckRegistroRepository sotckRegistroRepository;
    private final SotckEdicionRepository sotckEdicionRepository;
    private final SotckDetalleRepository sotckDetalleRepository;

    public SotckService(
            StockListadoRepository sotckListadoRepository,
            SotckRegistroRepository sotckRegistroRepository,
            SotckEdicionRepository sotckEdicionRepository,
            SotckDetalleRepository sotckDetalleRepository
    ) {
        this.sotckListadoRepository = sotckListadoRepository;
        this.sotckRegistroRepository = sotckRegistroRepository;
        this.sotckEdicionRepository = sotckEdicionRepository;
        this.sotckDetalleRepository = sotckDetalleRepository;
    }

    @Override
    @Cacheable(value = "stocks_lista",  key = "#request.idProducto + '-' + #request.idAlmacen")
    public ResponseListaSotck listaSotck(RequestListaSotck request) {
        return sotckListadoRepository.listaSotck(request);
    }

    @Override
    @CacheEvict(value = {"stocks_lista", "stock_detalle"}, allEntries = true)
    public ResponseRegistroSotck RegistroSotck(RequestRegistroSotck request) {
        return sotckRegistroRepository.RegistroSotck(request);
    }

    @Override
    @CacheEvict(value = {"stocks_lista", "stock_detalle"}, allEntries = true)
    public ResponseEditarAllSotck EditarAllSotck(RequestEditarAllSotck request) {
        return sotckEdicionRepository.EditarAllSotck(request);
    }

    @Override
    /*
    @Cacheable(
            value = "stock_detalle",
            key = "#request.idProducto + '-' + #request.idAlmacen"
    )
     */
    public ResponseDetalleSotck DetalleSotck(RequestDetalleSotck request) {
        return sotckDetalleRepository.DetalleSotck(request);
    }
}