// Lógica para Compras
let compraData = [];
let proveedoresList = [];
let almacenesList = [];
let articulosList = [];

document.addEventListener('viewLoaded', (e) => {
    if (e.detail === 'compras') {
        initComprasModule();
    }
});

function initComprasModule() {
    const form = document.getElementById('formCompra');
    const btnNuevo = document.getElementById('btnNuevaCompra');
    const btnClose = document.getElementById('btnCloseModalCompra');
    const btnCancel = document.getElementById('btnCancelModalCompra');
    const btnAgregarFila = document.getElementById('btnAgregarFilaCompra');
    
    cargarCompras();
    cargarCatalogosCompra();

    btnNuevo.addEventListener('click', () => abrirModalCompra());
    btnClose.addEventListener('click', cerrarModalCompra);
    btnCancel.addEventListener('click', cerrarModalCompra);
    btnAgregarFila.addEventListener('click', agregarFilaDetalleCompra);

    document.getElementById('searchInputCompra').addEventListener('input', (e) => {
        const query = e.target.value.toLowerCase();
        const filtrados = compraData.filter(a => 
            (a.serieComprobante && a.serieComprobante.toLowerCase().includes(query)) ||
            (a.numeroComprobante && a.numeroComprobante.toLowerCase().includes(query))
        );
        renderTableCompra(filtrados);
    });

    form.addEventListener('submit', async (e) => {
        e.preventDefault();
        
        const detalles = recolectarDetallesCompra();
        if(detalles.length === 0) {
            alert('Debe agregar al menos un artículo a la compra.');
            return;
        }

        const totales = calcularTotalesCompra(detalles);

        const payload = {
            idProveedor: parseInt(document.getElementById('idProveedorCompra').value),
            idAlmacen: parseInt(document.getElementById('idAlmacenCompra').value),
            idTipoComprobante: parseInt(document.getElementById('idTipoComprobanteCompra').value),
            idTipoMovimiento: parseInt(document.getElementById('idTipoMovimientoCompra').value),
            serieComprobante: document.getElementById('serieComprobanteCompra').value,
            numeroComprobante: document.getElementById('numeroComprobanteCompra').value,
            condicionPago: document.getElementById('condicionPagoCompra').value,
            subTotal: totales.subtotal,
            impuesto: totales.impuesto,
            total: totales.total,
            detalles: detalles
        };

        try {
            const btnSubmit = document.getElementById('btnGuardarCompra');
            btnSubmit.textContent = 'Procesando...';
            btnSubmit.disabled = true;

            await api.post('/compra', payload);
            alert('Compra registrada correctamente');
            
            cerrarModalCompra();
            cargarCompras();
        } catch (error) {
            console.error(error);
            alert('Error al registrar compra. Verifique los datos o si hay stock (en caso de movimientos especiales).');
        } finally {
            const btnSubmit = document.getElementById('btnGuardarCompra');
            btnSubmit.textContent = 'Completar Compra';
            btnSubmit.disabled = false;
        }
    });
}

async function cargarCatalogosCompra() {
    try {
        // Cargar Proveedores
        try {
            const resProv = await api.get('/Proveedores?estado=1');
            proveedoresList = resProv.proveedores || resProv.data || [];
            const selectProv = document.getElementById('idProveedorCompra');
            selectProv.innerHTML = '<option value="">Seleccione proveedor...</option>' + 
                proveedoresList.map(p => `<option value="${p.idProveedor}">${p.razonSocial || 'Prov ' + p.idProveedor}</option>`).join('');
        } catch(e) { console.warn('No se pudo cargar proveedores', e); }

        // Cargar Almacenes
        try {
            const resAlm = await api.get('/almacenes?estado=1');
            almacenesList = resAlm.almacenes || resAlm.data || [];
            const selectAlm = document.getElementById('idAlmacenCompra');
            selectAlm.innerHTML = '<option value="">Seleccione almacén...</option>' + 
                almacenesList.map(a => `<option value="${a.idAlmacen}">${a.descripcion}</option>`).join('');
        } catch(e) { console.warn('No se pudo cargar almacenes', e); }

        // Cargar Tipo Comprobante
        try {
            const resTc = await api.get('/tipocomprobante?estado=1');
            const tcs = resTc.tipoComprobante || resTc.data || [];
            document.getElementById('idTipoComprobanteCompra').innerHTML = '<option value="">Seleccione...</option>' + 
                tcs.map(t => `<option value="${t.idTipoComprobante}">${t.descripcion}</option>`).join('');
        } catch(e) { console.warn('No se pudo cargar tipos de comprobante', e); }

        // Cargar Tipo Movimiento (Entradas)
        try {
            const resTm = await api.get('/tipoMovimiento?estado=1');
            let tms = resTm.tipoMovimientos || resTm.data || [];
            tms = tms.filter(t => t.esEntrada === 1); // Solo entradas para compras
            document.getElementById('idTipoMovimientoCompra').innerHTML = '<option value="">Seleccione...</option>' + 
                tms.map(t => `<option value="${t.idTipoMovimiento}">${t.descripcion}</option>`).join('');
        } catch(e) { console.warn('No se pudo cargar tipos de movimiento', e); }

        // Cargar Artículos
        try {
            const resArt = await api.get('/articulos?estado=1');
            articulosList = resArt.articulos || resArt.data || [];
        } catch(e) { console.warn('No se pudo cargar artículos', e); }

    } catch (error) {
        console.error('Error general cargando catálogos de compras', error);
    }
}

