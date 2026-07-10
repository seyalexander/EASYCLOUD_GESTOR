// Lógica para Ventas
let ventaData = [];
let clientesList = [];
let cajasList = [];
let tcsList = [];
let tmsList = [];
let articulosVentaList = [];
let preciosList = [];
let tipoPagosList = [];

document.addEventListener('viewLoaded', (e) => {
    if (e.detail === 'ventas') {
        initVentasModule();
    }
});

function initVentasModule() {
    const form = document.getElementById('formVenta');
    const btnNuevo = document.getElementById('btnNuevaVenta');
    const btnClose = document.getElementById('btnCloseModalVenta');
    const btnCancel = document.getElementById('btnCancelModalVenta');
    const btnAgregarFila = document.getElementById('btnAgregarFilaVenta');
    const btnAgregarPago = document.getElementById('btnAgregarPagoVenta');
    
    cargarVentas();
    cargarCatalogosVenta();

    btnNuevo.addEventListener('click', () => abrirModalVenta());
    btnClose.addEventListener('click', cerrarModalVenta);
    btnCancel.addEventListener('click', cerrarModalVenta);
    btnAgregarFila.addEventListener('click', agregarFilaDetalleVenta);
    btnAgregarPago.addEventListener('click', agregarFilaPagoVenta);

    document.getElementById('searchInputVenta').addEventListener('input', (e) => {
        const query = e.target.value.toLowerCase();
        // Implementación básica de búsqueda por id (o cliente si mapeáramos nombres)
        const filtrados = ventaData.filter(a => a.idVenta.toString().includes(query));
        renderTableVenta(filtrados);
    });

    form.addEventListener('submit', async (e) => {
        e.preventDefault();
        
        const detalles = recolectarDetallesVenta();
        if(detalles.length === 0) {
            alert('Debe agregar al menos un artículo a la venta.');
            return;
        }

        const pagos = recolectarPagosVenta();
        
        const totales = calcularTotalesVenta(detalles);
        const totalPagado = calcularTotalPagado(pagos);
        const condicion = document.getElementById('condicionPagoVenta').value;
        
        if(condicion === 'CONTADO' && totalPagado < totales.total) {
            if(!confirm(`La condición es AL CONTADO, pero el total pagado ($${totalPagado.toFixed(2)}) es menor al total de la venta ($${totales.total.toFixed(2)}). ¿Desea continuar?`)){
                return;
            }
        }

        const payload = {
            idCliente: parseInt(document.getElementById('idClienteVenta').value),
            idCaja: parseInt(document.getElementById('idCajaVenta').value),
            idTipoComprobante: parseInt(document.getElementById('idTipoComprobanteVenta').value),
            idTipoMovimiento: parseInt(document.getElementById('idTipoMovimientoVenta').value),
            condicionPago: condicion,
            detalles: detalles,
            detallesPago: pagos.length > 0 ? pagos : null
        };

        try {
            const btnSubmit = document.getElementById('btnGuardarVenta');
            btnSubmit.textContent = 'Procesando...';
            btnSubmit.disabled = true;

            await api.post('/Venta', payload);
            alert('Venta procesada con éxito!');
            
            cerrarModalVenta();
            cargarVentas();
        } catch (error) {
            console.error(error);
            alert('Error al registrar venta. Verifique stock y catálogo.');
        } finally {
            const btnSubmit = document.getElementById('btnGuardarVenta');
            btnSubmit.textContent = 'Cerrar Venta';
            btnSubmit.disabled = false;
        }
    });
}

