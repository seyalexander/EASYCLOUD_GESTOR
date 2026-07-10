// Lógica para Proveedores
let proveedorData = [];
let tiposDocProveedor = [];

document.addEventListener('viewLoaded', (e) => {
    if (e.detail === 'proveedores') {
        initProveedorModule();
    }
});

function initProveedorModule() {
    const form = document.getElementById('formProveedor');
    const btnNuevo = document.getElementById('btnNuevoProveedor');
    const btnClose = document.getElementById('btnCloseModalProveedor');
    const btnCancel = document.getElementById('btnCancelModalProveedor');
    
    cargarCatalogoProveedor();
    cargarProveedores();

    btnNuevo.addEventListener('click', () => abrirModalProveedor());
    btnClose.addEventListener('click', cerrarModalProveedor);
    btnCancel.addEventListener('click', cerrarModalProveedor);

    document.getElementById('searchInputProveedor').addEventListener('input', (e) => {
        const query = e.target.value.toLowerCase();
        const filtrados = proveedorData.filter(a => 
            (a.razonSocial && a.razonSocial.toLowerCase().includes(query)) ||
            (a.ruc && a.ruc.toLowerCase().includes(query))
        );
        renderTableProveedor(filtrados);
    });

    form.addEventListener('submit', async (e) => {
        e.preventDefault();
        
        const id = document.getElementById('idProveedor').value;
        const payload = {
            razonSocial: document.getElementById('razonSocialProveedor').value,
            ruc: document.getElementById('rucProveedor').value,
            idTipoDocumento: parseInt(document.getElementById('idTipoDocumentoProveedor').value),
            telefono: document.getElementById('telefonoProveedor').value,
            email: document.getElementById('emailProveedor').value,
            direccion: document.getElementById('direccionProveedor').value
        };

        try {
            const btnSubmit = document.getElementById('btnGuardarProveedor');
            btnSubmit.textContent = 'Guardando...';
            btnSubmit.disabled = true;

            if (id) {
                payload.idProveedor = parseInt(id);
                await api.put('/Proveedores', payload);
                alert('Proveedor editado correctamente');
            } else {
                await api.post('/Proveedores', payload);
                alert('Proveedor creado correctamente');
            }
            
            cerrarModalProveedor();
            cargarProveedores();
        } catch (error) {
            console.error(error);
            alert('Error al guardar. Verifica la consola.');
        } finally {
            const btnSubmit = document.getElementById('btnGuardarProveedor');
            btnSubmit.textContent = 'Guardar';
            btnSubmit.disabled = false;
        }
    });
}

async function cargarCatalogoProveedor() {
    try {
        const resTd = await api.get('/TipoDocumento?estado=1');
        tiposDocProveedor = resTd.tipoDocumentos || resTd.data || [];
        document.getElementById('idTipoDocumentoProveedor').innerHTML = '<option value="">Seleccione...</option>' + 
            tiposDocProveedor.map(t => `<option value="${t.idTipoDocumento}">${t.descripcion}</option>`).join('');
    } catch(e) {
        console.warn('Error cargando catálogos de proveedores', e);
    }
}

async function cargarProveedores() {
    const tableBody = document.getElementById('tablaProveedorBody');
    tableBody.innerHTML = '<tr><td colspan="6" style="text-align: center; padding: 2rem;">Cargando proveedores...</td></tr>';
    
    try {
        const response = await api.get('/Proveedores?estado=2'); 
        proveedorData = response.proveedores || response.data || [];
        renderTableProveedor(proveedorData);
    } catch (error) {
        console.error(error);
        tableBody.innerHTML = '<tr><td colspan="6" style="text-align: center; color: var(--danger);">Error al cargar datos.</td></tr>';
    }
}

function renderTableProveedor(data) {
    const tableBody = document.getElementById('tablaProveedorBody');
    tableBody.innerHTML = '';
    
    if (data.length === 0) {
        tableBody.innerHTML = '<tr><td colspan="6" style="text-align: center; padding: 2rem;">No se encontraron registros.</td></tr>';
        return;
    }

    data.forEach(item => {
        const itemId = item.idProveedor;
        
        let estadoBadge = item.estado === 1 
            ? '<span class="badge badge-success">Activo</span>' 
            : '<span class="badge badge-error">Inactivo</span>';

        const tr = document.createElement('tr');
        tr.innerHTML = `
            <td><strong># ${itemId}</strong></td>
            <td>${item.ruc || '-'}</td>
            <td>${item.razonSocial || '-'}</td>
            <td>${item.telefono || '-'}</td>
            <td>${estadoBadge}</td>
            <td style="text-align: right;">
                <button class="icon-btn" onclick="editarProveedor(${itemId})" title="Editar"><i class="fa-solid fa-pen text-primary"></i></button>
                ${item.estado === 1 
                    ? `<button class="icon-btn" onclick="cambiarEstadoProveedor(${itemId}, 'anular')" title="Anular"><i class="fa-solid fa-ban text-danger"></i></button>`
                    : `<button class="icon-btn" onclick="cambiarEstadoProveedor(${itemId}, 'activar')" title="Activar"><i class="fa-solid fa-check text-success"></i></button>`
                }
            </td>
        `;
        tableBody.appendChild(tr);
    });
}

async function editarProveedor(id) {
    try {
        const response = await api.get(`/Proveedores/${id}`);
        const obj = response.proveedor || response.data || response;
        if(obj) {
            document.getElementById('idProveedor').value = obj.idProveedor || id;
            document.getElementById('idTipoDocumentoProveedor').value = obj.idTipoDocumento || '';
            document.getElementById('rucProveedor').value = obj.ruc || '';
            document.getElementById('razonSocialProveedor').value = obj.razonSocial || '';
            document.getElementById('telefonoProveedor').value = obj.telefono || '';
            document.getElementById('emailProveedor').value = obj.email || '';
            document.getElementById('direccionProveedor').value = obj.direccion || '';
            
            abrirModalProveedor(true);
        }
    } catch(e) {
        alert('Error al obtener el detalle');
        console.error(e);
    }
}

async function cambiarEstadoProveedor(id, accion) {
    if(confirm(`¿Seguro que desea ${accion} este proveedor?`)) {
        try {
            if(accion === 'anular') {
                await api.delete(`/Proveedores/${id}`);
            } else {
                await api.patch(`/Proveedores/${id}/activar`);
            }
            alert(`Proveedor ${accion}do correctamente`);
            cargarProveedores();
        } catch(e) {
            alert('Error al cambiar estado');
        }
    }
}

function abrirModalProveedor(isEdit = false) {
    document.getElementById('modalProveedor').style.display = 'flex';
    if(!isEdit) {
        document.getElementById('formProveedor').reset();
        document.getElementById('idProveedor').value = '';
        document.getElementById('modalTitleProveedor').textContent = 'Nuevo Proveedor';
    } else {
        document.getElementById('modalTitleProveedor').textContent = 'Editar Proveedor';
    }
}

function cerrarModalProveedor() {
    document.getElementById('modalProveedor').style.display = 'none';
}
