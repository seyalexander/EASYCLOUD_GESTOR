// Lógica para Usuarios
let usuarioData = [];

document.addEventListener('viewLoaded', (e) => {
    if (e.detail === 'usuarios') {
        initUsuarioModule();
    }
});

function initUsuarioModule() {
    const form = document.getElementById('formUsuario');
    const btnNuevo = document.getElementById('btnNuevoUsuario');
    const btnClose = document.getElementById('btnCloseModalUsuario');
    const btnCancel = document.getElementById('btnCancelModalUsuario');
    
    cargarUsuarios();
    cargarRolesParaUsuarios();

    btnNuevo.addEventListener('click', () => abrirModalUsuario());
    btnClose.addEventListener('click', cerrarModalUsuario);
    btnCancel.addEventListener('click', cerrarModalUsuario);

    document.getElementById('searchInputUsuario').addEventListener('input', (e) => {
        const query = e.target.value.toLowerCase();
        const filtrados = usuarioData.filter(a => 
            (a.usuario && a.usuario.toLowerCase().includes(query)) ||
            (a.nombre && a.nombre.toLowerCase().includes(query)) ||
            (a.apellido && a.apellido.toLowerCase().includes(query))
        );
        renderTableUsuario(filtrados);
    });

    form.addEventListener('submit', async (e) => {
        e.preventDefault();
        
        const id = document.getElementById('idUsuario').value;
        const pass = document.getElementById('passowrd').value;

        const payload = {
            usuario: document.getElementById('usuarioName').value,
            idRol: parseInt(document.getElementById('idRol').value),
            idEmpleado: parseInt(document.getElementById('idEmpleado').value)
        };

        if (pass) {
            payload.passowrd = pass; // El backend espera "passowrd" (con typo en swagger)
        }

        try {
            const btnSubmit = document.getElementById('btnGuardarUsuario');
            btnSubmit.textContent = 'Guardando...';
            btnSubmit.disabled = true;

            if (id) {
                payload.idUsuario = parseInt(id);
                payload.estado = parseInt(document.getElementById('estadoUsuario').value);
                await api.put('/usuario', payload);
                alert('Usuario editado correctamente');
            } else {
                if(!pass) {
                    alert("La contraseña es obligatoria para un nuevo usuario.");
                    btnSubmit.textContent = 'Guardar';
                    btnSubmit.disabled = false;
                    return;
                }
                await api.post('/usuario', payload);
                alert('Usuario creado correctamente');
            }
            
            cerrarModalUsuario();
            cargarUsuarios();
        } catch (error) {
            console.error(error);
            alert('Error al guardar. Verifica la consola.');
        } finally {
            const btnSubmit = document.getElementById('btnGuardarUsuario');
            btnSubmit.textContent = 'Guardar';
            btnSubmit.disabled = false;
        }
    });
}

async function cargarUsuarios() {
    const tableBody = document.getElementById('tablaUsuarioBody');
    tableBody.innerHTML = '<tr><td colspan="5" style="text-align: center; padding: 2rem;">Cargando usuarios...</td></tr>';
    
    try {
        const response = await api.get('/usuario?estado=1');
        usuarioData = response.usuarios || [];
        renderTableUsuario(usuarioData);
    } catch (error) {
        console.error(error);
        tableBody.innerHTML = '<tr><td colspan="5" style="text-align: center; color: var(--danger);">Error al cargar datos.</td></tr>';
    }
}

async function cargarRolesParaUsuarios() {
    try {
        const response = await api.get('/rol?estado=1');
        const roles = response.roles || [];
        const selectRol = document.getElementById('idRol');
        const optionsHTML = roles.map(r => `<option value="${r.idRol}">${r.descripcion}</option>`).join('');
        selectRol.innerHTML = '<option value="">Seleccione...</option>' + optionsHTML;
    } catch (error) {
        console.error('Error cargando roles', error);
    }
}

function renderTableUsuario(data) {
    const tableBody = document.getElementById('tablaUsuarioBody');
    tableBody.innerHTML = '';
    
    if (data.length === 0) {
        tableBody.innerHTML = '<tr><td colspan="5" style="text-align: center; padding: 2rem;">No se encontraron registros.</td></tr>';
        return;
    }

    data.forEach(item => {
        const estadoBadge = item.estado === 1 
            ? '<span class="badge badge-success">Activo</span>' 
            : '<span class="badge badge-danger">Inactivo</span>';
        
        const itemId = item.idUsuario;
        const nombreCompleto = (item.nombre || '') + ' ' + (item.apellido || '');

        const tr = document.createElement('tr');
        tr.innerHTML = `
            <td><strong>${item.usuario || '-'}</strong></td>
            <td>${nombreCompleto.trim() || '-'}</td>
            <td>${item.descripcionRol || '-'}</td>
            <td>${estadoBadge}</td>
            <td style="text-align: right;">
                <button class="icon-btn" onclick="editarUsuario(${itemId})" title="Editar"><i class="fa-solid fa-pen text-primary"></i></button>
                ${item.estado === 1 
                    ? `<button class="icon-btn" onclick="anularUsuario(${itemId})" title="Anular"><i class="fa-solid fa-trash text-danger"></i></button>`
                    : `<button class="icon-btn" onclick="activarUsuario(${itemId})" title="Activar"><i class="fa-solid fa-check text-success"></i></button>`
                }
            </td>
        `;
        tableBody.appendChild(tr);
    });
}

async function editarUsuario(id) {
    try {
        const response = await api.get(`/usuario/${id}`);
        const obj = response.usuario || response.data || response;
        
        if(obj) {
            document.getElementById('idUsuario').value = obj.idUsuario || id;
            document.getElementById('usuarioName').value = obj.usuario || '';
            document.getElementById('passowrd').value = ''; // No cargar la contraseña
            document.getElementById('passowrd').required = false; // Al editar no es obligatorio
            document.getElementById('idRol').value = obj.idRol || '';
            document.getElementById('idEmpleado').value = obj.idEmpleado || '';
            document.getElementById('estadoUsuario').value = obj.estado !== undefined ? obj.estado : 1;
            
            abrirModalUsuario(true);
        }
    } catch(e) {
        alert('Error al obtener el detalle');
        console.error(e);
    }
}

async function anularUsuario(id) {
    if(confirm('¿Estás seguro de anular este usuario?')) {
        try {
            await api.delete(`/usuario/${id}`);
            alert('Anulado correctamente');
            cargarUsuarios();
        } catch(e) {
            alert('Error al anular');
        }
    }
}

async function activarUsuario(id) {
    if(confirm('¿Estás seguro de reactivar este usuario?')) {
        try {
            await api.patch(`/usuario/${id}/activar`);
            alert('Activado correctamente');
            cargarUsuarios();
        } catch(e) {
            alert('Error al activar');
        }
    }
}

function abrirModalUsuario(isEdit = false) {
    document.getElementById('modalUsuario').style.display = 'flex';
    if(!isEdit) {
        document.getElementById('formUsuario').reset();
        document.getElementById('idUsuario').value = '';
        document.getElementById('modalTitleUsuario').textContent = 'Nuevo Usuario';
        document.getElementById('estadoUsuarioGroup').style.display = 'none';
        document.getElementById('passowrd').required = true;
    } else {
        document.getElementById('modalTitleUsuario').textContent = 'Editar Usuario';
        document.getElementById('estadoUsuarioGroup').style.display = 'block';
    }
}

function cerrarModalUsuario() {
    document.getElementById('modalUsuario').style.display = 'none';
}
