// Lógica para Precios de Producto
let productoPrecioData = [];

document.addEventListener('viewLoaded', (e) => {
    if (e.detail === 'productoPrecios') {
        initProductoPrecioModule();
    }
});

function initProductoPrecioModule() {
    const form = document.getElementById('formProductoPrecio');
    const btnNuevo = document.getElementById('btnNuevoProductoPrecio');
    const btnClose = document.getElementById('btnCloseModalProductoPrecio');
    const btnCancel = document.getElementById('btnCancelModalProductoPrecio');
    
    cargarProductoPrecios();
    cargarArticulosParaPrecios();

    btnNuevo.addEventListener('click', () => abrirModalProductoPrecio());
    btnClose.addEventListener('click', cerrarModalProductoPrecio);
    btnCancel.addEventListener('click', cerrarModalProductoPrecio);

    document.getElementById('searchInputProductoPrecio').addEventListener('input', (e) => {
        const query = e.target.value.toLowerCase();
        const filtrados = productoPrecioData.filter(a => 
            (a.descripcionArticulo && a.descripcionArticulo.toLowerCase().includes(query)) ||
            (a.descripcionLista && a.descripcionLista.toLowerCase().includes(query))
        );
        renderTableProductoPrecio(filtrados);
    });

    form.addEventListener('submit', async (e) => {
        e.preventDefault();
        
        const id = document.getElementById('idProductoPrecio').value;
        const payload = {
            idArticulo: parseInt(document.getElementById('idArticuloPrecio').value),
            idListaPrecio: parseInt(document.getElementById('idListaPrecio').value),
            precio: parseFloat(document.getElementById('precioArticulo').value),
            fechaInicio: document.getElementById('fechaInicioPrecio').value + "T00:00:00",
            fechaFin: document.getElementById('fechaFinPrecio').value + "T00:00:00"
        };

        try {
            const btnSubmit = document.getElementById('btnGuardarProductoPrecio');
            btnSubmit.textContent = 'Guardando...';
            btnSubmit.disabled = true;

            if (id) {
                payload.idProductoPrecio = parseInt(id);
                payload.estado = parseInt(document.getElementById('estadoProductoPrecio').value);
                await api.put('/productoPrecios', payload);
                alert('Precio editado correctamente');
            } else {
                await api.post('/productoPrecios', payload);
                alert('Precio creado correctamente');
            }
            
            cerrarModalProductoPrecio();
            cargarProductoPrecios();
        } catch (error) {
            console.error(error);
            alert('Error al guardar. Verifica la consola.');
        } finally {
            const btnSubmit = document.getElementById('btnGuardarProductoPrecio');
            btnSubmit.textContent = 'Guardar';
            btnSubmit.disabled = false;
        }
    });
}

async function cargarProductoPrecios() {
    const tableBody = document.getElementById('tablaProductoPrecioBody');
    tableBody.innerHTML = '<tr><td colspan="6" style="text-align: center; padding: 2rem;">Cargando precios...</td></tr>';
    
    try {
        const response = await api.get('/productoPrecios?estado=1');
        productoPrecioData = response.productoPrecios || response.data || [];
        renderTableProductoPrecio(productoPrecioData);
    } catch (error) {
        console.error(error);
        tableBody.innerHTML = '<tr><td colspan="6" style="text-align: center; color: var(--danger);">Error al cargar datos.</td></tr>';
    }
}

let articulosMap = {};

async function cargarArticulosParaPrecios() {
    try {
        const response = await api.get('/articulos?estado=1');
        const articulos = response.articulos || response.data || [];
        const selectArticulo = document.getElementById('idArticuloPrecio');
        
        articulosMap = {};
        const optionsHTML = articulos.map(a => {
            articulosMap[a.idArticulos] = `${a.codigoArticulo} - ${a.descripcion}`;
            return `<option value="${a.idArticulos}">${a.codigoArticulo} - ${a.descripcion}</option>`;
        }).join('');
        selectArticulo.innerHTML = '<option value="">Seleccione un artículo...</option>' + optionsHTML;
        
        if (productoPrecioData.length > 0) renderTableProductoPrecio(productoPrecioData);
    } catch (error) {
        console.error('Error cargando artículos', error);
    }
}

function formatearFechaCorta(fechaStr) {
    if(!fechaStr) return '-';
    // Asumir DD/MM/YYYY HH:mm:ss o ISO
    if(fechaStr.includes('/')) {
        const parts = fechaStr.split(' ')[0].split('/');
        if(parts.length === 3) return `${parts[2]}-${parts[1]}-${parts[0]}`; // Convertir a YYYY-MM-DD
    }
    const date = new Date(fechaStr);
    if(isNaN(date.getTime())) return fechaStr;
    return date.toISOString().split('T')[0];
}

