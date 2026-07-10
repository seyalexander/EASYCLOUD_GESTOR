// Lógica para Tipo de Comprobante
let tipoComprobanteData = [];

document.addEventListener('viewLoaded', (e) => {
    if (e.detail === 'tipocomprobante') {
        initTipoComprobanteModule();
    }
});

function initTipoComprobanteModule() {
    const form = document.getElementById('formTipoComprobante');
    const btnNuevo = document.getElementById('btnNuevoTipoComprobante');
    const btnClose = document.getElementById('btnCloseModalTipoComprobante');
    const btnCancel = document.getElementById('btnCancelModalTipoComprobante');
    
    cargarTipoComprobantes();

    btnNuevo.addEventListener('click', () => abrirModalTipoComprobante());
    btnClose.addEventListener('click', cerrarModalTipoComprobante);
    btnCancel.addEventListener('click', cerrarModalTipoComprobante);

    document.getElementById('searchInputTipoComprobante').addEventListener('input', (e) => {
        const query = e.target.value.toLowerCase();
        const filtrados = tipoComprobanteData.filter(a => 
            (a.descripcion && a.descripcion.toLowerCase().includes(query)) ||
            (a.codigoSunat && a.codigoSunat.toLowerCase().includes(query))
        );
        renderTableTipoComprobante(filtrados);
    });

    form.addEventListener('submit', async (e) => {
        e.preventDefault();
        
        const id = document.getElementById('idTipoComprobante').value;
        const payload = {
            descripcion: document.getElementById('descripcionTipoComp').value,
            codigoSunat: document.getElementById('codigoSunatComp').value
        };

        try {
            const btnSubmit = document.getElementById('btnGuardarTipoComprobante');
            btnSubmit.textContent = 'Guardando...';
            btnSubmit.disabled = true;

            if (id) {
                payload.idTipoComprobante = parseInt(id);
                payload.estado = parseInt(document.getElementById('estadoTipoComprobante').value);
                await api.put('/tipocomprobante', payload);
                alert('Tipo de comprobante editado correctamente');
            } else {
                await api.post('/tipocomprobante', payload);
                alert('Tipo de comprobante creado correctamente');
            }
            
            cerrarModalTipoComprobante();
            cargarTipoComprobantes();
        } catch (error) {
            console.error(error);
            alert('Error al guardar. Verifica la consola.');
        } finally {
            const btnSubmit = document.getElementById('btnGuardarTipoComprobante');
            btnSubmit.textContent = 'Guardar';
            btnSubmit.disabled = false;
        }
    });
}

async function cargarTipoComprobantes() {
    const tableBody = document.getElementById('tablaTipoComprobanteBody');
    tableBody.innerHTML = '<tr><td colspan="4" style="text-align: center; padding: 2rem;">Cargando tipos de comprobante...</td></tr>';
    
    try {
        const response = await api.get('/tipocomprobante?estado=1');
        tipoComprobanteData = response.tipoComprobante || response.tiposComprobante || response.data || [];
        renderTableTipoComprobante(tipoComprobanteData);
    } catch (error) {
        console.error(error);
        tableBody.innerHTML = '<tr><td colspan="4" style="text-align: center; color: var(--danger);">Error al cargar datos.</td></tr>';
    }
}

function renderTableTipoComprobante(data) {
    const tableBody = document.getElementById('tablaTipoComprobanteBody');
    tableBody.innerHTML = '';
    
    if (data.length === 0) {
        tableBody.innerHTML = '<tr><td colspan="4" style="text-align: center; padding: 2rem;">No se encontraron registros.</td></tr>';
        return;
    }

    data.forEach(item => {
        const estadoBadge = item.estado === 1 
            ? '<span class="badge badge-success">Activo</span>' 
            : '<span class="badge badge-danger">Inactivo</span>';
        
        const itemId = item.idTipoComprobante;

        const tr = document.createElement('tr');
        tr.innerHTML = `
            <td><strong>${item.codigoSunat || '-'}</strong></td>
            <td>${item.descripcion || '-'}</td>
            <td>${estadoBadge}</td>
            <td style="text-align: right;">
                <button class="icon-btn" onclick="editarTipoComprobante(${itemId})" title="Editar"><i class="fa-solid fa-pen text-primary"></i></button>
                ${item.estado === 1 
                    ? `<button class="icon-btn" onclick="anularTipoComprobante(${itemId})" title="Anular"><i class="fa-solid fa-trash text-danger"></i></button>`
                    : `<button class="icon-btn" onclick="activarTipoComprobante(${itemId})" title="Activar"><i class="fa-solid fa-check text-success"></i></button>`
                }
            </td>
        `;
        tableBody.appendChild(tr);
    });
}

async function editarTipoComprobante(id) {
    try {
        const response = await api.get(`/tipocomprobante/${id}`);
        const obj = response.tipoComprobante || response.data || response;
        
        if(obj) {
            document.getElementById('idTipoComprobante').value = obj.idTipoComprobante || id;
            document.getElementById('descripcionTipoComp').value = obj.descripcion || '';
            document.getElementById('codigoSunatComp').value = obj.codigoSunat || '';
            document.getElementById('estadoTipoComprobante').value = obj.estado !== undefined ? obj.estado : 1;
            
            abrirModalTipoComprobante(true);
        }
    } catch(e) {
        alert('Error al obtener el detalle');
        console.error(e);
    }
}

async function anularTipoComprobante(id) {
    if(confirm('¿Estás seguro de anular este tipo de comprobante?')) {
        try {
            await api.delete(`/tipocomprobante/${id}`);
            alert('Anulado correctamente');
            cargarTipoComprobantes();
        } catch(e) {
            alert('Error al anular');
        }
    }
}

async function activarTipoComprobante(id) {
    if(confirm('¿Estás seguro de reactivar este tipo de comprobante?')) {
        try {
            await api.patch(`/tipocomprobante/${id}/activar`);
            alert('Activado correctamente');
            cargarTipoComprobantes();
        } catch(e) {
            alert('Error al activar');
        }
    }
}

function abrirModalTipoComprobante(isEdit = false) {
    document.getElementById('modalTipoComprobante').style.display = 'flex';
    if(!isEdit) {
        document.getElementById('formTipoComprobante').reset();
        document.getElementById('idTipoComprobante').value = '';
        document.getElementById('modalTitleTipoComprobante').textContent = 'Nuevo Tipo de Comprobante';
        document.getElementById('estadoTipoComprobanteGroup').style.display = 'none';
    } else {
        document.getElementById('modalTitleTipoComprobante').textContent = 'Editar Tipo de Comprobante';
        document.getElementById('estadoTipoComprobanteGroup').style.display = 'block';
    }
}

function cerrarModalTipoComprobante() {
    document.getElementById('modalTipoComprobante').style.display = 'none';
}
