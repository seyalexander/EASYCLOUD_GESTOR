// Lógica para Moneda
let monedaData = [];

document.addEventListener('viewLoaded', (e) => {
    if (e.detail === 'moneda') {
        initMonedaModule();
    }
});

function initMonedaModule() {
    const form = document.getElementById('formMoneda');
    const btnNuevo = document.getElementById('btnNuevoMoneda');
    const btnClose = document.getElementById('btnCloseModalMoneda');
    const btnCancel = document.getElementById('btnCancelModalMoneda');
    
    cargarMonedas();

    btnNuevo.addEventListener('click', () => abrirModalMoneda());
    btnClose.addEventListener('click', cerrarModalMoneda);
    btnCancel.addEventListener('click', cerrarModalMoneda);

    document.getElementById('searchInputMoneda').addEventListener('input', (e) => {
        const query = e.target.value.toLowerCase();
        const filtrados = monedaData.filter(a => 
            (a.descripcion && a.descripcion.toLowerCase().includes(query)) ||
            (a.simbolo && a.simbolo.toLowerCase().includes(query))
        );
        renderTableMoneda(filtrados);
    });

    form.addEventListener('submit', async (e) => {
        e.preventDefault();
        
        const id = document.getElementById('idMoneda').value;
        const payload = {
            descripcion: document.getElementById('descripcionMoneda').value,
            simbolo: document.getElementById('simboloMoneda').value,
            esPrincipal: parseInt(document.getElementById('esPrincipalMoneda').value)
        };

        try {
            const btnSubmit = document.getElementById('btnGuardarMoneda');
            btnSubmit.textContent = 'Guardando...';
            btnSubmit.disabled = true;

            if (id) {
                payload.idMoneda = parseInt(id);
                payload.estado = parseInt(document.getElementById('estadoMoneda').value);
                await api.put('/moneda', payload);
                alert('Moneda editada correctamente');
            } else {
                await api.post('/moneda', payload);
                alert('Moneda creada correctamente');
            }
            
            cerrarModalMoneda();
            cargarMonedas();
        } catch (error) {
            console.error(error);
            alert('Error al guardar. Verifica la consola.');
        } finally {
            const btnSubmit = document.getElementById('btnGuardarMoneda');
            btnSubmit.textContent = 'Guardar';
            btnSubmit.disabled = false;
        }
    });
}

async function cargarMonedas() {
    const tableBody = document.getElementById('tablaMonedaBody');
    tableBody.innerHTML = '<tr><td colspan="5" style="text-align: center; padding: 2rem;">Cargando monedas...</td></tr>';
    
    try {
        const response = await api.get('/moneda?estado=1');
        monedaData = response.monedas || [];
        renderTableMoneda(monedaData);
    } catch (error) {
        console.error(error);
        tableBody.innerHTML = '<tr><td colspan="5" style="text-align: center; color: var(--danger);">Error al cargar datos.</td></tr>';
    }
}

function renderTableMoneda(data) {
    const tableBody = document.getElementById('tablaMonedaBody');
    tableBody.innerHTML = '';
    
    if (data.length === 0) {
        tableBody.innerHTML = '<tr><td colspan="5" style="text-align: center; padding: 2rem;">No se encontraron registros.</td></tr>';
        return;
    }

    data.forEach(item => {
        const estadoBadge = item.estado === 1 
            ? '<span class="badge badge-success">Activo</span>' 
            : '<span class="badge badge-danger">Inactivo</span>';
            
        const principalBadge = item.esPrincipal === 1 
            ? '<span style="color: var(--primary);"><i class="fa-solid fa-star"></i> Sí</span>' 
            : '<span style="color: var(--text-muted);">No</span>';
        
        const itemId = item.idMoneda;

        const tr = document.createElement('tr');
        tr.innerHTML = `
            <td><strong>${item.simbolo || '-'}</strong></td>
            <td>${item.descripcion || '-'}</td>
            <td>${principalBadge}</td>
            <td>${estadoBadge}</td>
            <td style="text-align: right;">
                <button class="icon-btn" onclick="editarMoneda(${itemId})" title="Editar"><i class="fa-solid fa-pen text-primary"></i></button>
                ${item.estado === 1 
                    ? `<button class="icon-btn" onclick="anularMoneda(${itemId})" title="Anular"><i class="fa-solid fa-trash text-danger"></i></button>`
                    : `<button class="icon-btn" onclick="activarMoneda(${itemId})" title="Activar"><i class="fa-solid fa-check text-success"></i></button>`
                }
            </td>
        `;
        tableBody.appendChild(tr);
    });
}

async function editarMoneda(id) {
    try {
        const response = await api.get(`/moneda/${id}`);
        const obj = response.moneda || response.data || response;
        
        if(obj) {
            document.getElementById('idMoneda').value = obj.idMoneda || id;
            document.getElementById('descripcionMoneda').value = obj.descripcion || '';
            document.getElementById('simboloMoneda').value = obj.simbolo || '';
            document.getElementById('esPrincipalMoneda').value = obj.esPrincipal !== undefined ? obj.esPrincipal : 0;
            document.getElementById('estadoMoneda').value = obj.estado !== undefined ? obj.estado : 1;
            
            abrirModalMoneda(true);
        }
    } catch(e) {
        alert('Error al obtener el detalle');
        console.error(e);
    }
}

async function anularMoneda(id) {
    if(confirm('¿Estás seguro de anular esta moneda?')) {
        try {
            await api.delete(`/moneda/${id}`);
            alert('Anulada correctamente');
            cargarMonedas();
        } catch(e) {
            alert('Error al anular');
        }
    }
}

async function activarMoneda(id) {
    if(confirm('¿Estás seguro de reactivar esta moneda?')) {
        try {
            await api.patch(`/moneda/${id}/activar`);
            alert('Activada correctamente');
            cargarMonedas();
        } catch(e) {
            alert('Error al activar');
        }
    }
}

function abrirModalMoneda(isEdit = false) {
    document.getElementById('modalMoneda').style.display = 'flex';
    if(!isEdit) {
        document.getElementById('formMoneda').reset();
        document.getElementById('idMoneda').value = '';
        document.getElementById('modalTitleMoneda').textContent = 'Nueva Moneda';
        document.getElementById('estadoMonedaGroup').style.display = 'none';
    } else {
        document.getElementById('modalTitleMoneda').textContent = 'Editar Moneda';
        document.getElementById('estadoMonedaGroup').style.display = 'block';
    }
}

function cerrarModalMoneda() {
    document.getElementById('modalMoneda').style.display = 'none';
}
