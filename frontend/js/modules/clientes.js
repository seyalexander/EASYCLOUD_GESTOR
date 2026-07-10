// Lógica para Clientes
let clienteData = [];
let tiposDocCliente = [];
let tiposCliente = [];

document.addEventListener('viewLoaded', (e) => {
    if (e.detail === 'clientes') {
        initClienteModule();
    }
});

function initClienteModule() {
    const form = document.getElementById('formCliente');
    const btnNuevo = document.getElementById('btnNuevoCliente');
    const btnClose = document.getElementById('btnCloseModalCliente');
    const btnCancel = document.getElementById('btnCancelModalCliente');
    
    cargarCatalogoClientes();
    cargarClientes();

    btnNuevo.addEventListener('click', () => abrirModalCliente());
    btnClose.addEventListener('click', cerrarModalCliente);
    btnCancel.addEventListener('click', cerrarModalCliente);

    document.getElementById('searchInputCliente').addEventListener('input', (e) => {
        const query = e.target.value.toLowerCase();
        const filtrados = clienteData.filter(a => 
            (a.nombres && a.nombres.toLowerCase().includes(query)) ||
            (a.razonSocial && a.razonSocial.toLowerCase().includes(query)) ||
            (a.numeroDocumento && a.numeroDocumento.toLowerCase().includes(query))
        );
        renderTableCliente(filtrados);
    });

    form.addEventListener('submit', async (e) => {
        e.preventDefault();
        
        const id = document.getElementById('idCliente').value;
        const payload = {
            nombres: document.getElementById('nombresCliente').value,
            apellidos: document.getElementById('apellidosCliente').value,
            razonSocial: document.getElementById('razonSocialCliente').value,
            numeroDocumento: document.getElementById('numeroDocumentoCliente').value,
            idTipoDocumento: parseInt(document.getElementById('idTipoDocumentoCliente').value),
            idTipoCliente: parseInt(document.getElementById('idTipoCliente').value),
            telefono: document.getElementById('telefonoCliente').value,
            email: document.getElementById('emailCliente').value
        };

        try {
            const btnSubmit = document.getElementById('btnGuardarCliente');
            btnSubmit.textContent = 'Guardando...';
            btnSubmit.disabled = true;

            if (id) {
                payload.idCliente = parseInt(id);
                await api.put('/clientes', payload);
                alert('Cliente editado correctamente');
            } else {
                await api.post('/clientes', payload);
                alert('Cliente creado correctamente');
            }
            
            cerrarModalCliente();
            cargarClientes();
        } catch (error) {
            console.error(error);
            alert('Error al guardar. Verifica la consola.');
        } finally {
            const btnSubmit = document.getElementById('btnGuardarCliente');
            btnSubmit.textContent = 'Guardar';
            btnSubmit.disabled = false;
        }
    });
}

async function cargarCatalogoClientes() {
    try {
        const resTd = await api.get('/TipoDocumento?estado=1');
        tiposDocCliente = resTd.tipoDocumentos || resTd.data || [];
        document.getElementById('idTipoDocumentoCliente').innerHTML = '<option value="">Seleccione...</option>' + 
            tiposDocCliente.map(t => `<option value="${t.idTipoDocumento}">${t.descripcion}</option>`).join('');
            
        const resTc = await api.get('/tipoClientes?estado=1');
        tiposCliente = resTc.tipoClientes || resTc.data || [];
        document.getElementById('idTipoCliente').innerHTML = '<option value="">Seleccione...</option>' + 
            tiposCliente.map(t => `<option value="${t.idTipoClientes}">${t.descripcion}</option>`).join('');
    } catch(e) {
        console.warn('Error cargando catálogos de clientes', e);
    }
}