async function cargarCatalogosVenta() {
    try {
        // Cargar Clientes
        try {
            const resCli = await api.get('/clientes?estado=1'); // asumiendo ruta
            clientesList = resCli.clientes || resCli.data || [];
            document.getElementById('idClienteVenta').innerHTML = '<option value="">Seleccione cliente...</option>' + 
                clientesList.map(c => `<option value="${c.idCliente}">${c.nombres || c.razonSocial || 'Cli ' + c.idCliente}</option>`).join('');
        } catch(e) {}

        // Cargar Cajas
        try {
            const resCaja = await api.get('/caja');
            cajasList = resCaja.cajas || resCaja.data || [];
            document.getElementById('idCajaVenta').innerHTML = '<option value="">Seleccione caja...</option>' + 
                cajasList.map(c => `<option value="${c.idCaja}">${c.descripcion}</option>`).join('');
        } catch(e) {}

        // Cargar Tipo Comprobante
        try {
            const resTc = await api.get('/tipocomprobante?estado=1');
            tcsList = resTc.tipoComprobante || resTc.data || [];
            document.getElementById('idTipoComprobanteVenta').innerHTML = '<option value="">Seleccione...</option>' + 
                tcsList.map(t => `<option value="${t.idTipoComprobante}">${t.descripcion}</option>`).join('');
        } catch(e) {}

        // Cargar Tipo Movimiento (Salidas)
        try {
            const resTm = await api.get('/tipoMovimiento?estado=1');
            tmsList = (resTm.tipoMovimientos || resTm.data || []).filter(t => t.esEntrada === 0);
            document.getElementById('idTipoMovimientoVenta').innerHTML = '<option value="">Seleccione...</option>' + 
                tmsList.map(t => `<option value="${t.idTipoMovimiento}">${t.descripcion}</option>`).join('');
        } catch(e) {}

        // Cargar Articulos
        try {
            const resArt = await api.get('/articulos?estado=1');
            articulosVentaList = resArt.articulos || resArt.data || [];
        } catch(e) {}
        
        // Cargar Precios (para autocompletar precio de venta)
        try {
            const resPrecios = await api.get('/productoPrecios?estado=1');
            preciosList = resPrecios.productoPrecios || resPrecios.data || [];
        } catch(e) {}

        // Cargar Tipo Pagos
        try {
            const resTp = await api.get('/tipoPagos?estado=1'); // asumiendo que existe
            tipoPagosList = resTp.tipoPagos || resTp.data || [];
        } catch(e) {}

    } catch (error) {
        console.error('Error cargando catálogos de venta', error);
    }
}

async function cargarVentas() {
    const tableBody = document.getElementById('tablaVentaBody');
    tableBody.innerHTML = '<tr><td colspan="5" style="text-align: center; padding: 2rem;">Cargando ventas...</td></tr>';
    
    try {
        const response = await api.get('/Venta?estado=2');
        ventaData = response.ventas || response.data || [];
        renderTableVenta(ventaData);
    } catch (error) {
        console.error(error);
        tableBody.innerHTML = '<tr><td colspan="5" style="text-align: center; color: var(--danger);">Error al cargar datos.</td></tr>';
    }
}

function renderTableVenta(data) {
    const tableBody = document.getElementById('tablaVentaBody');
    tableBody.innerHTML = '';
    
    if (data.length === 0) {
        tableBody.innerHTML = '<tr><td colspan="5" style="text-align: center; padding: 2rem;">No se encontraron registros.</td></tr>';
        return;
    }

    data.forEach(item => {
        const itemId = item.idVenta;
        
        const tr = document.createElement('tr');
        tr.innerHTML = `
            <td><strong># ${itemId}</strong></td>
            <td>Cliente ID: ${item.idCliente || '-'}</td>
            <td>$ ${parseFloat(item.total || 0).toFixed(2)}</td>
            <td><span class="badge ${item.condicionPago === 'CONTADO' ? 'badge-success' : 'badge-warning'}">${item.condicionPago || '-'}</span></td>
            <td style="text-align: right;">
                <button class="icon-btn" onclick="anularVenta(${itemId})" title="Anular"><i class="fa-solid fa-ban text-danger"></i></button>
            </td>
        `;
        tableBody.appendChild(tr);
    });
}

