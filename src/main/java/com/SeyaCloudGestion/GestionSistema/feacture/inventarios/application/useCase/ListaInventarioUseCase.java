package com.SeyaCloudGestion.GestionSistema.feacture.inventarios.application.useCase;
import com.SeyaCloudGestion.GestionSistema.feacture.inventarios.application.dto.request.RequestListaInventario;
import com.SeyaCloudGestion.GestionSistema.feacture.inventarios.application.dto.response.ResponseListaInventario;
import com.SeyaCloudGestion.GestionSistema.feacture.inventarios.domain.services.InventarioService;
import org.springframework.stereotype.Component;

@Component
public class ListaInventarioUseCase {

    private final InventarioService inventarioService;

    public ListaInventarioUseCase(
            InventarioService inventarioService
    ) {
        this.inventarioService = inventarioService;
    }

    public ResponseListaInventario ListaInventario(RequestListaInventario request) {
        try {
            ResponseListaInventario response = inventarioService.listaInventario(request);
            if (response.isExito()) {
                // Lógica adicional en caso de éxito si fuera necesaria
            }

            return response;

        } catch (IllegalArgumentException | SecurityException e) {
            ResponseListaInventario response = new ResponseListaInventario();
            response.setExito(false);
            response.setMessage(e.getMessage());
            response.setInventarios(java.util.List.of());
            return response;
        } catch (Exception e) {
            String mensajeError = "Error inesperado al listar los inventarios: " + e.getMessage();
            System.err.println("[ERROR] " + mensajeError);
            ResponseListaInventario response = new ResponseListaInventario();
            response.setExito(false);
            response.setMessage(mensajeError);
            response.setInventarios(java.util.List.of());
            return response;
        }
    }
}
