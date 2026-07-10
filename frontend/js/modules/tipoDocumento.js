// Lógica para Tipo de Documento
let tipoDocumentoData = [];

document.addEventListener('viewLoaded', (e) => {
    if (e.detail === 'tipoDocumento') {
        initTipoDocumentoModule();
    }
});

function initTipoDocumentoModule() {
    const form = document.getElementById('formTipoDocumento');
    const btnNuevo = document.getElementById('btnNuevoTipoDocumento');
    const btnClose = document.getElementById('btnCloseModalTipoDocumento');
    const btnCancel = document.getElementById('btnCancelModalTipoDocumento');
    
    cargarTipoDocumentos();

    btnNuevo.addEventListener('click', () => abrirModalTipoDocumento());
    btnClose.addEventListener('click', cerrarModalTipoDocumento);
    btnCancel.addEventListener('click', cerrarModalTipoDocumento);

    document.getElementById('searchInputTipoDocumento').addEventListener('input', (e) => {
        const query = e.target.value.toLowerCase();
        const filtrados = tipoDocumentoData.filter(a => 
            (a.descripcion && a.descripcion.toLowerCase().includes(query)) ||
            (a.codigoSunat && a.codigoSunat.toLowerCase().includes(query))
        );
        renderTableTipoDocumento(filtrados);
    });

    form.addEventListener('submit', async (e) => {
        e.preventDefault();
        
        const id = document.getElementById('idTipoDocumentos').value;
        const payload = {
            descripcion: document.getElementById('descripcionTipoDoc').value,
            codigoSunat: document.getElementById('codigoSunatDoc').value,
            tipoCaracter: document.getElementById('tipoCaracterDoc').value,
            longitudMin: parseInt(document.getElementById('longitudMinDoc').value),
            longitudMax: parseInt(document.getElementById('longitudMaxDoc').value)
        };

        try {
            const btnSubmit = document.getElementById('btnGuardarTipoDocumento');
            btnSubmit.textContent = 'Guardando...';
            btnSubmit.disabled = true;

            if (id) {
                payload.idTipoDocumentos = parseInt(id);
                payload.estado = parseInt(document.getElementById('estadoTipoDocumento').value);
                await api.put('/tipoDocumento', payload);
                alert('Tipo editado correctamente');
            } else {
                await api.post('/tipoDocumento', payload);
                alert('Tipo creado correctamente');
            }
            
            cerrarModalTipoDocumento();
            cargarTipoDocumentos();
        } catch (error) {
            console.error(error);
            alert('Error al guardar. Verifica la consola.');
        } finally {
            const btnSubmit = document.getElementById('btnGuardarTipoDocumento');
            btnSubmit.textContent = 'Guardar';
            btnSubmit.disabled = false;
        }
    });
}

async function cargarTipoDocumentos() {
    const tableBody = document.getElementById('tablaTipoDocumentoBody');
    tableBody.innerHTML = '<tr><td colspan="5" style="text-align: center; padding: 2rem;">Cargando tipos de documento...</td></tr>';
    
    try {
        const response = await api.get('/tipoDocumento?estado=1');
        tipoDocumentoData = response.tipoDocumentos || response.data || [];
        renderTableTipoDocumento(tipoDocumentoData);
    } catch (error) {
        console.error(error);
        tableBody.innerHTML = '<tr><td colspan="5" style="text-align: center; color: var(--danger);">Error al cargar datos.</td></tr>';
    }
}

function renderTableTipoDocumento(data) {
    const tableBody = document.getElementById('tablaTipoDocumentoBody');
    tableBody.innerHTML = '';
    
    if (data.length === 0) {
        tableBody.innerHTML = '<tr><td colspan="5" style="text-align: center; padding: 2rem;">No se encontraron registros.</td></tr>';
        return;
    }

    data.forEach(item => {
        const estadoBadge = item.estado === 1 
            ? '<span class="badge badge-success">Activo</span>' 
            : '<span class="badge badge-danger">Inactivo</span>';
        
        const itemId = item.idTipoDocumentos;

        const tr = document.createElement('tr');
        tr.innerHTML = `
            <td><strong>${item.codigoSunat || '-'}</strong></td>
            <td>${item.descripcion || '-'}</td>
            <td>${item.tipoCaracter || '-'} [${item.longitudMin}-${item.longitudMax}]</td>
            <td>${estadoBadge}</td>
            <td style="text-align: right;">
                <button class="icon-btn" onclick="editarTipoDocumento(${itemId})" title="Editar"><i class="fa-solid fa-pen text-primary"></i></button>
                ${item.estado === 1 
                    ? `<button class="icon-btn" onclick="anularTipoDocumento(${itemId})" title="Anular"><i class="fa-solid fa-trash text-danger"></i></button>`
                    : `<button class="icon-btn" onclick="activarTipoDocumento(${itemId})" title="Activar"><i class="fa-solid fa-check text-success"></i></button>`
                }
            </td>
        `;
        tableBody.appendChild(tr);
    });
}

async function editarTipoDocumento(id) {
    try {
        const response = await api.get(`/tipoDocumento/${id}`);
        const obj = response.tipoDocumento || response.data || response;
        
        if(obj) {
            document.getElementById('idTipoDocumentos').value = obj.idTipoDocumentos || id;
            document.getElementById('descripcionTipoDoc').value = obj.descripcion || '';
            document.getElementById('codigoSunatDoc').value = obj.codigoSunat || '';
            document.getElementById('tipoCaracterDoc').value = obj.tipoCaracter || '';
            document.getElementById('longitudMinDoc').value = obj.longitudMin || '';
            document.getElementById('longitudMaxDoc').value = obj.longitudMax || '';
            document.getElementById('estadoTipoDocumento').value = obj.estado !== undefined ? obj.estado : 1;
            
            abrirModalTipoDocumento(true);
        }
    } catch(e) {
        alert('Error al obtener el detalle');
        console.error(e);
    }
}

async function anularTipoDocumento(id) {
    if(confirm('¿Estás seguro de anular este tipo?')) {
        try {
            await api.delete(`/tipoDocumento/${id}`);
            alert('Anulado correctamente');
            cargarTipoDocumentos();
        } catch(e) {
            alert('Error al anular');
        }
    }
}

async function activarTipoDocumento(id) {
    if(confirm('¿Estás seguro de reactivar este tipo?')) {
        try {
            await api.patch(`/tipoDocumento/${id}/activar`);
            alert('Activado correctamente');
            cargarTipoDocumentos();
        } catch(e) {
            alert('Error al activar');
        }
    }
}

function abrirModalTipoDocumento(isEdit = false) {
    document.getElementById('modalTipoDocumento').style.display = 'flex';
    if(!isEdit) {
        document.getElementById('formTipoDocumento').reset();
        document.getElementById('idTipoDocumentos').value = '';
        document.getElementById('modalTitleTipoDocumento').textContent = 'Nuevo Tipo de Documento';
        document.getElementById('estadoTipoDocumentoGroup').style.display = 'none';
    } else {
        document.getElementById('modalTitleTipoDocumento').textContent = 'Editar Tipo de Documento';
        document.getElementById('estadoTipoDocumentoGroup').style.display = 'block';
    }
}

function cerrarModalTipoDocumento() {
    document.getElementById('modalTipoDocumento').style.display = 'none';
}