function renderTableProductoPrecio(data) {
    const tableBody = document.getElementById('tablaProductoPrecioBody');
    tableBody.innerHTML = '';
    
    if (data.length === 0) {
        tableBody.innerHTML = '<tr><td colspan="6" style="text-align: center; padding: 2rem;">No se encontraron registros.</td></tr>';
        return;
    }

    data.forEach(item => {
        const estadoBadge = item.estado === 1 
            ? '<span class="badge badge-success">Activo</span>' 
            : '<span class="badge badge-danger">Inactivo</span>';
        
        const itemId = item.idProductoPrecio;
        const nombreArticulo = item.descripcionArticulo || articulosMap[item.idArticulo] || `Art: ${item.idArticulo || '-'}`;
        const nombreLista = item.descripcionLista || `Lista: ${item.idListaPrecio || '-'}`;

        const tr = document.createElement('tr');
        tr.innerHTML = `
            <td><strong>${nombreArticulo}</strong></td>
            <td>${nombreLista}</td>
            <td>$ ${parseFloat(item.precio || 0).toFixed(2)}</td>
            <td><small>${formatearFechaCorta(item.fechaInicio)} al ${formatearFechaCorta(item.fechaFin)}</small></td>
            <td>${estadoBadge}</td>
            <td style="text-align: right;">
                <button class="icon-btn" onclick="editarProductoPrecio(${itemId})" title="Editar"><i class="fa-solid fa-pen text-primary"></i></button>
                ${item.estado === 1 
                    ? `<button class="icon-btn" onclick="anularProductoPrecio(${itemId})" title="Anular"><i class="fa-solid fa-trash text-danger"></i></button>`
                    : `<button class="icon-btn" onclick="activarProductoPrecio(${itemId})" title="Activar"><i class="fa-solid fa-check text-success"></i></button>`
                }
            </td>
        `;
        tableBody.appendChild(tr);
    });
}

async function editarProductoPrecio(id) {
    try {
        const response = await api.get(`/productoPrecios/${id}`);
        const obj = response.productoPrecio || response.productoPrecios || response.data || response;
        
        if(obj) {
            document.getElementById('idProductoPrecio').value = obj.idProductoPrecio || id;
            document.getElementById('idArticuloPrecio').value = obj.idArticulo || '';
            document.getElementById('idListaPrecio').value = obj.idListaPrecio || '';
            document.getElementById('precioArticulo').value = obj.precio || '';
            
            if(obj.fechaInicio) document.getElementById('fechaInicioPrecio').value = obj.fechaInicio.split('T')[0];
            if(obj.fechaFin) document.getElementById('fechaFinPrecio').value = obj.fechaFin.split('T')[0];

            document.getElementById('estadoProductoPrecio').value = obj.estado !== undefined ? obj.estado : 1;
            
            abrirModalProductoPrecio(true);
        }
    } catch(e) {
        alert('Error al obtener el detalle');
        console.error(e);
    }
}

async function anularProductoPrecio(id) {
    if(confirm('¿Estás seguro de anular este precio?')) {
        try {
            await api.delete(`/productoPrecios/${id}`);
            alert('Anulado correctamente');
            cargarProductoPrecios();
        } catch(e) {
            alert('Error al anular');
        }
    }
}

async function activarProductoPrecio(id) {
    if(confirm('¿Estás seguro de reactivar este precio?')) {
        try {
            await api.patch(`/productoPrecios/${id}/activar`);
            alert('Activado correctamente');
            cargarProductoPrecios();
        } catch(e) {
            alert('Error al activar');
        }
    }
}

function abrirModalProductoPrecio(isEdit = false) {
    document.getElementById('modalProductoPrecio').style.display = 'flex';
    if(!isEdit) {
        document.getElementById('formProductoPrecio').reset();
        document.getElementById('idProductoPrecio').value = '';
        document.getElementById('modalTitleProductoPrecio').textContent = 'Nuevo Precio';
        document.getElementById('estadoProductoPrecioGroup').style.display = 'none';
    } else {
        document.getElementById('modalTitleProductoPrecio').textContent = 'Editar Precio';
        document.getElementById('estadoProductoPrecioGroup').style.display = 'block';
    }
}

function cerrarModalProductoPrecio() {
    document.getElementById('modalProductoPrecio').style.display = 'none';
}
