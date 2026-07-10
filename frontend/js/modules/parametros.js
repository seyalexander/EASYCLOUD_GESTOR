// Lógica para Parámetros
let parametrosData = [];

document.addEventListener('viewLoaded', (e) => {
    if (e.detail === 'parametros') {
        initParametrosModule();
    }
});

function initParametrosModule() {
    const form = document.getElementById('formParametros');
    const btnNuevo = document.getElementById('btnNuevoParametro');
    const btnClose = document.getElementById('btnCloseModalParametros');
    const btnCancel = document.getElementById('btnCancelModalParametros');
    
    cargarParametros();

    btnNuevo.addEventListener('click', () => abrirModalParametros());
    btnClose.addEventListener('click', cerrarModalParametros);
    btnCancel.addEventListener('click', cerrarModalParametros);

    document.getElementById('searchInputParametros').addEventListener('input', (e) => {
        const query = e.target.value.toLowerCase();
        const filtrados = parametrosData.filter(a => 
            (a.clave && a.clave.toLowerCase().includes(query)) ||
            (a.descripcion && a.descripcion.toLowerCase().includes(query))
        );
        renderTableParametros(filtrados);
    });

    form.addEventListener('submit', async (e) => {
        e.preventDefault();
        
        const id = document.getElementById('idParametros').value;
        const payload = {
            clave: document.getElementById('claveParametro').value,
            valor: document.getElementById('valorParametro').value,
            descripcion: document.getElementById('descripcionParametro').value
        };

        try {
            const btnSubmit = document.getElementById('btnGuardarParametro');
            btnSubmit.textContent = 'Guardando...';
            btnSubmit.disabled = true;

            if (id) {
                payload.idParametros = parseInt(id); // Backend espera idParametros en PUT
                payload.estado = parseInt(document.getElementById('estadoParametro').value);
                await api.put('/parametros', payload);
                alert('Parámetro editado correctamente');
            } else {
                await api.post('/parametros', payload);
                alert('Parámetro creado correctamente');
            }
            
            cerrarModalParametros();
            cargarParametros();
        } catch (error) {
            console.error(error);
            alert('Error al guardar. Verifica la consola.');
        } finally {
            const btnSubmit = document.getElementById('btnGuardarParametro');
            btnSubmit.textContent = 'Guardar';
            btnSubmit.disabled = false;
        }
    });
}

async function cargarParametros() {
    const tableBody = document.getElementById('tablaParametrosBody');
    tableBody.innerHTML = '<tr><td colspan="5" style="text-align: center; padding: 2rem;">Cargando parámetros...</td></tr>';
    
    try {
        const response = await api.get('/parametros?estado=1');
        parametrosData = response.parametros || [];
        renderTableParametros(parametrosData);
    } catch (error) {
        console.error(error);
        tableBody.innerHTML = '<tr><td colspan="5" style="text-align: center; color: var(--danger);">Error al cargar datos.</td></tr>';
    }
}

function renderTableParametros(data) {
    const tableBody = document.getElementById('tablaParametrosBody');
    tableBody.innerHTML = '';
    
    if (data.length === 0) {
        tableBody.innerHTML = '<tr><td colspan="5" style="text-align: center; padding: 2rem;">No se encontraron registros.</td></tr>';
        return;
    }

    data.forEach(item => {
        const estadoBadge = item.estado === 1 
            ? '<span class="badge badge-success">Activo</span>' 
            : '<span class="badge badge-danger">Inactivo</span>';
        
        const itemId = item.idParametros || item.idParametroSistema;

        const tr = document.createElement('tr');
        tr.innerHTML = `
            <td><strong>${item.clave || '-'}</strong></td>
            <td>${item.valor || '-'}</td>
            <td>${item.descripcion || '-'}</td>
            <td>${estadoBadge}</td>
            <td style="text-align: right;">
                <button class="icon-btn" onclick="editarParametro(${itemId})" title="Editar"><i class="fa-solid fa-pen text-primary"></i></button>
                ${item.estado === 1 
                    ? `<button class="icon-btn" onclick="anularParametro(${itemId})" title="Anular"><i class="fa-solid fa-trash text-danger"></i></button>`
                    : `<button class="icon-btn" onclick="activarParametro(${itemId})" title="Activar"><i class="fa-solid fa-check text-success"></i></button>`
                }
            </td>
        `;
        tableBody.appendChild(tr);
    });
}

async function editarParametro(id) {
    try {
        const response = await api.get(`/parametros/${id}`);
        const obj = response.parametros || response.parametro || response.data || response;
        
        if(obj) {
            document.getElementById('idParametros').value = obj.idParametros || obj.idParametroSistema || id;
            document.getElementById('claveParametro').value = obj.clave || '';
            document.getElementById('valorParametro').value = obj.valor || '';
            document.getElementById('descripcionParametro').value = obj.descripcion || '';
            document.getElementById('estadoParametro').value = obj.estado !== undefined ? obj.estado : 1;
            
            abrirModalParametros(true);
        }
    } catch(e) {
        alert('Error al obtener el detalle');
        console.error(e);
    }
}

async function anularParametro(id) {
    if(confirm('¿Estás seguro de anular este parámetro?')) {
        try {
            await api.delete(`/parametros/${id}`);
            alert('Anulado correctamente');
            cargarParametros();
        } catch(e) {
            alert('Error al anular');
        }
    }
}

async function activarParametro(id) {
    if(confirm('¿Estás seguro de reactivar este parámetro?')) {
        try {
            await api.patch(`/parametros/${id}/activar`);
            alert('Activado correctamente');
            cargarParametros();
        } catch(e) {
            alert('Error al activar');
        }
    }
}

function abrirModalParametros(isEdit = false) {
    document.getElementById('modalParametros').style.display = 'flex';
    if(!isEdit) {
        document.getElementById('formParametros').reset();
        document.getElementById('idParametros').value = '';
        document.getElementById('modalTitleParametros').textContent = 'Nuevo Parámetro';
        document.getElementById('estadoParametroGroup').style.display = 'none';
    } else {
        document.getElementById('modalTitleParametros').textContent = 'Editar Parámetro';
        document.getElementById('estadoParametroGroup').style.display = 'block';
    }
}

function cerrarModalParametros() {
    document.getElementById('modalParametros').style.display = 'none';
}
