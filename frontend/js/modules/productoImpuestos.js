// Lógica para Impuestos de Producto
let productoImpuestoData = [];

document.addEventListener('viewLoaded', (e) => {
    if (e.detail === 'productoImpuestos') {
        initProductoImpuestoModule();
    }
});

function initProductoImpuestoModule() {
    const form = document.getElementById('formProductoImpuesto');
    const btnNuevo = document.getElementById('btnNuevoProductoImpuesto');
    const btnClose = document.getElementById('btnCloseModalProductoImpuesto');
    const btnCancel = document.getElementById('btnCancelModalProductoImpuesto');
    
    cargarProductoImpuestos();
    cargarArticulosParaImpuestos();

    btnNuevo.addEventListener('click', () => abrirModalProductoImpuesto());
    btnClose.addEventListener('click', cerrarModalProductoImpuesto);
    btnCancel.addEventListener('click', cerrarModalProductoImpuesto);

    document.getElementById('searchInputProductoImpuesto').addEventListener('input', (e) => {
        const query = e.target.value.toLowerCase();
        const filtrados = productoImpuestoData.filter(a => 
            (a.descripcionArticulo && a.descripcionArticulo.toLowerCase().includes(query))
        );
        renderTableProductoImpuesto(filtrados);
    });

    form.addEventListener('submit', async (e) => {
        e.preventDefault();
        
        const id = document.getElementById('idProductoImpuesto').value;
        const payload = {
            idArticulo: parseInt(document.getElementById('idArticuloImpuesto').value),
            porcentaje: parseFloat(document.getElementById('porcentajeImpuesto').value)
        };

        try {
            const btnSubmit = document.getElementById('btnGuardarProductoImpuesto');
            btnSubmit.textContent = 'Guardando...';
            btnSubmit.disabled = true;

            if (id) {
                payload.idProductoImpuesto = parseInt(id);
                payload.estado = parseInt(document.getElementById('estadoProductoImpuesto').value);
                await api.put('/productoImpuestos', payload);
                alert('Impuesto editado correctamente');
            } else {
                await api.post('/productoImpuestos', payload);
                alert('Impuesto creado correctamente');
            }
            
            cerrarModalProductoImpuesto();
            cargarProductoImpuestos();
        } catch (error) {
            console.error(error);
            alert('Error al guardar. Verifica la consola.');
        } finally {
            const btnSubmit = document.getElementById('btnGuardarProductoImpuesto');
            btnSubmit.textContent = 'Guardar';
            btnSubmit.disabled = false;
        }
    });
}

async function cargarProductoImpuestos() {
    const tableBody = document.getElementById('tablaProductoImpuestoBody');
    tableBody.innerHTML = '<tr><td colspan="4" style="text-align: center; padding: 2rem;">Cargando impuestos...</td></tr>';
    
    try {
        const response = await api.get('/productoImpuestos?estado=1');
        productoImpuestoData = response.productoImpuestos || response.data || [];
        renderTableProductoImpuesto(productoImpuestoData);
    } catch (error) {
        console.error(error);
        tableBody.innerHTML = '<tr><td colspan="4" style="text-align: center; color: var(--danger);">Error al cargar datos.</td></tr>';
    }
}

let articulosImpuestoMap = {};

async function cargarArticulosParaImpuestos() {
    try {
        const response = await api.get('/articulos?estado=1');
        const articulos = response.articulos || response.data || [];
        const selectArticulo = document.getElementById('idArticuloImpuesto');
        
        articulosImpuestoMap = {};
        const optionsHTML = articulos.map(a => {
            articulosImpuestoMap[a.idArticulos] = `${a.codigoArticulo} - ${a.descripcion}`;
            return `<option value="${a.idArticulos}">${a.codigoArticulo} - ${a.descripcion}</option>`;
        }).join('');
        selectArticulo.innerHTML = '<option value="">Seleccione un artículo...</option>' + optionsHTML;
        
        if (productoImpuestoData.length > 0) renderTableProductoImpuesto(productoImpuestoData);
    } catch (error) {
        console.error('Error cargando artículos', error);
    }
}

