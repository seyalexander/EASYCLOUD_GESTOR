// Lógica para el módulo de Artículos
let articulosData = [];
let todasSubfamilias = [];

// Escuchar cuando el router carga la vista de artículos
document.addEventListener('viewLoaded', (e) => {
    if (e.detail === 'articulos') {
        initArticulosModule();
    }
});

function initArticulosModule() {
    const tableBody = document.getElementById('tablaArticulosBody');
    const modal = document.getElementById('modalArticulo');
    const form = document.getElementById('formArticulo');
    const btnNuevo = document.getElementById('btnNuevoArticulo');
    const btnClose = document.getElementById('btnCloseModal');
    const btnCancel = document.getElementById('btnCancelModal');
    
    // Cargar datos principales y catálogos de los dropdowns
    cargarArticulos();
    cargarCatalogosDropdowns();

    // Eventos del Modal
    btnNuevo.addEventListener('click', () => abrirModal());
    btnClose.addEventListener('click', cerrarModal);
    btnCancel.addEventListener('click', cerrarModal);

    // Evento Cascada Familia -> SubFamilia
    document.getElementById('isFamilia').addEventListener('change', (e) => {
        actualizarSubFamilias(e.target.value);
    });

    // Búsqueda local
    document.getElementById('searchInputArticulos').addEventListener('input', (e) => {
        const query = e.target.value.toLowerCase();
        const filtrados = articulosData.filter(a => 
            (a.codigoArticulo && a.codigoArticulo.toLowerCase().includes(query)) ||
            (a.descripcion && a.descripcion.toLowerCase().includes(query))
        );
        renderTable(filtrados);
    });

    // Evento Submit (Crear o Editar)
    form.addEventListener('submit', async (e) => {
        e.preventDefault();
        
        const idArticulo = document.getElementById('idArticulo').value;
        const payload = {
            codigoArticulo: document.getElementById('codigoArticulo').value,
            codigoBarras: document.getElementById('codigoBarras').value,
            descripcion: document.getElementById('descripcion').value,
            precioVenta: parseFloat(document.getElementById('precioVenta').value),
            costoCompra: parseFloat(document.getElementById('costoCompra').value),
            stockMinimo: parseFloat(document.getElementById('stockMinimo').value || 0),
            estado: parseInt(document.getElementById('estado').value),
            isFamilia: parseInt(document.getElementById('isFamilia').value),
            idSubFamilia: parseInt(document.getElementById('idSubFamilia').value),
            idUnidadMedida: parseInt(document.getElementById('idUnidadMedida').value),
            idMarca: parseInt(document.getElementById('idMarca').value),
            imagenUrl: document.getElementById('imagenUrl').value
        };

        try {
            const btnSubmit = document.getElementById('btnGuardarArticulo');
            btnSubmit.textContent = 'Guardando...';
            btnSubmit.disabled = true;

            if (idArticulo) {
                // Editar (PUT)
                // El backend pide "idArticulos" (con s)
                payload.idArticulos = parseInt(idArticulo);
                await api.put('/articulos', payload);
                alert('Artículo editado correctamente');
            } else {
                // Crear (POST)
                await api.post('/articulos', payload);
                alert('Artículo creado correctamente');
            }
            
            cerrarModal();
            cargarArticulos(); // Recargar la tabla
        } catch (error) {
            console.error(error);
            alert('Error al guardar el artículo. Verifica la consola y el backend.');
        } finally {
            const btnSubmit = document.getElementById('btnGuardarArticulo');
            btnSubmit.textContent = 'Guardar Artículo';
            btnSubmit.disabled = false;
        }
    });
}

// ================= Métodos API =================