async function cargarClientes() {
    const tableBody = document.getElementById('tablaClienteBody');
    tableBody.innerHTML = '<tr><td colspan="6" style="text-align: center; padding: 2rem;">Cargando clientes...</td></tr>';
    
    try {
        const response = await api.get('/clientes?estado=2'); // 2 para todos si aplica
        clienteData = response.clientes || response.data || [];
        renderTableCliente(clienteData);
    } catch (error) {
        console.error(error);
        tableBody.innerHTML = '<tr><td colspan="6" style="text-align: center; color: var(--danger);">Error al cargar datos.</td></tr>';
    }
}

function renderTableCliente(data) {
    const tableBody = document.getElementById('tablaClienteBody');
    tableBody.innerHTML = '';
    
    if (data.length === 0) {
        tableBody.innerHTML = '<tr><td colspan="6" style="text-align: center; padding: 2rem;">No se encontraron registros.</td></tr>';
        return;
    }

    data.forEach(item => {
        const itemId = item.idCliente;
        
        let nombreDisplay = item.nombres ? (item.nombres + ' ' + (item.apellidos||'')) : (item.razonSocial || 'S/N');
        let estadoBadge = item.estado === 1 
            ? '<span class="badge badge-success">Activo</span>' 
            : '<span class="badge badge-error">Inactivo</span>';

        const tr = document.createElement('tr');
        tr.innerHTML = `
            <td><strong># ${itemId}</strong></td>
            <td>${item.numeroDocumento || '-'}</td>
            <td>${nombreDisplay}</td>
            <td>${item.telefono || '-'}</td>
            <td>${estadoBadge}</td>
            <td style="text-align: right;">
                <button class="icon-btn" onclick="editarCliente(${itemId})" title="Editar"><i class="fa-solid fa-pen text-primary"></i></button>
                ${item.estado === 1 
                    ? `<button class="icon-btn" onclick="cambiarEstadoCliente(${itemId}, 'anular')" title="Anular"><i class="fa-solid fa-ban text-danger"></i></button>`
                    : `<button class="icon-btn" onclick="cambiarEstadoCliente(${itemId}, 'activar')" title="Activar"><i class="fa-solid fa-check text-success"></i></button>`
                }
            </td>
        `;
        tableBody.appendChild(tr);
    });
}

async function editarCliente(id) {
    try {
        const response = await api.get(`/clientes/${id}`);
        const obj = response.cliente || response.data || response;
        if(obj) {
            document.getElementById('idCliente').value = obj.idCliente || id;
            document.getElementById('idTipoDocumentoCliente').value = obj.idTipoDocumento || '';
            document.getElementById('numeroDocumentoCliente').value = obj.numeroDocumento || '';
            document.getElementById('idTipoCliente').value = obj.idTipoCliente || '';
            document.getElementById('nombresCliente').value = obj.nombres || '';
            document.getElementById('apellidosCliente').value = obj.apellidos || '';
            document.getElementById('razonSocialCliente').value = obj.razonSocial || '';
            document.getElementById('telefonoCliente').value = obj.telefono || '';
            document.getElementById('emailCliente').value = obj.email || '';
            
            abrirModalCliente(true);
        }
    } catch(e) {
        alert('Error al obtener el detalle');
        console.error(e);
    }
}

async function cambiarEstadoCliente(id, accion) {
    if(confirm(`¿Seguro que desea ${accion} este cliente?`)) {
        try {
            if(accion === 'anular') {
                await api.delete(`/clientes/${id}`);
            } else {
                await api.patch(`/clientes/${id}/activar`);
            }
            alert(`Cliente ${accion}do correctamente`);
            cargarClientes();
        } catch(e) {
            alert('Error al cambiar estado');
        }
    }
}

function abrirModalCliente(isEdit = false) {
    document.getElementById('modalCliente').style.display = 'flex';
    if(!isEdit) {
        document.getElementById('formCliente').reset();
        document.getElementById('idCliente').value = '';
        document.getElementById('modalTitleCliente').textContent = 'Nuevo Cliente';
    } else {
        document.getElementById('modalTitleCliente').textContent = 'Editar Cliente';
    }
}

function cerrarModalCliente() {
    document.getElementById('modalCliente').style.display = 'none';
}