function renderTableProductoImpuesto(data) {
    const tableBody = document.getElementById('tablaProductoImpuestoBody');
    tableBody.innerHTML = '';
    
    if (data.length === 0) {
        tableBody.innerHTML = '<tr><td colspan="4" style="text-align: center; padding: 2rem;">No se encontraron registros.</td></tr>';
        return;
    }

    data.forEach(item => {
        const estadoBadge = item.estado === 1 
            ? '<span class="badge badge-success">Activo</span>' 
            : '<span class="badge badge-danger">Inactivo</span>';
        
        const itemId = item.idProductoImpuesto;
        const nombreArticulo = item.descripcionArticulo || articulosImpuestoMap[item.idArticulo] || `Art: ${item.idArticulo || '-'}`;

        const tr = document.createElement('tr');
        tr.innerHTML = `
            <td><strong>${nombreArticulo}</strong></td>
            <td>${parseFloat(item.porcentaje || 0).toFixed(2)} %</td>
            <td>${estadoBadge}</td>
            <td style="text-align: right;">
                <button class="icon-btn" onclick="editarProductoImpuesto(${itemId})" title="Editar"><i class="fa-solid fa-pen text-primary"></i></button>
                ${item.estado === 1 
                    ? `<button class="icon-btn" onclick="anularProductoImpuesto(${itemId})" title="Anular"><i class="fa-solid fa-trash text-danger"></i></button>`
                    : `<button class="icon-btn" onclick="activarProductoImpuesto(${itemId})" title="Activar"><i class="fa-solid fa-check text-success"></i></button>`
                }
            </td>
        `;
        tableBody.appendChild(tr);
    });
}

async function editarProductoImpuesto(id) {
    try {
        const response = await api.get(`/productoImpuestos/${id}`);
        const obj = response.productoImpuesto || response.productoImpuestos || response.data || response;
        
        if(obj) {
            document.getElementById('idProductoImpuesto').value = obj.idProductoImpuesto || id;
            document.getElementById('idArticuloImpuesto').value = obj.idArticulo || '';
            document.getElementById('porcentajeImpuesto').value = obj.porcentaje || '';
            document.getElementById('estadoProductoImpuesto').value = obj.estado !== undefined ? obj.estado : 1;
            
            abrirModalProductoImpuesto(true);
        }
    } catch(e) {
        alert('Error al obtener el detalle');
        console.error(e);
    }
}

async function anularProductoImpuesto(id) {
    if(confirm('¿Estás seguro de anular este impuesto?')) {
        try {
            await api.delete(`/productoImpuestos/${id}`);
            alert('Anulado correctamente');
            cargarProductoImpuestos();
        } catch(e) {
            alert('Error al anular');
        }
    }
}

async function activarProductoImpuesto(id) {
    if(confirm('¿Estás seguro de reactivar este impuesto?')) {
        try {
            await api.patch(`/productoImpuestos/${id}/activar`);
            alert('Activado correctamente');
            cargarProductoImpuestos();
        } catch(e) {
            alert('Error al activar');
        }
    }
}

function abrirModalProductoImpuesto(isEdit = false) {
    document.getElementById('modalProductoImpuesto').style.display = 'flex';
    if(!isEdit) {
        document.getElementById('formProductoImpuesto').reset();
        document.getElementById('idProductoImpuesto').value = '';
        document.getElementById('modalTitleProductoImpuesto').textContent = 'Nuevo Impuesto';
        document.getElementById('estadoProductoImpuestoGroup').style.display = 'none';
    } else {
        document.getElementById('modalTitleProductoImpuesto').textContent = 'Editar Impuesto';
        document.getElementById('estadoProductoImpuestoGroup').style.display = 'block';
    }
}

function cerrarModalProductoImpuesto() {
    document.getElementById('modalProductoImpuesto').style.display = 'none';
}
