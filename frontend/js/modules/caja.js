// Lógica para Caja
let cajaData = [];

document.addEventListener('viewLoaded', (e) => {
    if (e.detail === 'caja') {
        initCajaModule();
    }
});

function initCajaModule() {
    const form = document.getElementById('formCaja');
    const btnNuevo = document.getElementById('btnNuevaCaja');
    const btnClose = document.getElementById('btnCloseModalCaja');
    const btnCancel = document.getElementById('btnCancelModalCaja');
    
    cargarCajas();

    btnNuevo.addEventListener('click', () => abrirModalCaja());
    btnClose.addEventListener('click', cerrarModalCaja);
    btnCancel.addEventListener('click', cerrarModalCaja);

    document.getElementById('searchInputCaja').addEventListener('input', (e) => {
        const query = e.target.value.toLowerCase();
        const filtrados = cajaData.filter(a => 
            (a.descripcion && a.descripcion.toLowerCase().includes(query))
        );
        renderTableCaja(filtrados);
    });

    form.addEventListener('submit', async (e) => {
        e.preventDefault();
        
        const id = document.getElementById('idCaja').value;
        const payload = {
            descripcion: document.getElementById('descripcionCaja').value
        };

        try {
            const btnSubmit = document.getElementById('btnGuardarCaja');
            btnSubmit.textContent = 'Guardando...';
            btnSubmit.disabled = true;

            if (id) {
                payload.idCaja = parseInt(id);
                await api.put('/caja', payload);
                alert('Caja editada correctamente');
            } else {
                await api.post('/caja', payload);
                alert('Caja creada correctamente');
            }
            
            cerrarModalCaja();
            cargarCajas();
        } catch (error) {
            console.error(error);
            alert('Error al guardar. Verifica la consola.');
        } finally {
            const btnSubmit = document.getElementById('btnGuardarCaja');
            btnSubmit.textContent = 'Guardar';
            btnSubmit.disabled = false;
        }
    });
}

async function cargarCajas() {
    const tableBody = document.getElementById('tablaCajaBody');
    tableBody.innerHTML = '<tr><td colspan="3" style="text-align: center; padding: 2rem;">Cargando cajas...</td></tr>';
    
    try {
        const response = await api.get('/caja');
        cajaData = response.cajas || response.data || [];
        renderTableCaja(cajaData);
    } catch (error) {
        console.error(error);
        tableBody.innerHTML = '<tr><td colspan="3" style="text-align: center; color: var(--danger);">Error al cargar datos.</td></tr>';
    }
}

function renderTableCaja(data) {
    const tableBody = document.getElementById('tablaCajaBody');
    tableBody.innerHTML = '';
    
    if (data.length === 0) {
        tableBody.innerHTML = '<tr><td colspan="3" style="text-align: center; padding: 2rem;">No se encontraron registros.</td></tr>';
        return;
    }

    data.forEach(item => {
        const itemId = item.idCaja;

        const tr = document.createElement('tr');
        tr.innerHTML = `
            <td><strong># ${itemId}</strong></td>
            <td>${item.descripcion || '-'}</td>
            <td style="text-align: right;">
                <button class="icon-btn" onclick="editarCaja(${itemId})" title="Editar"><i class="fa-solid fa-pen text-primary"></i></button>
            </td>
        `;
        tableBody.appendChild(tr);
    });
}

async function editarCaja(id) {
    try {
        const response = await api.get(`/caja/${id}`);
        const obj = response.caja || response.data || response;
        
        if(obj) {
            document.getElementById('idCaja').value = obj.idCaja || id;
            document.getElementById('descripcionCaja').value = obj.descripcion || '';
            
            abrirModalCaja(true);
        }
    } catch(e) {
        alert('Error al obtener el detalle');
        console.error(e);
    }
}

function abrirModalCaja(isEdit = false) {
    document.getElementById('modalCaja').style.display = 'flex';
    if(!isEdit) {
        document.getElementById('formCaja').reset();
        document.getElementById('idCaja').value = '';
        document.getElementById('modalTitleCaja').textContent = 'Nueva Caja';
    } else {
        document.getElementById('modalTitleCaja').textContent = 'Editar Caja';
    }
}

function cerrarModalCaja() {
    document.getElementById('modalCaja').style.display = 'none';
}