function abrirModalVenta() {
    document.getElementById('modalVenta').style.display = 'flex';
    document.getElementById('formVenta').reset();
    document.getElementById('tablaDetallesVenta').innerHTML = '';
    document.getElementById('tablaPagosVenta').innerHTML = '';
    actualizarVistaTotalesVenta();
    agregarFilaDetalleVenta();
    agregarFilaPagoVenta();
}

function cerrarModalVenta() {
    document.getElementById('modalVenta').style.display = 'none';
}

function agregarFilaDetalleVenta() {
    const tbody = document.getElementById('tablaDetallesVenta');
    const tr = document.createElement('tr');
    
    const optionsHTML = articulosVentaList.map(a => `<option value="${a.idArticulos}">${a.codigoArticulo} - ${a.descripcion}</option>`).join('');
    
    tr.innerHTML = `
        <td style="padding: 0.5rem 0;">
            <select class="form-control item-articulo" required onchange="autoCompletarPrecio(this)">
                <option value="">Seleccione artículo...</option>
                ${optionsHTML}
            </select>
        </td>
        <td style="padding: 0.5rem 0; padding-left: 0.5rem;">
            <input type="number" class="form-control item-cantidad" required min="1" step="1" value="1" onchange="recalcularFilaVenta(this)" onkeyup="recalcularFilaVenta(this)">
        </td>
        <td style="padding: 0.5rem 0; padding-left: 0.5rem;">
            <input type="number" class="form-control item-precio" required min="0.00" step="0.01" value="0.00" onchange="recalcularFilaVenta(this)" onkeyup="recalcularFilaVenta(this)">
        </td>
        <td style="padding: 0.5rem 0; padding-left: 0.5rem; text-align: right; vertical-align: middle;">
            <span class="item-subtotal-text">$ 0.00</span>
        </td>
        <td style="padding: 0.5rem 0; text-align: right;">
            <button type="button" class="icon-btn" onclick="this.closest('tr').remove(); actualizarVistaTotalesVenta();"><i class="fa-solid fa-trash text-danger"></i></button>
        </td>
    `;
    tbody.appendChild(tr);
}

function autoCompletarPrecio(selectElem) {
    const idArt = parseInt(selectElem.value);
    const tr = selectElem.closest('tr');
    if(!idArt) return;
    
    // Buscar precio en la lista cargada
    const precioObj = preciosList.find(p => p.idArticulo === idArt && p.estado === 1);
    if(precioObj) {
        tr.querySelector('.item-precio').value = precioObj.precio;
    }
    recalcularFilaVenta(selectElem);
}

function recalcularFilaVenta(element) {
    const tr = element.closest('tr');
    const cant = parseFloat(tr.querySelector('.item-cantidad').value) || 0;
    const precio = parseFloat(tr.querySelector('.item-precio').value) || 0;
    const subtotal = cant * precio;
    tr.querySelector('.item-subtotal-text').textContent = '$ ' + subtotal.toFixed(2);
    actualizarVistaTotalesVenta();
}

function agregarFilaPagoVenta() {
    const tbody = document.getElementById('tablaPagosVenta');
    const tr = document.createElement('tr');
    
    // Fallback if tipoPagos API failed
    let options = `<option value="1">Efectivo</option><option value="2">Tarjeta/Transferencia</option>`;
    if(tipoPagosList.length > 0) {
        options = tipoPagosList.map(tp => `<option value="${tp.idTipoPago}">${tp.descripcion}</option>`).join('');
    }

    tr.innerHTML = `
        <td style="padding: 0.5rem 0;">
            <select class="form-control pago-tipo" required>
                ${options}
            </select>
        </td>
        <td style="padding: 0.5rem 0; padding-left: 0.5rem;">
            <input type="text" class="form-control pago-ref" placeholder="Ej. OP-1234" value="CONTADO">
        </td>
        <td style="padding: 0.5rem 0; padding-left: 0.5rem;">
            <input type="number" class="form-control pago-monto" required min="0.00" step="0.01" value="0.00" onchange="actualizarVistaTotalesVenta()" onkeyup="actualizarVistaTotalesVenta()">
        </td>
        <td style="padding: 0.5rem 0; text-align: right;">
            <button type="button" class="icon-btn" onclick="this.closest('tr').remove(); actualizarVistaTotalesVenta();"><i class="fa-solid fa-trash text-danger"></i></button>
        </td>
    `;
    tbody.appendChild(tr);
    
    // Auto fill monto if there is a pending balance
    setTimeout(() => {
        const totales = calcularTotalesVenta(recolectarDetallesVenta());
        const pagado = calcularTotalPagado(recolectarPagosVenta());
        if(totales.total > pagado) {
            tr.querySelector('.pago-monto').value = (totales.total - pagado).toFixed(2);
            actualizarVistaTotalesVenta();
        }
    }, 100);
}

