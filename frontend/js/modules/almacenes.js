// Lógica para Almacenes
let almacenData = [];

document.addEventListener('viewLoaded', (e) => {
    // Escucha ambas rutas posibles por si acaso
    if (e.detail === 'almacenes' || e.detail === 'inventarios') {
        initAlmacenModule();
    }
});

function initAlmacenModule() {
    const form = document.getElementById('formAlmacen');
    const btnNuevo = document.getElementById('btnNuevoAlmacen');
    const btnClose = document.getElementById('btnCloseModalAlmacen');
    const btnCancel = document.getElementById('btnCancelModalAlmacen');
    
    cargarAlmacenes();

    btnNuevo.addEventListener('click', () => abrirModalAlmacen());
    btnClose.addEventListener('click', cerrarModalAlmacen);
    btnCancel.addEventListener('click', cerrarModalAlmacen);

    document.getElementById('searchInputAlmacen').addEventListener('input', (e) => {
        const query = e.target.value.toLowerCase();
        const filtrados = almacenData.filter(a => 
            (a.descripcion && a.descripcion.toLowerCase().includes(query))
        );
        renderTableAlmacen(filtrados);
    });

    form.addEventListener('submit', async (e) => {
        e.preventDefault();
        
        const id = document.getElementById('idAlmacen').value;
        const payload = {
            descripcion: document.getElementById('descripcionAlmacen').value,
            idSucursal: parseInt(document.getElementById('idSucursalAlmacen').value)
        };

        try {
            const btnSubmit = document.getElementById('btnGuardarAlmacen');
            btnSubmit.textContent = 'Guardando...';
            btnSubmit.disabled = true;

            if (id) {
                payload.idAlmacen = parseInt(id);
                await api.put('/almacenes', payload);
                alert('Almacén editado correctamente');
            } else {
                await api.post('/almacenes', payload);
                alert('Almacén creado correctamente');
            }
            
            cerrarModalAlmacen();
            cargarAlmacenes();
        } catch (error) {
            console.error(error);
            alert('Error al guardar. Verifica la consola.');
        } finally {
            const btnSubmit = document.getElementById('btnGuardarAlmacen');
            btnSubmit.textContent = 'Guardar';
            btnSubmit.disabled = false;
        }
    });
}

async function cargarAlmacenes() {
    const tableBody = document.getElementById('tablaAlmacenBody');
    tableBody.innerHTML = '<tr><td colspan="5" style="text-align: center; padding: 2rem;">Cargando almacenes...</td></tr>';
    
    try {
        const response = await api.get('/almacenes?estado=2');
        almacenData = response.almacenes || response.data || [];
        renderTableAlmacen(almacenData);
    } catch (error) {
        console.error(error);
        tableBody.innerHTML = '<tr><td colspan="5" style="text-align: center; color: var(--danger);">Error al cargar datos.</td></tr>';
    }
}

function renderTableAlmacen(data) {
    const tableBody = document.getElementById('tablaAlmacenBody');
    tableBody.innerHTML = '';
    
    if (data.length === 0) {
        tableBody.innerHTML = '<tr><td colspan="5" style="text-align: center; padding: 2rem;">No se encontraron registros.</td></tr>';
        return;
    }

    data.forEach(item => {
        const itemId = item.idAlmacen;
        
        let estadoBadge = item.estado === 1 
            ? '<span class="badge badge-success">Activo</span>' 
            : '<span class="badge badge-error">Inactivo</span>';

        const tr = document.createElement('tr');
        tr.innerHTML = `
            <td><strong># ${itemId}</strong></td>
            <td>${item.descripcion || '-'}</td>
            <td>Sucursal ${item.idSucursal || '-'}</td>
            <td>${estadoBadge}</td>
            <td style="text-align: right;">
                <button class="icon-btn" onclick="editarAlmacen(${itemId})" title="Editar"><i class="fa-solid fa-pen text-primary"></i></button>
                ${item.estado === 1 
                    ? `<button class="icon-btn" onclick="cambiarEstadoAlmacen(${itemId}, 'anular')" title="Anular"><i class="fa-solid fa-ban text-danger"></i></button>`
                    : `<button class="icon-btn" onclick="cambiarEstadoAlmacen(${itemId}, 'activar')" title="Activar"><i class="fa-solid fa-check text-success"></i></button>`
                }
            </td>
        `;
        tableBody.appendChild(tr);
    });
}

async function editarAlmacen(id) {
    try {
        const response = await api.get(`/almacenes/${id}`);
        const obj = response.almacen || response.data || response;
        if(obj) {
            document.getElementById('idAlmacen').value = obj.idAlmacen || id;
            document.getElementById('descripcionAlmacen').value = obj.descripcion || '';
            document.getElementById('idSucursalAlmacen').value = obj.idSucursal || 1;
            
            abrirModalAlmacen(true);
        }
    } catch(e) {
        alert('Error al obtener el detalle');
        console.error(e);
    }
}

async function cambiarEstadoAlmacen(id, accion) {
    if(confirm(`¿Seguro que desea ${accion} este almacén?`)) {
        try {
            if(accion === 'anular') {
                await api.delete(`/almacenes/${id}`);
            } else {
                await api.patch(`/almacenes/${id}/activar`);
            }
            alert(`Almacén ${accion}do correctamente`);
            cargarAlmacenes();
        } catch(e) {
            alert('Error al cambiar estado');
        }
    }
}

function abrirModalAlmacen(isEdit = false) {
    document.getElementById('modalAlmacen').style.display = 'flex';
    if(!isEdit) {
        document.getElementById('formAlmacen').reset();
        document.getElementById('idAlmacen').value = '';
        document.getElementById('modalTitleAlmacen').textContent = 'Nuevo Almacén';
    } else {
        document.getElementById('modalTitleAlmacen').textContent = 'Editar Almacén';
    }
}

function cerrarModalAlmacen() {
    document.getElementById('modalAlmacen').style.display = 'none';
}
