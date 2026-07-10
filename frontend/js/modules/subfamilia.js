// Lógica para SubFamilias
let subfamiliaData = [];

document.addEventListener('viewLoaded', (e) => {
    if (e.detail === 'subfamilia') {
        initSubfamiliaModule();
    }
});

function initSubfamiliaModule() {
    const form = document.getElementById('formSubfamilia');
    const btnNuevo = document.getElementById('btnNuevoSubfamilia');
    const btnClose = document.getElementById('btnCloseModalSubfamilia');
    const btnCancel = document.getElementById('btnCancelModalSubfamilia');
    
    cargarSubfamilias();
    cargarFamiliasParaSubfamilia();

    btnNuevo.addEventListener('click', () => abrirModalSubfamilia());
    btnClose.addEventListener('click', cerrarModalSubfamilia);
    btnCancel.addEventListener('click', cerrarModalSubfamilia);

    document.getElementById('searchInputSubfamilia').addEventListener('input', (e) => {
        const query = e.target.value.toLowerCase();
        const filtrados = subfamiliaData.filter(a => 
            (a.subFamiliaDescripcion && a.subFamiliaDescripcion.toLowerCase().includes(query)) ||
            (a.familiaDescripcion && a.familiaDescripcion.toLowerCase().includes(query))
        );
        renderTableSubfamilia(filtrados);
    });

    form.addEventListener('submit', async (e) => {
        e.preventDefault();
        
        const id = document.getElementById('idSubFamilia').value;
        const payload = {
            idFamilia: parseInt(document.getElementById('idFamiliaSub').value),
            subFamiliaDescripcion: document.getElementById('subFamiliaDescripcion').value,
            imagenUrl: document.getElementById('imagenUrlSub').value
        };

        try {
            const btnSubmit = document.getElementById('btnGuardarSubfamilia');
            btnSubmit.textContent = 'Guardando...';
            btnSubmit.disabled = true;

            if (id) {
                payload.idSubFamilia = parseInt(id);
                payload.estado = parseInt(document.getElementById('estadoSubfamilia').value);
                await api.put('/subfamilia', payload);
                alert('Sub Familia editada correctamente');
            } else {
                await api.post('/subfamilia', payload);
                alert('Sub Familia creada correctamente');
            }
            
            cerrarModalSubfamilia();
            cargarSubfamilias();
        } catch (error) {
            console.error(error);
            alert('Error al guardar. Verifica la consola.');
        } finally {
            const btnSubmit = document.getElementById('btnGuardarSubfamilia');
            btnSubmit.textContent = 'Guardar';
            btnSubmit.disabled = false;
        }
    });
}

async function cargarSubfamilias() {
    const tableBody = document.getElementById('tablaSubfamiliaBody');
    tableBody.innerHTML = '<tr><td colspan="5" style="text-align: center; padding: 2rem;">Cargando sub familias...</td></tr>';
    
    try {
        const response = await api.get('/subfamilia?estado=1');
        subfamiliaData = response.subfamilias || [];
        renderTableSubfamilia(subfamiliaData);
    } catch (error) {
        console.error(error);
        tableBody.innerHTML = '<tr><td colspan="5" style="text-align: center; color: var(--danger);">Error al cargar datos.</td></tr>';
    }
}

async function cargarFamiliasParaSubfamilia() {
    try {
        const response = await api.get('/familia?estado=1');
        const familias = response.familias || [];
        const selectFamilia = document.getElementById('idFamiliaSub');
        const optionsHTML = familias.map(f => `<option value="${f.idFamilia}">${f.descripcion}</option>`).join('');
        selectFamilia.innerHTML = '<option value="">Seleccione...</option>' + optionsHTML;
    } catch (error) {
        console.error('Error cargando familias', error);
    }
}

function renderTableSubfamilia(data) {
    const tableBody = document.getElementById('tablaSubfamiliaBody');
    tableBody.innerHTML = '';
    
    if (data.length === 0) {
        tableBody.innerHTML = '<tr><td colspan="5" style="text-align: center; padding: 2rem;">No se encontraron registros.</td></tr>';
        return;
    }

    data.forEach(item => {
        const estadoBadge = item.estado === 1 
            ? '<span class="badge badge-success">Activo</span>' 
            : '<span class="badge badge-danger">Inactivo</span>';
        
        const itemId = item.idSubFamilia;
        const imgSrc = item.imagenUrl ? `<img src="${item.imagenUrl}" style="width: 32px; height: 32px; border-radius: 4px; object-fit: cover;">` : '<i class="fa-solid fa-image text-muted"></i>';

        const tr = document.createElement('tr');
        tr.innerHTML = `
            <td>${imgSrc}</td>
            <td>${item.familiaDescripcion || '-'}</td>
            <td><strong>${item.subFamiliaDescripcion || '-'}</strong></td>
            <td>${estadoBadge}</td>
            <td style="text-align: right;">
                <button class="icon-btn" onclick="editarSubfamilia(${itemId})" title="Editar"><i class="fa-solid fa-pen text-primary"></i></button>
                ${item.estado === 1 
                    ? `<button class="icon-btn" onclick="anularSubfamilia(${itemId})" title="Anular"><i class="fa-solid fa-trash text-danger"></i></button>`
                    : `<button class="icon-btn" onclick="activarSubfamilia(${itemId})" title="Activar"><i class="fa-solid fa-check text-success"></i></button>`
                }
            </td>
        `;
        tableBody.appendChild(tr);
    });
}

async function editarSubfamilia(id) {
    try {
        const response = await api.get(`/subfamilia/${id}`);
        const obj = response.subFamilia || response.subfamilia || response.data || response;
        
        if(obj) {
            document.getElementById('idSubFamilia').value = obj.idSubFamilia || id;
            document.getElementById('idFamiliaSub').value = obj.idFamilia || '';
            document.getElementById('subFamiliaDescripcion').value = obj.subFamiliaDescripcion || '';
            document.getElementById('imagenUrlSub').value = obj.imagenUrl || '';
            document.getElementById('estadoSubfamilia').value = obj.estado !== undefined ? obj.estado : 1;
            
            abrirModalSubfamilia(true);
        }
    } catch(e) {
        alert('Error al obtener el detalle');
        console.error(e);
    }
}

async function anularSubfamilia(id) {
    if(confirm('¿Estás seguro de anular esta sub familia?')) {
        try {
            await api.delete(`/subfamilia/${id}`);
            alert('Anulada correctamente');
            cargarSubfamilias();
        } catch(e) {
            alert('Error al anular');
        }
    }
}

async function activarSubfamilia(id) {
    if(confirm('¿Estás seguro de reactivar esta sub familia?')) {
        try {
            await api.patch(`/subfamilia/${id}/activar`);
            alert('Activada correctamente');
            cargarSubfamilias();
        } catch(e) {
            alert('Error al activar');
        }
    }
}

function abrirModalSubfamilia(isEdit = false) {
    document.getElementById('modalSubfamilia').style.display = 'flex';
    if(!isEdit) {
        document.getElementById('formSubfamilia').reset();
        document.getElementById('idSubFamilia').value = '';
        document.getElementById('modalTitleSubfamilia').textContent = 'Nueva Sub Familia';
        document.getElementById('estadoSubfamiliaGroup').style.display = 'none';
    } else {
        document.getElementById('modalTitleSubfamilia').textContent = 'Editar Sub Familia';
        document.getElementById('estadoSubfamiliaGroup').style.display = 'block';
    }
}

function cerrarModalSubfamilia() {
    document.getElementById('modalSubfamilia').style.display = 'none';
}
