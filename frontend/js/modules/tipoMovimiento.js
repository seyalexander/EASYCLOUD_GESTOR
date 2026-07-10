// Lógica para Tipo de Movimiento
let tipoMovimientoData = [];

document.addEventListener('viewLoaded', (e) => {
    if (e.detail === 'tipoMovimiento') {
        initTipoMovimientoModule();
    }
});

function initTipoMovimientoModule() {
    const form = document.getElementById('formTipoMovimiento');
    const btnNuevo = document.getElementById('btnNuevoTipoMovimiento');
    const btnClose = document.getElementById('btnCloseModalTipoMovimiento');
    const btnCancel = document.getElementById('btnCancelModalTipoMovimiento');
    
    cargarTipoMovimientos();

    btnNuevo.addEventListener('click', () => abrirModalTipoMovimiento());
    btnClose.addEventListener('click', cerrarModalTipoMovimiento);
    btnCancel.addEventListener('click', cerrarModalTipoMovimiento);

    document.getElementById('searchInputTipoMovimiento').addEventListener('input', (e) => {
        const query = e.target.value.toLowerCase();
        const filtrados = tipoMovimientoData.filter(a => 
            (a.descripcion && a.descripcion.toLowerCase().includes(query))
        );
        renderTableTipoMovimiento(filtrados);
    });

    form.addEventListener('submit', async (e) => {
        e.preventDefault();
        
        const id = document.getElementById('idTipoMovimiento').value;
        const payload = {
            descripcion: document.getElementById('descripcionTipoMov').value,
            esEntrada: parseInt(document.getElementById('esEntradaMov').value)
        };

        try {
            const btnSubmit = document.getElementById('btnGuardarTipoMovimiento');
            btnSubmit.textContent = 'Guardando...';
            btnSubmit.disabled = true;

            if (id) {
                payload.idTipoMovimiento = parseInt(id);
                payload.estado = parseInt(document.getElementById('estadoTipoMovimiento').value);
                await api.put('/tipoMovimiento', payload);
                alert('Tipo de movimiento editado correctamente');
            } else {
                await api.post('/tipoMovimiento', payload);
                alert('Tipo de movimiento creado correctamente');
            }
            
            cerrarModalTipoMovimiento();
            cargarTipoMovimientos();
        } catch (error) {
            console.error(error);
            alert('Error al guardar. Verifica la consola.');
        } finally {
            const btnSubmit = document.getElementById('btnGuardarTipoMovimiento');
            btnSubmit.textContent = 'Guardar';
            btnSubmit.disabled = false;
        }
    });
}

async function cargarTipoMovimientos() {
    const tableBody = document.getElementById('tablaTipoMovimientoBody');
    tableBody.innerHTML = '<tr><td colspan="5" style="text-align: center; padding: 2rem;">Cargando tipos de movimiento...</td></tr>';
    
    try {
        const response = await api.get('/tipoMovimiento?estado=1');
        tipoMovimientoData = response.tipoMovimientos || response.data || [];
        renderTableTipoMovimiento(tipoMovimientoData);
    } catch (error) {
        console.error(error);
        tableBody.innerHTML = '<tr><td colspan="5" style="text-align: center; color: var(--danger);">Error al cargar datos.</td></tr>';
    }
}

function renderTableTipoMovimiento(data) {
    const tableBody = document.getElementById('tablaTipoMovimientoBody');
    tableBody.innerHTML = '';
    
    if (data.length === 0) {
        tableBody.innerHTML = '<tr><td colspan="5" style="text-align: center; padding: 2rem;">No se encontraron registros.</td></tr>';
        return;
    }

    data.forEach(item => {
        const estadoBadge = item.estado === 1 
            ? '<span class="badge badge-success">Activo</span>' 
            : '<span class="badge badge-danger">Inactivo</span>';
            
        const tipoBadge = item.esEntrada === 1 
            ? '<span style="color: var(--success);"><i class="fa-solid fa-arrow-down"></i> Entrada</span>' 
            : '<span style="color: var(--danger);"><i class="fa-solid fa-arrow-up"></i> Salida</span>';
        
        const itemId = item.idTipoMovimiento;

        const tr = document.createElement('tr');
        tr.innerHTML = `
            <td><strong># ${itemId}</strong></td>
            <td>${item.descripcion || '-'}</td>
            <td>${tipoBadge}</td>
            <td>${estadoBadge}</td>
            <td style="text-align: right;">
                <button class="icon-btn" onclick="editarTipoMovimiento(${itemId})" title="Editar"><i class="fa-solid fa-pen text-primary"></i></button>
                ${item.estado === 1 
                    ? `<button class="icon-btn" onclick="anularTipoMovimiento(${itemId})" title="Anular"><i class="fa-solid fa-trash text-danger"></i></button>`
                    : `<button class="icon-btn" onclick="activarTipoMovimiento(${itemId})" title="Activar"><i class="fa-solid fa-check text-success"></i></button>`
                }
            </td>
        `;
        tableBody.appendChild(tr);
    });
}

async function editarTipoMovimiento(id) {
    try {
        const response = await api.get(`/tipoMovimiento/${id}`);
        const obj = response.tipoMovimiento || response.data || response;
        
        if(obj) {
            document.getElementById('idTipoMovimiento').value = obj.idTipoMovimiento || id;
            document.getElementById('descripcionTipoMov').value = obj.descripcion || '';
            document.getElementById('esEntradaMov').value = obj.esEntrada !== undefined ? obj.esEntrada : 1;
            document.getElementById('estadoTipoMovimiento').value = obj.estado !== undefined ? obj.estado : 1;
            
            abrirModalTipoMovimiento(true);
        }
    } catch(e) {
        alert('Error al obtener el detalle');
        console.error(e);
    }
}

async function anularTipoMovimiento(id) {
    if(confirm('¿Estás seguro de anular este tipo de movimiento?')) {
        try {
            await api.delete(`/tipoMovimiento/${id}`);
            alert('Anulado correctamente');
            cargarTipoMovimientos();
        } catch(e) {
            alert('Error al anular');
        }
    }
}

async function activarTipoMovimiento(id) {
    if(confirm('¿Estás seguro de reactivar este tipo de movimiento?')) {
        try {
            await api.patch(`/tipoMovimiento/${id}/activar`);
            alert('Activado correctamente');
            cargarTipoMovimientos();
        } catch(e) {
            alert('Error al activar');
        }
    }
}

function abrirModalTipoMovimiento(isEdit = false) {
    document.getElementById('modalTipoMovimiento').style.display = 'flex';
    if(!isEdit) {
        document.getElementById('formTipoMovimiento').reset();
        document.getElementById('idTipoMovimiento').value = '';
        document.getElementById('modalTitleTipoMovimiento').textContent = 'Nuevo Tipo de Movimiento';
        document.getElementById('estadoTipoMovimientoGroup').style.display = 'none';
    } else {
        document.getElementById('modalTitleTipoMovimiento').textContent = 'Editar Tipo de Movimiento';
        document.getElementById('estadoTipoMovimientoGroup').style.display = 'block';
    }
}

function cerrarModalTipoMovimiento() {
    document.getElementById('modalTipoMovimiento').style.display = 'none';
}
