// Lógica para Rol
let rolData = [];

document.addEventListener('viewLoaded', (e) => {
    if (e.detail === 'rol') {
        initRolModule();
    }
});

function initRolModule() {
    const form = document.getElementById('formRol');
    const btnNuevo = document.getElementById('btnNuevoRol');
    const btnClose = document.getElementById('btnCloseModalRol');
    const btnCancel = document.getElementById('btnCancelModalRol');
    
    cargarRoles();

    btnNuevo.addEventListener('click', () => abrirModalRol());
    btnClose.addEventListener('click', cerrarModalRol);
    btnCancel.addEventListener('click', cerrarModalRol);

    document.getElementById('searchInputRol').addEventListener('input', (e) => {
        const query = e.target.value.toLowerCase();
        const filtrados = rolData.filter(a => 
            (a.descripcion && a.descripcion.toLowerCase().includes(query))
        );
        renderTableRol(filtrados);
    });

    form.addEventListener('submit', async (e) => {
        e.preventDefault();
        
        const id = document.getElementById('idRol').value;
        const payload = {
            descripcion: document.getElementById('descripcionRol').value
        };

        try {
            const btnSubmit = document.getElementById('btnGuardarRol');
            btnSubmit.textContent = 'Guardando...';
            btnSubmit.disabled = true;

            if (id) {
                payload.idRol = parseInt(id);
                payload.estado = parseInt(document.getElementById('estadoRol').value);
                await api.put('/rol', payload);
                alert('Rol editado correctamente');
            } else {
                await api.post('/rol', payload);
                alert('Rol creado correctamente');
            }
            
            cerrarModalRol();
            cargarRoles();
        } catch (error) {
            console.error(error);
            alert('Error al guardar. Verifica la consola.');
        } finally {
            const btnSubmit = document.getElementById('btnGuardarRol');
            btnSubmit.textContent = 'Guardar';
            btnSubmit.disabled = false;
        }
    });
}

async function cargarRoles() {
    const tableBody = document.getElementById('tablaRolBody');
    tableBody.innerHTML = '<tr><td colspan="4" style="text-align: center; padding: 2rem;">Cargando roles...</td></tr>';
    
    try {
        const response = await api.get('/rol?estado=1');
        rolData = response.roles || [];
        renderTableRol(rolData);
    } catch (error) {
        console.error(error);
        tableBody.innerHTML = '<tr><td colspan="4" style="text-align: center; color: var(--danger);">Error al cargar datos.</td></tr>';
    }
}

function renderTableRol(data) {
    const tableBody = document.getElementById('tablaRolBody');
    tableBody.innerHTML = '';
    
    if (data.length === 0) {
        tableBody.innerHTML = '<tr><td colspan="4" style="text-align: center; padding: 2rem;">No se encontraron registros.</td></tr>';
        return;
    }

    data.forEach(item => {
        const estadoBadge = item.estado === 1 
            ? '<span class="badge badge-success">Activo</span>' 
            : '<span class="badge badge-danger">Inactivo</span>';
        
        const itemId = item.idRol;

        const tr = document.createElement('tr');
        tr.innerHTML = `
            <td><strong># ${itemId}</strong></td>
            <td>${item.descripcion || '-'}</td>
            <td>${estadoBadge}</td>
            <td style="text-align: right;">
                <button class="icon-btn" onclick="editarRol(${itemId})" title="Editar"><i class="fa-solid fa-pen text-primary"></i></button>
                ${item.estado === 1 
                    ? `<button class="icon-btn" onclick="anularRol(${itemId})" title="Anular"><i class="fa-solid fa-trash text-danger"></i></button>`
                    : `<button class="icon-btn" onclick="activarRol(${itemId})" title="Activar"><i class="fa-solid fa-check text-success"></i></button>`
                }
            </td>
        `;
        tableBody.appendChild(tr);
    });
}

async function editarRol(id) {
    try {
        const response = await api.get(`/rol/${id}`);
        const obj = response.rol || response.data || response;
        
        if(obj) {
            document.getElementById('idRol').value = obj.idRol || id;
            document.getElementById('descripcionRol').value = obj.descripcion || '';
            document.getElementById('estadoRol').value = obj.estado !== undefined ? obj.estado : 1;
            
            abrirModalRol(true);
        }
    } catch(e) {
        alert('Error al obtener el detalle');
        console.error(e);
    }
}

async function anularRol(id) {
    if(confirm('¿Estás seguro de anular este rol?')) {
        try {
            await api.delete(`/rol/${id}`);
            alert('Anulado correctamente');
            cargarRoles();
        } catch(e) {
            alert('Error al anular');
        }
    }
}

async function activarRol(id) {
    if(confirm('¿Estás seguro de reactivar este rol?')) {
        try {
            await api.patch(`/rol/${id}/activar`);
            alert('Activado correctamente');
            cargarRoles();
        } catch(e) {
            alert('Error al activar');
        }
    }
}

function abrirModalRol(isEdit = false) {
    document.getElementById('modalRol').style.display = 'flex';
    if(!isEdit) {
        document.getElementById('formRol').reset();
        document.getElementById('idRol').value = '';
        document.getElementById('modalTitleRol').textContent = 'Nuevo Rol';
        document.getElementById('estadoRolGroup').style.display = 'none';
    } else {
        document.getElementById('modalTitleRol').textContent = 'Editar Rol';
        document.getElementById('estadoRolGroup').style.display = 'block';
    }
}

function cerrarModalRol() {
    document.getElementById('modalRol').style.display = 'none';
}