function recolectarDetallesVenta() {
    const detalles = [];
    const filas = document.querySelectorAll('#tablaDetallesVenta tr');
    filas.forEach(tr => {
        const idArt = parseInt(tr.querySelector('.item-articulo').value);
        const cant = parseFloat(tr.querySelector('.item-cantidad').value);
        const precio = parseFloat(tr.querySelector('.item-precio').value);
        
        if(idArt && cant > 0 && precio >= 0) {
            detalles.push({
                idArticulo: idArt,
                cantidad: cant,
                precioUnitario: precio
            });
        }
    });
    return detalles;
}

function recolectarPagosVenta() {
    const pagos = [];
    const filas = document.querySelectorAll('#tablaPagosVenta tr');
    filas.forEach(tr => {
        const idTipo = parseInt(tr.querySelector('.pago-tipo').value);
        const ref = tr.querySelector('.pago-ref').value || 'S/N';
        const monto = parseFloat(tr.querySelector('.pago-monto').value);
        
        if(idTipo && monto > 0) {
            pagos.push({
                idTipoPago: idTipo,
                monto: monto,
                referencia: ref
            });
        }
    });
    return pagos;
}

function calcularTotalesVenta(detalles) {
    let subtotal = 0;
    detalles.forEach(d => {
        subtotal += (d.cantidad * d.precioUnitario);
    });
    const impuesto = subtotal * 0.18;
    const total = subtotal + impuesto;
    return { subtotal, impuesto, total };
}

function calcularTotalPagado(pagos) {
    let total = 0;
    pagos.forEach(p => { total += p.monto; });
    return total;
}

function actualizarVistaTotalesVenta() {
    const detalles = recolectarDetallesVenta();
    const pagos = recolectarPagosVenta();
    
    const totales = calcularTotalesVenta(detalles);
    const pagado = calcularTotalPagado(pagos);
    const saldo = pagado - totales.total;
    
    document.getElementById('lblSubtotalVenta').textContent = '$ ' + totales.subtotal.toFixed(2);
    document.getElementById('lblImpuestoVenta').textContent = '$ ' + totales.impuesto.toFixed(2);
    document.getElementById('lblTotalVenta').textContent = '$ ' + totales.total.toFixed(2);
    
    document.getElementById('lblTotalPagadoVenta').textContent = '$ ' + pagado.toFixed(2);
    
    const lblSaldo = document.getElementById('lblSaldoVenta');
    lblSaldo.textContent = '$ ' + saldo.toFixed(2);
    if(saldo < 0) {
        lblSaldo.style.color = 'var(--danger)';
    } else {
        lblSaldo.style.color = 'var(--text-color)';
    }
}

async function anularVenta(id) {
    if(confirm('¿Está seguro de anular esta venta? Esta acción no se puede deshacer.')) {
        try {
            await api.delete(`/Venta/${id}`);
            alert('Venta anulada correctamente');
            cargarVentas();
        } catch(e) {
            alert('Error al anular venta');
        }
    }
}
