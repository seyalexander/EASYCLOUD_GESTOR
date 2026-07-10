// Lógica para Unidad de Medida
let unidadMedidaData = [];

document.addEventListener('viewLoaded', (e) => {
    if (e.detail === 'unidadMedida') {
        initUnidadMedidaModule();
    }
});

function initUnidadMedidaModule() {
    const form = document.getElementById('formUnidadMedida');
    const btnNuevo = document.getElementById('btnNuevoUnidadMedida');
    const btnClose = document.getElementById('btnCloseModalUnidadMedida');
    const btnCancel = document.getElementById('btnCancelModalUnidadMedida');
    
    cargarUnidadesMedida();

    btnNuevo.addEventListener('click', () => abrirModalUnidadMedida());
    btnClose.addEventListener('click', cerrarModalUnidadMedida);
    btnCancel.addEventListener('click', cerrarModalUnidadMedida);

    document.getElementById('searchInputUnidadMedida').addEventListener('input', (e) => {
        const query = e.target.value.toLowerCase();
        const filtrados = unidadMedidaData.filter(a => 
            (a.descripcion && a.descripcion.toLowerCase().includes(query)) ||
            (a.siglas && a.siglas.toLowerCase().includes(query))
        );
        renderTableUnidadMedida(filtrados);
    });

    form.addEventListener('submit', async (e) => {
        e.preventDefault();
        
        const id = document.getElementById('idUnidadMedida').value;
        const payload = {
            descripcion: document.getElementById('descripcionUnidadMedida').value,
            siglas: document.getElementById('siglasUnidadMedida').value
        };

        try {
            const btnSubmit = document.getElementById('btnGuardarUnidadMedida');
            btnSubmit.textContent = 'Guardando...';
            btnSubmit.disabled = true;

            if (id) {
                payload.idUnidadMedida = parseInt(id);
                payload.estado = parseInt(document.getElementById('estadoUnidadMedida').value);
                await api.put('/unidadMedida', payload);
                alert('Unidad editada correctamente');
            } else {
                await api.post('/unidadMedida', payload);
                alert('Unidad creada correctamente');
            }
            
            cerrarModalUnidadMedida();
            cargarUnidadesMedida();
        } catch (error) {
            console.error(error);
            alert('Error al guardar. Verifica la consola.');
        } finally {
            const btnSubmit = document.getElementById('btnGuardarUnidadMedida');
            btnSubmit.textContent = 'Guardar';
            btnSubmit.disabled = false;
        }
    });
}

async function cargarUnidadesMedida() {
    const tableBody = document.getElementById('tablaUnidadMedidaBody');
    tableBody.innerHTML = '<tr><td colspan="4" style="text-align: center; padding: 2rem;">Cargando unidades...</td></tr>';
    
    try {
        const response = await api.get('/unidadMedida?estado=1');
        unidadMedidaData = response.unidadesMedida || [];
        renderTableUnidadMedida(unidadMedidaData);
    } catch (error) {
        console.error(error);
        tableBody.innerHTML = '<tr><td colspan="4" style="text-align: center; color: var(--danger);">Error al cargar datos.</td></tr>';
    }
}

function renderTableUnidadMedida(data) {
    const tableBody = document.getElementById('tablaUnidadMedidaBody');
    tableBody.innerHTML = '';
    
    if (data.length === 0) {
        tableBody.innerHTML = '<tr><td colspan="4" style="text-align: center; padding: 2rem;">No se encontraron registros.</td></tr>';
        return;
    }

    data.forEach(item => {
        const estadoBadge = item.estado === 1 
            ? '<span class="badge badge-success">Activo</span>' 
            : '<span class="badge badge-danger">Inactivo</span>';
        
        const itemId = item.idUnidadMedida;

        const tr = document.createElement('tr');
        tr.innerHTML = `
            <td><strong>${item.siglas || '-'}</strong></td>
            <td>${item.descripcion || '-'}</td>
            <td>${estadoBadge}</td>
            <td style="text-align: right;">
                <button class="icon-btn" onclick="editarUnidadMedida(${itemId})" title="Editar"><i class="fa-solid fa-pen text-primary"></i></button>
                ${item.estado === 1 
                    ? `<button class="icon-btn" onclick="anularUnidadMedida(${itemId})" title="Anular"><i class="fa-solid fa-trash text-danger"></i></button>`
                    : `<button class="icon-btn" onclick="activarUnidadMedida(${itemId})" title="Activar"><i class="fa-solid fa-check text-success"></i></button>`
                }
            </td>
        `;
        tableBody.appendChild(tr);
    });
}

async function editarUnidadMedida(id) {
    try {
        const response = await api.get(`/unidadMedida/${id}`);
        const obj = response.unidadMedida || response.data || response;
        
        if(obj) {
            document.getElementById('idUnidadMedida').value = obj.idUnidadMedida || id;
            document.getElementById('descripcionUnidadMedida').value = obj.descripcion || '';
            document.getElementById('siglasUnidadMedida').value = obj.siglas || '';
            document.getElementById('estadoUnidadMedida').value = obj.estado !== undefined ? obj.estado : 1;
            
            abrirModalUnidadMedida(true);
        }
    } catch(e) {
        alert('Error al obtener el detalle');
        console.error(e);
    }
}

async function anularUnidadMedida(id) {
    if(confirm('¿Estás seguro de anular esta unidad?')) {
        try {
            await api.delete(`/unidadMedida/${id}`);
            alert('Anulada correctamente');
            cargarUnidadesMedida();
        } catch(e) {
            alert('Error al anular');
        }
    }
}

async function activarUnidadMedida(id) {
    if(confirm('¿Estás seguro de reactivar esta unidad?')) {
        try {
            await api.patch(`/unidadMedida/${id}/activar`);
            alert('Activada correctamente');
            cargarUnidadesMedida();
        } catch(e) {
            alert('Error al activar');
        }
    }
}

function abrirModalUnidadMedida(isEdit = false) {
    document.getElementById('modalUnidadMedida').style.display = 'flex';
    if(!isEdit) {
        document.getElementById('formUnidadMedida').reset();
        document.getElementById('idUnidadMedida').value = '';
        document.getElementById('modalTitleUnidadMedida').textContent = 'Nueva Unidad de Medida';
        document.getElementById('estadoUnidadMedidaGroup').style.display = 'none';
    } else {
        document.getElementById('modalTitleUnidadMedida').textContent = 'Editar Unidad de Medida';
        document.getElementById('estadoUnidadMedidaGroup').style.display = 'block';
    }
}

function cerrarModalUnidadMedida() {
    document.getElementById('modalUnidadMedida').style.display = 'none';
}