async function cargarArticulos() {
    const tableBody = document.getElementById('tablaArticulosBody');
    tableBody.innerHTML = '<tr><td colspan="6" style="text-align: center; padding: 2rem;">Cargando artículos...</td></tr>';
    
    try {
        // En el backend el endpoint GET recibe un ModelAttribute RequestListaArticulo, lo simulamos enviando query params
        // Por ahora lo enviamos sin params para traer todos
        const response = await api.get('/articulos?estado=1'); // O ajustar según tu backend
        
        // Asumiendo que el response tiene un array en un campo (ej: data o content)
        articulosData = response.articulos || response.data || response.lista || response.listArticulos || [];
        
        // Si el backend devuelve un arreglo directo
        if (Array.isArray(response)) {
            articulosData = response;
        }

        renderTable(articulosData);
    } catch (error) {
        console.error('Error cargando articulos', error);
        tableBody.innerHTML = '<tr><td colspan="6" style="text-align: center; color: var(--danger); padding: 2rem;">Error al cargar datos. Verifica la conexión.</td></tr>';
    }
}

function renderTable(datos) {
    const tableBody = document.getElementById('tablaArticulosBody');
    tableBody.innerHTML = '';

    if (!datos || datos.length === 0) {
        tableBody.innerHTML = '<tr><td colspan="6" style="text-align: center; padding: 2rem;">No hay artículos registrados.</td></tr>';
        return;
    }

    datos.forEach(item => {
        const tr = document.createElement('tr');
        
        // Manejo de estado
        const estadoBadge = item.estado === 1 || item.estado === 'Activo' 
            ? '<span class="badge badge-success">Activo</span>' 
            : '<span class="badge badge-danger">Inactivo</span>';

        // Usamos el nombre de la familia/marca si existe, sino el ID
        const familiaText = item.descripcionFamilia || item.isFamilia || '-';

        // Obtenemos el ID correcto (el backend usa idArticulos en plural)
        const itemId = item.idArticulos || item.idArticulo;

        tr.innerHTML = `
            <td>
                <strong>${item.codigoArticulo || '-'}</strong><br>
                <small style="color: var(--text-muted);">${familiaText}</small>
            </td>
            <td>${item.descripcion || '-'}</td>
            <td>$ ${(item.precioVenta || 0).toFixed(2)}</td>
            <td>${item.stockMinimo || 0}</td>
            <td>${estadoBadge}</td>
            <td style="text-align: right;">
                <button class="icon-btn" onclick="editarArticulo(${itemId})" title="Editar"><i class="fa-solid fa-pen text-primary"></i></button>
                ${item.estado === 1 
                    ? `<button class="icon-btn" onclick="anularArticulo(${itemId})" title="Anular"><i class="fa-solid fa-trash text-danger"></i></button>`
                    : `<button class="icon-btn" onclick="activarArticulo(${itemId})" title="Activar"><i class="fa-solid fa-check text-success"></i></button>`
                }
            </td>
        `;
        tableBody.appendChild(tr);
    });
}

// ================= Carga de Selects =================

async function cargarCatalogosDropdowns() {
    try {
        // Ejecutamos las 4 peticiones en paralelo
        const [resFamilia, resSubFamilia, resUnidad, resMarca] = await Promise.all([
            api.get('/familia?estado=1').catch(() => []),
            api.get('/subfamilia?estado=1').catch(() => []),
            api.get('/unidadMedida?estado=1').catch(() => []),
            api.get('/marca?estado=1').catch(() => [])
        ]);

        // Helper para mapear opciones al select
        const llenarSelect = (idElemento, dataArray, idKey, descKey) => {
            const select = document.getElementById(idElemento);
            // Conserva la primera opción "Seleccione..."
            const optionsHTML = (dataArray || []).map(item => 
                `<option value="${item[idKey]}">${item[descKey]}</option>`
            ).join('');
            select.innerHTML = '<option value="">Seleccione...</option>' + optionsHTML;
        };

        // Llenamos usando las claves correctas (Asumidas basadas en los DTOs)
        llenarSelect('isFamilia', resFamilia.familia || resFamilia.data || resFamilia || [], 'idFamilia', 'descripcion');
        
        // Guardar subfamilias globalmente y no llenar select aún
        todasSubfamilias = resSubFamilia.subfamilias || resSubFamilia.data || resSubFamilia || [];
        
        llenarSelect('idUnidadMedida', resUnidad.unidadesMedida || resUnidad.data || resUnidad || [], 'idUnidadMedida', 'descripcion');
        llenarSelect('idMarca', resMarca.marcas || resMarca.data || resMarca || [], 'idMarca', 'descripcion');

    } catch (error) {
        console.error('Error cargando los catálogos para los selects:', error);
    }
}