async function cargarCompras() {
    const tableBody = document.getElementById('tablaCompraBody');
    tableBody.innerHTML = '<tr><td colspan="5" style="text-align: center; padding: 2rem;">Cargando compras...</td></tr>';
    
    try {
        const response = await api.get('/compra?estado=2');
        compraData = response.compras || response.data || [];
        renderTableCompra(compraData);
    } catch (error) {
        console.error(error);
        tableBody.innerHTML = '<tr><td colspan="5" style="text-align: center; color: var(--danger);">Error al cargar datos.</td></tr>';
    }
}

function renderTableCompra(data) {
    const tableBody = document.getElementById('tablaCompraBody');
    tableBody.innerHTML = '';
    
    if (data.length === 0) {
        tableBody.innerHTML = '<tr><td colspan="5" style="text-align: center; padding: 2rem;">No se encontraron registros.</td></tr>';
        return;
    }

    data.forEach(item => {
        const itemId = item.idCompra;
        
        const tr = document.createElement('tr');
        tr.innerHTML = `
            <td><strong># ${itemId}</strong></td>
            <td>${item.serieComprobante || ''}-${item.numeroComprobante || ''}</td>
            <td>$ ${parseFloat(item.total || 0).toFixed(2)}</td>
            <td><span class="badge ${item.condicionPago === 'CONTADO' ? 'badge-success' : 'badge-warning'}">${item.condicionPago || '-'}</span></td>
            <td style="text-align: right;">
                <button class="icon-btn" onclick="verDetalleCompra(${itemId})" title="Ver Detalle"><i class="fa-solid fa-eye text-primary"></i></button>
            </td>
        `;
        tableBody.appendChild(tr);
    });
}

function abrirModalCompra() {
    document.getElementById('modalCompra').style.display = 'flex';
    document.getElementById('formCompra').reset();
    document.getElementById('tablaDetallesCompra').innerHTML = '';
    actualizarVistaTotalesCompra();
    agregarFilaDetalleCompra(); // Agregar una fila por defecto
}

function cerrarModalCompra() {
    document.getElementById('modalCompra').style.display = 'none';
}

function agregarFilaDetalleCompra() {
    const tbody = document.getElementById('tablaDetallesCompra');
    const tr = document.createElement('tr');
    
    const optionsHTML = articulosList.map(a => `<option value="${a.idArticulos}">${a.codigoArticulo} - ${a.descripcion}</option>`).join('');
    
    tr.innerHTML = `
        <td style="padding: 0.5rem 0;">
            <select class="form-control item-articulo" required>
                <option value="">Seleccione artículo...</option>
                ${optionsHTML}
            </select>
        </td>
        <td style="padding: 0.5rem 0; padding-left: 0.5rem;">
            <input type="number" class="form-control item-cantidad" required min="0.01" step="0.01" value="1" onchange="recalcularFilaCompra(this)" onkeyup="recalcularFilaCompra(this)">
        </td>
        <td style="padding: 0.5rem 0; padding-left: 0.5rem;">
            <input type="number" class="form-control item-costo" required min="0.00" step="0.01" value="0.00" onchange="recalcularFilaCompra(this)" onkeyup="recalcularFilaCompra(this)">
        </td>
        <td style="padding: 0.5rem 0; padding-left: 0.5rem; text-align: right; vertical-align: middle;">
            <span class="item-subtotal-text">$ 0.00</span>
        </td>
        <td style="padding: 0.5rem 0; text-align: right;">
            <button type="button" class="icon-btn" onclick="this.closest('tr').remove(); actualizarVistaTotalesCompra();"><i class="fa-solid fa-trash text-danger"></i></button>
        </td>
    `;
    tbody.appendChild(tr);
}

function recalcularFilaCompra(element) {
    const tr = element.closest('tr');
    const cant = parseFloat(tr.querySelector('.item-cantidad').value) || 0;
    const costo = parseFloat(tr.querySelector('.item-costo').value) || 0;
    const subtotal = cant * costo;
    tr.querySelector('.item-subtotal-text').textContent = '$ ' + subtotal.toFixed(2);
    actualizarVistaTotalesCompra();
}

function recolectarDetallesCompra() {
    const detalles = [];
    const filas = document.querySelectorAll('#tablaDetallesCompra tr');
    filas.forEach(tr => {
        const idArt = parseInt(tr.querySelector('.item-articulo').value);
        const cant = parseFloat(tr.querySelector('.item-cantidad').value);
        const costo = parseFloat(tr.querySelector('.item-costo').value);
        
        if(idArt && cant > 0 && costo >= 0) {
            detalles.push({
                idArticulo: idArt,
                cantidad: cant,
                costoUnitario: costo
            });
        }
    });
    return detalles;
}

function calcularTotalesCompra(detalles) {
    let subtotal = 0;
    detalles.forEach(d => {
        subtotal += (d.cantidad * d.costoUnitario);
    });
    // Asumiendo impuesto 18% incluido o no. Vamos a calcular 18% sobre subtotal para el ejemplo:
    const impuesto = subtotal * 0.18;
    const total = subtotal + impuesto;
    return { subtotal, impuesto, total };
}

function actualizarVistaTotalesCompra() {
    const detalles = recolectarDetallesCompra();
    const totales = calcularTotalesCompra(detalles);
    
    document.getElementById('lblSubtotalCompra').textContent = '$ ' + totales.subtotal.toFixed(2);
    document.getElementById('lblImpuestoCompra').textContent = '$ ' + totales.impuesto.toFixed(2);
    document.getElementById('lblTotalCompra').textContent = '$ ' + totales.total.toFixed(2);
}

async function verDetalleCompra(id) {
    try {
        const response = await api.get(`/compra/${id}`);
        console.log("Detalle compra:", response);
        alert('Revisa la consola para ver el JSON de la compra (ID ' + id + ')');
    } catch(e) {
        alert('Error al obtener el detalle');
        console.error(e);
    }
}