function actualizarSubFamilias(idFamilia) {
    const selectSub = document.getElementById('idSubFamilia');
    if (!idFamilia) {
        selectSub.innerHTML = '<option value="">Seleccione...</option>';
        selectSub.disabled = true;
        return;
    }
    
    const filtradas = todasSubfamilias.filter(sf => sf.idFamilia == idFamilia);
    const optionsHTML = filtradas.map(item => 
        `<option value="${item.idSubFamilia}">${item.subFamiliaDescripcion}</option>`
    ).join('');
    
    selectSub.innerHTML = '<option value="">Seleccione...</option>' + optionsHTML;
    selectSub.disabled = false;
}

// ================= Acciones =================

async function editarArticulo(id) {
    try {
        const response = await api.get(`/articulos/${id}`);
        // El backend devuelve { exito, mensaje, articulos: { ... } }
        const articulo = response.articulos || response.data || response;
        
        if(articulo) {
            document.getElementById('idArticulo').value = articulo.idArticulos || articulo.idArticulo || id;
            document.getElementById('codigoArticulo').value = articulo.codigoArticulo || '';
            document.getElementById('codigoBarras').value = articulo.codigoBarras || '';
            document.getElementById('descripcion').value = articulo.descripcion || '';
            document.getElementById('precioVenta').value = articulo.precioVenta || 0;
            document.getElementById('costoCompra').value = articulo.costoCompra || 0;
            document.getElementById('stockMinimo').value = articulo.stockMinimo || 0;
            document.getElementById('estado').value = articulo.estado !== undefined ? articulo.estado : 1;
            document.getElementById('isFamilia').value = articulo.idFamilia || 1;
            
            // Actualizar subfamilias basadas en la familia seleccionada antes de setear el idSubFamilia
            actualizarSubFamilias(articulo.idFamilia || 1);
            
            document.getElementById('idSubFamilia').value = articulo.idSubFamilia || 1;
            document.getElementById('idUnidadMedida').value = articulo.idUnidadMedida || 1;
            document.getElementById('idMarca').value = articulo.idMarca || 1;
            document.getElementById('imagenUrl').value = articulo.imagenUrl || '';
            
            abrirModal(true);
        }
    } catch(e) {
        alert('Error al obtener el detalle del artículo');
        console.error(e);
    }
}

async function anularArticulo(id) {
    if(confirm('¿Estás seguro de anular (desactivar) este artículo?')) {
        try {
            await api.delete(`/articulos/${id}`);
            alert('Artículo anulado');
            cargarArticulos();
        } catch(e) {
            alert('Error al anular el artículo');
        }
    }
}

async function activarArticulo(id) {
    if(confirm('¿Estás seguro de reactivar este artículo?')) {
        try {
            await api.patch(`/articulos/${id}/activar`);
            alert('Artículo activado');
            cargarArticulos();
        } catch(e) {
            alert('Error al activar el artículo');
        }
    }
}

// ================= Helpers Modal =================

function abrirModal(isEdit = false) {
    document.getElementById('modalArticulo').style.display = 'flex';
    if(!isEdit) {
        document.getElementById('formArticulo').reset();
        document.getElementById('idArticulo').value = '';
        actualizarSubFamilias(null); // Deshabilita y limpia el select de subfamilias
        document.getElementById('modalTitle').textContent = 'Nuevo Artículo';
    } else {
        document.getElementById('modalTitle').textContent = 'Editar Artículo';
    }
}

function cerrarModal() {
    document.getElementById('modalArticulo').style.display = 'none';
    document.getElementById('formArticulo').reset();
}
